package projetofinal.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import projetofinal.model.Alertas.AlertaOrcamento;
import projetofinal.model.Transações.Ganho;
import projetofinal.model.Transações.Transacao;

public class CarteiraTest {

    @Test
    public void deveInicializarCarteiraVazia() {
        Carteira carteira = new Carteira("Minha Carteira");

        assertEquals("Minha Carteira", carteira.getNome());
        assertTrue(carteira.getTransacoes().isEmpty());
        assertTrue(carteira.getAlertas().isEmpty());
    }

    @Test
    public void deveCalcularSaldoCorretamente() {
        Carteira carteira = new Carteira("Principal");
        
        Ganho ganho1 = new Ganho(1000.0, "Salário", "01/10", Arrays.asList("Fixo"), "Empresa");
        Ganho ganho2 = new Ganho(250.0, "Freela", "05/10", Arrays.asList("Extra"), "Cliente");
        
        carteira.adicionarTransacao(ganho1);
        carteira.adicionarTransacao(ganho2);

        assertEquals(1250.0, carteira.calcularSaldo());
    }

    @Test
    public void deveBuscarTransacaoPorDescricaoOuTag() {
        Carteira carteira = new Carteira("Principal");
        Ganho ganho = new Ganho(500.0, "Venda de Livros", "10/10", Arrays.asList("Livraria", "Desapego"), "Sebo");
        carteira.adicionarTransacao(ganho);

        // Busca por descrição (ignorando maiúsculas/minúsculas)
        List<Transacao> buscaDescricao = carteira.fazerBusca("livros");
        assertEquals(1, buscaDescricao.size());

        // Busca por tag
        List<Transacao> buscaTag = carteira.fazerBusca("desapego");
        assertEquals(1, buscaTag.size());

        // Busca sem resultados
        List<Transacao> buscaVazia = carteira.fazerBusca("carro");
        assertTrue(buscaVazia.isEmpty());
    }

    @Test
    public void deveRemoverTransacaoEAlerta() {
        Carteira carteira = new Carteira("Testes");
        
        Ganho ganho = new Ganho(100.0, "Teste", "01/01", Arrays.asList("Tag"), "Fonte");
        AlertaOrcamento alerta = new AlertaOrcamento("Alerta Teste", 500.0);
        
        carteira.adicionarTransacao(ganho);
        carteira.adicionarAlerta(alerta);
        
        assertTrue(carteira.removerTransacao(ganho));
        assertTrue(carteira.getTransacoes().isEmpty());
        
        assertTrue(carteira.removerAlerta(alerta));
        assertTrue(carteira.getAlertas().isEmpty());
    }

    @Test
    public void deveExportarDadosParaArquivo(@TempDir Path pastaTemporaria) throws Exception {
        Carteira carteira = new Carteira("Exportacao");
        Ganho ganho = new Ganho(300.0, "Venda", "2023-01-01", Arrays.asList("Comercio"), "Loja");
        carteira.adicionarTransacao(ganho);

        // Cria um caminho de arquivo seguro dentro da pasta temporária do JUnit
        Path arquivoExportacao = pastaTemporaria.resolve("extrato.txt");
        
        carteira.exportarDados(arquivoExportacao.toString());

        // Verifica se o arquivo foi realmente criado e se contém o texto esperado
        assertTrue(Files.exists(arquivoExportacao));
        String conteudoArquivo = Files.readString(arquivoExportacao);
        assertTrue(conteudoArquivo.contains("=== Extrato da Carteira: Exportacao ==="));
        assertTrue(conteudoArquivo.contains("Venda"));
        assertTrue(conteudoArquivo.contains("Saldo Final: R$ 300.0"));
    }
}