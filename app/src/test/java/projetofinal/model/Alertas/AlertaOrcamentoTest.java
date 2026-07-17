package projetofinal.model.Alertas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;

public class AlertaOrcamentoTest {

    // Adicionado para inicializar a base gráfica do JavaFX, evitando o IllegalStateException
    @BeforeAll
    public static void initJFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // O Toolkit do JavaFX já foi inicializado
        }
    }

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