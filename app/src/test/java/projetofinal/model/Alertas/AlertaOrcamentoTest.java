package projetofinal.model.Alertas;

import org.junit.jupiter.api.Test;

import projetofinal.model.Alertas.AlertaOrcamento;

import static org.junit.jupiter.api.Assertions.*;

public class AlertaOrcamentoTest {

    @Test
    public void deveInicializarComValoresCorretos() {
        AlertaOrcamento alerta = new AlertaOrcamento("Orçamento estourado", 500.0);
        
        // Verifica a inicialização na classe mãe e na subclasse
        assertEquals("Orçamento estourado", alerta.getMensagem());
        assertTrue(alerta.isAtivo());
        assertEquals(500.0, alerta.getValorLimite());
        assertEquals(0.0, alerta.getValorAtual());
    }

    @Test
    public void deveAcionarGatilhoQuandoAtingirLimite() {
        AlertaOrcamento alerta = new AlertaOrcamento("Aviso de Orçamento", 100.0);
        
        alerta.atualizarValorAtual(50.0);
        assertEquals(50.0, alerta.getValorAtual());
        assertFalse(alerta.verificarGatilho());
        
        alerta.atualizarValorAtual(50.0);
        assertEquals(100.0, alerta.getValorAtual());
        assertTrue(alerta.verificarGatilho());
        
        alerta.atualizarValorAtual(10.0);
        assertTrue(alerta.verificarGatilho());
    }

    @Test
    public void naoDeveAcionarGatilhoQuandoEstiverInativo() {
        AlertaOrcamento alerta = new AlertaOrcamento("Aviso de Orçamento", 100.0);
        alerta.setAtivo(false);
        alerta.atualizarValorAtual(150.0);
        
        assertFalse(alerta.verificarGatilho());
    }
    
    @Test
    public void devePermitirAlterarValoresViaSetters() {
        AlertaOrcamento alerta = new AlertaOrcamento("Inicial", 10.0);
        
        alerta.setMensagem("Modificada");
        alerta.setValorLimite(200.0);
        alerta.setValorAtual(150.0);
        alerta.setAtivo(false);
        
        assertEquals("Modificada", alerta.getMensagem());
        assertEquals(200.0, alerta.getValorLimite());
        assertEquals(150.0, alerta.getValorAtual());
        assertFalse(alerta.isAtivo());
    }
}