package projetofinal.controller;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import projetofinal.model.Usuario;
import projetofinal.model.Transações.Transacao;

public class TelaPrincipalControllerTest {

    private TelaPrincipalController controller;

    // Inicializa o ambiente do JavaFX necessário para os testes
    @BeforeAll
    public static void initJFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // O Toolkit já foi iniciado
        }
    }

    @BeforeEach
    public void setup() throws Exception {
        controller = new TelaPrincipalController();

        // Injeta a Label do saldo que será atualizada na tela principal
        Field fieldSaldo = controller.getClass().getDeclaredField("saldoUsuario");
        fieldSaldo.setAccessible(true);
        fieldSaldo.set(controller, new Label());

        // Injeta a ListView do histórico para evitar NullPointerException durante o carregamento
        Field fieldLista = controller.getClass().getDeclaredField("listaHistorico");
        fieldLista.setAccessible(true);
        fieldLista.set(controller, new ListView<Transacao>());
    }

    @Test
    public void setUsuario_DeveAtualizarLabelDeSaldoNaTela() throws Exception {
        // Cria um usuário anônimo simulando um saldo de R$ 1500,00
        Usuario usuarioMock = new Usuario("Arthur", "arthur@email.com", "123") {
            @Override
            public double getSaldoTotal() {
                return 1500.0;
            }
        };

        // A chamada invoca as formatações internamente
        controller.setUsuario(usuarioMock);

        // Recupera a label injetada para conferir o resultado
        Field field = controller.getClass().getDeclaredField("saldoUsuario");
        field.setAccessible(true);
        Label labelSaldo = (Label) field.get(controller);

        // Utilizamos o String.format para acompanhar o idioma padrão da máquina (evitando erros de ponto/vírgula)
        String saldoEsperado = String.format("R$ %.2f", 1500.0);
        
        assertEquals(saldoEsperado, labelSaldo.getText());
        assertEquals(usuarioMock, controller.getUsuario());
    }
}