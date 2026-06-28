package projetofinal.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Label cadastro;

    @FXML
    private TextField campoEmail;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private TextFlow mensagemLogin;

    @FXML
    private AnchorPane mudarSenha;

    @FXML
    void alteradorSenha(MouseEvent event) {

    }

    @FXML
    void botaoLogin(ActionEvent event) { // o Action event nada mais é o evento do botão ser clicado
        System.out.println("o botão foi clicado !!!!!");

        String usuario = campoEmail.getText();
        String senha = campoSenha.getText();

        if (usuario.equals("brasil") && senha.equals("pqd")) {
            try {
                    String caminho = "/projetofinal/telaPrincipal.fxml";
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
                    Parent novaTela = loader.load(); // cria um objeto parent que representa a minha tela
    
                    //Obtém a janela (Stage) atual através do clique do botão
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // defato a tela do sistema operacional
    
                    // Muda a cena da janela
                    stage.setScene(new Scene(novaTela));
                    stage.show();
                    
                } catch (Exception e) {
                    // Se der erro, ele imprime o motivo no console (essencial para testar!)
                    e.printStackTrace();
                }

        } else {
            mensagemLogin.setVisible(true);
            campoEmail.setText("");
            campoSenha.setText("");
        }

    }

    @FXML
    void criarUsuario(MouseEvent event) {

    }

    @FXML
    void limparMensagemLogin(MouseEvent evento) {
        mensagemLogin.setVisible(false);
    }

}