package projetofinal.model.Alertas;

import org.junit.jupiter.api.Test;

import projetofinal.model.Alertas.AlertaVencimento;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class AlertaVencimentoTest {

    @Test
    public void deveInicializarComValoresCorretos() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        AlertaVencimento alerta = new AlertaVencimento("Boleto", amanha);
        
        assertEquals("Boleto", alerta.getMensagem());
        assertEquals(amanha, alerta.getDataVencimento());
        assertTrue(alerta.isAtivo());
    }

    @Test
    public void deveAcionarGatilhoSeDataForHojeOuPassado() {
        AlertaVencimento alertaHoje = new AlertaVencimento("Vence hoje", LocalDate.now());
        assertTrue(alertaHoje.verificarGatilho());

        AlertaVencimento alertaPassado = new AlertaVencimento("Venceu ontem", LocalDate.now().minusDays(1));
        assertTrue(alertaPassado.verificarGatilho());
    }

    @Test
    public void naoDeveAcionarGatilhoSeDataForNoFuturo() {
        AlertaVencimento alertaFuturo = new AlertaVencimento("Vence amanhã", LocalDate.now().plusDays(1));
        assertFalse(alertaFuturo.verificarGatilho());
    }

    @Test
    public void naoDeveAcionarGatilhoQuandoEstiverInativo() {
        AlertaVencimento alerta = new AlertaVencimento("Vence hoje", LocalDate.now());
        alerta.setAtivo(false);
        
        assertFalse(alerta.verificarGatilho());
    }
    
    @Test
    public void devePermitirAlterarDataViaSetter() {
        AlertaVencimento alerta = new AlertaVencimento("Teste", LocalDate.now());
        LocalDate novaData = LocalDate.now().plusDays(5);
        
        alerta.setDataVencimento(novaData);
        assertEquals(novaData, alerta.getDataVencimento());
    }
}