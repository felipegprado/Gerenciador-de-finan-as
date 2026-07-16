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

    /**
     * Método para pegar o usuário que acabou de fazer o login, assim,
     * mantenho o objeto após ter sido lido o meu Json
     * @param usuario objeto que representa o usuário atual.
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioAtual = usuario;
        this.atualizaSaldo();
    }

    public Usuario getUsuario() {
        return usuarioAtual;
    }

    /** Método apenas para mostrar a mensagem do saldo total na tela */
    private void atualizaSaldo() {
        saldoUsuario.setText(String.format("R$ %.2f", usuarioAtual.getSaldoTotal()));
    }

    /**
     * Método para gerenciar  o botão que volta para a tela de Login.
     * @see login.fxml
     * @param event atributo que pega o clique do Mouse (javaFX)
     */
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

    /**
     * Método para gerenciar o botão que entra na tela do Gerenciador de Carteiras
     * @see gerenciadorCarteiras.fxml
     * @param event atributo que pega o clique do Mouse (javaFX)
     */
    @FXML
    void EntrarGerenciadorCarteiras(ActionEvent event) {

            try {
        String caminho = "/projetofinal/gerenciadorCarteiras.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));

        Parent novaTela = loader.load();
        GerenciarCarteirasController controller = loader.getController();
        controller.setUsuarioLogado(usuarioAtual);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(novaTela));
        stage.show();
    } catch(Exception e) {
        e.printStackTrace();
    }

    }

}
