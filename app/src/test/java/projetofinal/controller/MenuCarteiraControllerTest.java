package projetofinal.controller;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import projetofinal.model.Carteira;
import projetofinal.model.Usuario;

public class MenuCarteiraControllerTest {

    private MenuCarteiraController controller;

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
        controller = new MenuCarteiraController();

        injetarComponente(controller, "labelNomeCarteira", new Label());
        injetarComponente(controller, "labelSaldoCarteira", new Label());
        injetarComponente(controller, "listaTransacoes", new ListView<String>());
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
    public void setDados_DevePreencherLabelsComNomeESaldoDaCarteira() throws Exception {
        Usuario usuario = new Usuario("Teste", "teste@teste", "123");
        
        // Simula uma carteira com saldo fixo para o teste
        Carteira carteiraMock = new Carteira("Carteira Viagem") {
            @Override
            public double calcularSaldo() {
                return 250.50;
            }
        };

        // Chama o método que formata os dados na tela[cite: 19]
        controller.setDados(usuario, carteiraMock);

        Label labelNome = (Label) obterCampo(controller, "labelNomeCarteira");
        Label labelSaldo = (Label) obterCampo(controller, "labelSaldoCarteira");

        // Verifica se os valores corretos foram inseridos nos componentes visuais[cite: 19]
        assertEquals("Carteira Viagem", labelNome.getText());
        assertEquals(String.format("Saldo: R$ %.2f", 250.50), labelSaldo.getText());
    }
}