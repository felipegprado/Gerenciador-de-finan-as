package projetofinal.model.Transações;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class GanhoTest {

    @Test
    public void deveInicializarGanhoCorretamente() {
        Ganho ganho = new Ganho(1500.0, "Salário", "10/10/2023", Arrays.asList("Fixo", "Trabalho"), "Empresa X");

        assertEquals(1500.0, ganho.getValor());
        assertEquals("Salário", ganho.getDescricao());
        assertEquals("Empresa X", ganho.getFonte());
        assertEquals(2, ganho.getTags().size());
    }

    @Test
    public void deveExecutarTransacaoRetornandoValorPositivo() {
        Ganho ganho = new Ganho(500.0, "Venda", "12/10/2023", Arrays.asList("Extra"), "Cliente Y");

        assertEquals(500.0, ganho.executarTransacao());
    }

    @Test
    public void devePermitirAlterarFonteViaSetter() {
        Ganho ganho = new Ganho(100.0, "Pix", "15/10/2023", Arrays.asList("Transferencia"), "João");
        
        ganho.setFonte("Maria");
        assertEquals("Maria", ganho.getFonte());
    }
}