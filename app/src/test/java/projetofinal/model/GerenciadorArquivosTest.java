package projetofinal.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import projetofinal.model.Exceções.FormatoArquivoInvalidoException;
import projetofinal.model.Transações.Ganho;
import projetofinal.model.Transações.Gasto;
import projetofinal.model.Transações.Transacao;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class GerenciadorArquivosTest {

    @Test
    public void deveRejeitarArquivoSemExtensaoTxt() {
        GerenciadorArquivos gerenciador = new GerenciadorArquivos();
        
        // Verifica se a exceção é lançada quando o arquivo não termina com .txt
        Exception exception = assertThrows(FormatoArquivoInvalidoException.class, () -> {
            gerenciador.carregarDados("dados_financeiros.csv");
        });
        
        assertEquals("O sistema apenas aceita arquivos de texto (.txt)", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoAoLerArquivoInexistente() {
        GerenciadorArquivos gerenciador = new GerenciadorArquivos();
        
        Exception exception = assertThrows(FormatoArquivoInvalidoException.class, () -> {
            gerenciador.carregarDados("arquivo_inexistente_fantasma.txt");
        });
        
        assertTrue(exception.getMessage().contains("Erro ao ler o arquivo"));
    }

    @Test
    public void deveCarregarDadosDeUmArquivoTxtValido(@TempDir Path pastaTemporaria) throws Exception {
        // Prepara o conteúdo do arquivo simulando o formato esperado
        String conteudoMock = "=== Extrato da Carteira: Teste ===\n" +
                              "2023-10-01 | Salário | R$ 5000.0\n" +
                              "2023-10-02 | Aluguel | R$ -1500.0\n" +
                              "\n" + // Linha em branco para testar se ele ignora
                              "Saldo Final: R$ 3500.0\n";
                              
        Path arquivoMock = pastaTemporaria.resolve("extrato_mock.txt");
        Files.writeString(arquivoMock, conteudoMock);

        GerenciadorArquivos gerenciador = new GerenciadorArquivos();
        Carteira carteiraRecuperada = gerenciador.carregarDados(arquivoMock.toString());

        // Verifica o cabeçalho gerado pelo carregamento
        assertEquals("Carteira Recuperada", carteiraRecuperada.getNome());
        assertEquals(2, carteiraRecuperada.getTransacoes().size());

        // Verifica a primeira transação (Valor >= 0, deve ser um Ganho)
        Transacao t1 = carteiraRecuperada.getTransacoes().get(0);
        assertTrue(t1 instanceof Ganho);
        assertEquals("Salário", t1.getDescricao());
        assertEquals(5000.0, t1.getValor());
        assertEquals("Recuperado", ((Ganho) t1).getFonte());

        // Verifica a segunda transação (Valor < 0, deve ser um Gasto)
        Transacao t2 = carteiraRecuperada.getTransacoes().get(1);
        assertTrue(t2 instanceof Gasto);
        assertEquals("Aluguel", t2.getDescricao());
        
        // Em um Gasto, o valor base é positivo, mas a execução o torna negativo
        assertEquals(1500.0, t2.getValor());
        assertEquals(-1500.0, t2.executarTransacao());
        
        assertEquals("Fixo", ((Gasto) t2).getFrequencia());
        assertEquals("Desconhecido", ((Gasto) t2).getLocalidade());
    }
}