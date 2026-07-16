package projetofinal.controller;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import projetofinal.model.Usuario;

public class TelaPrincipalControllerTest {

    private TelaPrincipalController controller;

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
        controller = new TelaPrincipalController();

        // Injeta a Label que será atualizada na tela principal
        Field field = controller.getClass().getDeclaredField("saldoUsuario");
        field.setAccessible(true);
        field.set(controller, new Label());
    }

    @Test
    public void setUsuario_DeveAtualizarLabelDeSaldoNaTela() throws Exception {
        // Cria um usuário anônimo simulando um saldo de R$ 1500,00 para facilitar o teste
        Usuario usuarioMock = new Usuario("Arthur", "arthur@email.com", "123") {
            @Override
            public double getSaldoTotal() {
                return 1500.0;
            }
        };

        // A chamada do setUsuario invoca o atualizaSaldo internamente[cite: 20]
        controller.setUsuario(usuarioMock);

        // Recupera a label injetada para conferir o resultado
        Field field = controller.getClass().getDeclaredField("saldoUsuario");
        field.setAccessible(true);
        Label labelSaldo = (Label) field.get(controller);

        // Verifica se a formatação foi aplicada corretamente na label[cite: 20]
        assertEquals("R$ 1500.00", labelSaldo.getText());
        assertEquals(usuarioMock, controller.getUsuario());
    }
}