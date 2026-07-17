package projetofinal.model.Transações;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class GastoTest {

    @Test
    public void deveInicializarGastoCorretamente() {
        Gasto gasto = new Gasto(200.0, "Supermercado", "10/10/2023", Arrays.asList("Alimentacao", "Essencial"), "Mensal", "Mercado Central");

        // Verifica a inicialização dos atributos específicos
        assertEquals("Mensal", gasto.getFrequencia());
        assertEquals("Mercado Central", gasto.getLocalidade());
        assertEquals("Supermercado", gasto.getDescricao());
        assertEquals(2, gasto.getTags().size());
    }

    @Test
    public void devePermitirAlterarFrequenciaELocalidadeViaSetters() {
        Gasto gasto = new Gasto(100.0, "Gasolina", "15/10/2023", Arrays.asList("Transporte"), "Semanal", "Posto A");
        
        gasto.setFrequencia("Quinzenal");
        gasto.setLocalidade("Posto B");
        
        assertEquals("Quinzenal", gasto.getFrequencia());
        assertEquals("Posto B", gasto.getLocalidade());
    }

    @Test
    public void deveRetornarValorNegativoParaDescontoNoSaldo() {
        Gasto gasto = new Gasto(50.0, "Padaria", "11/10/2023", Arrays.asList("Alimentacao"), "Diário", "Esquina");
        
        // Correção: O valor puro é positivo, a transação converte para negativo
        assertEquals(50.0, gasto.getValor());
        assertEquals(-50.0, gasto.executarTransacao());
    }
}