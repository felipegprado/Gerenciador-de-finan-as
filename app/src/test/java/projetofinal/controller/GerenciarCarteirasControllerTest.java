package projetofinal.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projetofinal.model.Usuario;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class GerenciarCarteirasControllerTest {

    private GerenciarCarteirasController controller;

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
        controller = new GerenciarCarteirasController();

        injetarComponente(controller, "campoNomeNovaCarteira", new TextField());
        injetarComponente(controller, "mensagemErro", new Label());
        injetarComponente(controller, "listaCarteiras", new ListView<String>());
    }

    private void injetarComponente(Object alvo, String nomeCampo, Object componente) throws Exception {
        Field field = alvo.getClass().getDeclaredField(nomeCampo);
        field.setAccessible(true);
        field.set(alvo, componente);
    }
    
    private Object obterCampo(Object alvo, String nomeCampo) throws Exception {
        Field field = alvo.getClass().getDeclaredField(nomeCampo);
        field.setAccessible(true);
        return field.get(alvo);
    }

    @Test
    public void setUsuarioLogado_DeveAtualizarPlaceholderDaLista() throws Exception {
        Usuario usuarioMock = new Usuario("Teste", "teste@email.com", "senha123");
        
        // Ao setar o usuário logado, o método atualizarListaNaTela é chamado internamente[cite: 17].
        controller.setUsuarioLogado(usuarioMock); 
        
        ListView<String> lista = (ListView<String>) obterCampo(controller, "listaCarteiras");
        
        // Verifica se a label com a mensagem de "Nenhuma carteira cadastrada" foi configurada no Placeholder[cite: 17].
        assertNotNull(lista.getPlaceholder()); 
    }

    @Test
    public void limparMensagemErro_DeveLimparCampoTextoEOcultarLabel() throws Exception {
        TextField campoNome = (TextField) obterCampo(controller, "campoNomeNovaCarteira");
        campoNome.setText("Nome Existente");
        
        Label erro = (Label) obterCampo(controller, "mensagemErro");
        erro.setVisible(true); // Simula que a mensagem de erro estava aparecendo

        // O evento de MouseEvent não é utilizado internamente, então podemos passar null seguramente[cite: 17].
        controller.limparMensagemErro(null); 

        // Confirma se o texto foi apagado e a mensagem sumiu[cite: 17].
        assertTrue(campoNome.getText().isEmpty());
        assertFalse(erro.isVisible());
    }
}