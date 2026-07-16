package projetofinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import projetofinal.model.Usuario;

public class TelaPrincipalController {

    @FXML
    private Button botaoCarteirasDisponiveis;

    @FXML
    private Button botaoGerenciarCarteiras;

    @FXML
    private Button botaoGraficos;

    @FXML
    private Button botaoHistorico;

    @FXML
    private Button botaoSair;

    @FXML
    private Label saldoUsuario;

    /** Atributo para armazenar o usuário que acabou de entrar*/
    private Usuario usuarioAtual;

    public void setUsuario(Usuario usuario) {
        this.usuarioAtual = usuario;
        this.atualizaSaldo();
    }

    private void atualizaSaldo() {
        saldoUsuario.setText(String.format("R$ %.2f", usuarioAtual.getSaldoTotal()));
    }

    @FXML
    void sairPagina(ActionEvent event) {

        try {
            String caminho = "/projetofinal/login.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));

            Parent novaTela = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(novaTela));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
