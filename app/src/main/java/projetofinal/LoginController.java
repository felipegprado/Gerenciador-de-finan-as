package projetofinal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    // Liga o campo de texto do FXML a esta variável Java
    @FXML
    private TextField txLogin;

    // Liga o campo de senha do FXML a esta variável Java
    @FXML
    private PasswordField txSenha;

    // Liga os botões do FXML a estas variáveis Java
    @FXML
    private Button btEntrar;

    @FXML
    private Button btSair;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        // Este método roda automaticamente assim que a interface é carregada.
        // Perfeito para configurações iniciais da tela.
    }

    // Método disparado quando o botão "Entrar" for clicado
    @FXML
    public void entrar(ActionEvent event) {
        String usuario = txLogin.getText();
        String senha = txSenha.getText();

        System.out.println("--- Tentativa de Login ---");
        System.out.println("Utilizador: " + usuario);
        System.out.println("Senha: " + senha);

        // Exemplo básico de validação no terminal
        if (usuario.equals("admin") && senha.equals("1234")) {
            System.out.println("Login efetuado com sucesso!");
        } else {
            System.out.println("Utilizador ou senha incorretos.");
        }
    }

    // Método disparado quando o botão "Sair" for clicado
    @FXML
    public void sair(ActionEvent event) {
        System.out.println("A fechar a aplicação da loja GolFX...");
        System.exit(0); // Encerra o programa de forma limpa
    }
}