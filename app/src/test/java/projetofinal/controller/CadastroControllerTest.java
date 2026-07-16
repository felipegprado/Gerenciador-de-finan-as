package projetofinal.controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class CadastroControllerTest {

    private CadastroController controller;

    // Inicializa o ambiente JavaFX necessário para instanciar elementos gráficos
    @BeforeAll
    public static void initJFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // O Toolkit do JavaFX já foi inicializado
        }
    }

    @BeforeEach
    public void setup() throws Exception {
        controller = new CadastroController();

        // Injeção manual dos componentes JavaFX simulando o comportamento do @FXML
        injetarComponente(controller, "cadastroNome", new TextField());
        injetarComponente(controller, "cadastroUsuario", new TextField());
        injetarComponente(controller, "cadastroSenha", new PasswordField());
        injetarComponente(controller, "botaoCadastrar", new Button());
    }

    private void injetarComponente(Object alvo, String nomeCampo, Object componente) throws Exception {
        Field field = alvo.getClass().getDeclaredField(nomeCampo);
        field.setAccessible(true);
        field.set(alvo, componente);
    }

    @Test
    public void criarCadastro_NaoDeveProsseguirSeCamposVazios() throws Exception {
        // Recupera o campo injetado para manipular seu valor
        Field fieldNome = controller.getClass().getDeclaredField("cadastroNome");
        fieldNome.setAccessible(true);
        TextField nome = (TextField) fieldNome.get(controller);
        
        nome.setText(""); // Simula que o usuário deixou o campo vazio

        // Como há uma verificação de campos vazios que dá um "return" antecipado[cite: 15], 
        // o código não chega na lógica de carregar a próxima tela e não lança NullPointerException.
        assertDoesNotThrow(() -> controller.criarCadastro(null));
    }
}