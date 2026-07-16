package projetofinal.model;

import org.junit.jupiter.api.Test;

import projetofinal.model.Transações.Transacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TransacaoTest {

    // Classe concreta construída apenas para testar a abstração
    private class TransacaoConcreta extends Transacao {
        public TransacaoConcreta(double valor, String descricao, String data, List<String> tags) {
            super(valor, descricao, data, tags);
        }

        @Override
        public double executarTransacao() {
            return getValor();
        }
    }

    @Test
    public void deveInicializarCorretamenteEEvitarListaTagsNula() {
        // Verifica a lógica que evita NullPointerException se passarem uma lista nula
        Transacao t1 = new TransacaoConcreta(100.0, "Almoço", "2023-10-01", null);
        assertNotNull(t1.getTags());
        assertTrue(t1.getTags().isEmpty());

        // Verifica a inicialização com uma lista válida
        List<String> tags = new ArrayList<>(Arrays.asList("Alimentação", "Diário"));
        Transacao t2 = new TransacaoConcreta(200.0, "Jantar", "2023-10-02", tags);
        assertEquals(2, t2.getTags().size());
    }

    @Test
    public void deveConverterDataEntreStringELocalDateCorretamente() {
        Transacao t = new TransacaoConcreta(100.0, "Teste de Data", "2023-10-15", null);
        
        // Testa o método getData() que faz o parse da String para LocalDate
        LocalDate dataEsperada = LocalDate.of(2023, 10, 15);
        assertEquals(dataEsperada, t.getData());

        // Testa o método setData() que passa de LocalDate para String
        LocalDate novaData = LocalDate.of(2023, 12, 31);
        t.setData(novaData);
        assertEquals(novaData, t.getData());
    }

    @Test
    public void deveAdicionarTagPeloMetodoAuxiliar() {
        Transacao t = new TransacaoConcreta(50.0, "Sorvete", "2023-10-01", new ArrayList<>());
        t.adicionarTag("Sobremesa");
        
        assertEquals(1, t.getTags().size());
        assertEquals("Sobremesa", t.getTags().get(0));
    }

    @Test
    public void deveAlterarValoresViaSetters() {
        Transacao t = new TransacaoConcreta(0.0, "Inicial", "2023-01-01", null);
        
        t.setValor(350.50);
        t.setDescricao("Atualizado");
        
        assertEquals(350.50, t.getValor());
        assertEquals("Atualizado", t.getDescricao());
    }
}