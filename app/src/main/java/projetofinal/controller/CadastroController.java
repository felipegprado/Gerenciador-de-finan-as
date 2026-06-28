package projetofinal.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projetofinal.model.Usuario;
import projetofinal.repository.UsuarioRepository;

public class CadastroController {

    @FXML
    private Button botaoCadastrar;

    @FXML
    private TextField cadastroNome;

    @FXML
    private PasswordField cadastroSenha;

    @FXML
    private TextField cadastroUsuario;

    @FXML
    void criarCadastro(ActionEvent event) {
        String nome = cadastroNome.getText();
        String usuario = cadastroUsuario.getText();
        String senha = cadastroSenha.getText();

        // Validação
        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
            System.out.println("teste a ser implementado");
            return;
        }

        Usuario novo = new Usuario(nome, usuario, senha);

        UsuarioRepository arq = new UsuarioRepository();
        arq.cadastrarUsuario(novo);

        System.out.println("Usuário cadastrado com sucesso!");
        /* mesma lógica para ir na pagina de login */
        try {
            String caminho = "/projetofinal/login.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent novaTela = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(novaTela));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
