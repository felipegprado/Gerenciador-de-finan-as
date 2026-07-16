package projetofinal.controller;

import javafx.application.Platform;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    private LoginController controller;

    @BeforeAll
    public static void initJFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit já iniciado
        }
    }

    @BeforeEach
    public void setup() throws Exception {
        controller = new LoginController();

        // Injeta o TextFlow usado para a mensagem de erro no login
        Field field = controller.getClass().getDeclaredField("mensagemLogin");
        field.setAccessible(true);
        field.set(controller, new TextFlow());
    }

    @Test
    public void limparMensagemLogin_DeveOcultarMensagemDeErro() throws Exception {
        Field field = controller.getClass().getDeclaredField("mensagemLogin");
        field.setAccessible(true);
        TextFlow mensagem = (TextFlow) field.get(controller);

        // Simula que a mensagem de erro está aparecendo na tela
        mensagem.setVisible(true);
        assertTrue(mensagem.isVisible());

        // Aciona o método que ocorre ao clicar para limpar a mensagem[cite: 18]
        // Podemos passar 'null' porque o MouseEvent não é utilizado no corpo do método[cite: 18]
        controller.limparMensagemLogin(null);

        // Verifica se a mensagem foi ocultada[cite: 18]
        assertFalse(mensagem.isVisible());
    }
}