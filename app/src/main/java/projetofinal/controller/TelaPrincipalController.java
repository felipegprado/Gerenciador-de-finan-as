package projetofinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
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

    @FXML
    private StackPane segundoBotaocarteirasDisponiveis;

    /** Atributo para armazenar o usuário que acabou de entrar */
    private Usuario usuarioAtual;

    /**
     * Método para pegar o usuário que acabou de fazer o login, assim,
     * mantenho o objeto após ter sido lido o meu Json
     * 
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
     * Método para carregar a tela de Gerenciar Carteiras
     * 
     * @see gerenciadorCarteiras.fxml
     * @param stageAtual
     */
    private void navegarParaGerenciadorCarteiras(Stage stageAtual) {
        try {
            String caminho = "/projetofinal/gerenciadorCarteiras.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent novaTela = loader.load();

            GerenciarCarteirasController controller = loader.getController();
            controller.setUsuarioLogado(this.usuarioAtual);

            stageAtual.setScene(new Scene(novaTela));
            stageAtual.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navegarParaCarteirasDisponiveis(Stage stageAtual) {
        try {
            String caminho = "/projetofinal/carteiras.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent novaTela = loader.load();

            CarteirasController controller = loader.getController();
            controller.setUsuarioLogado(this.usuarioAtual);

            stageAtual.setScene(new Scene(novaTela));
            stageAtual.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para gerenciar o botão que volta para a tela de Login.
     * 
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Método para gerenciar o botão que entra na tela do Gerenciador de Carteiras
     * 
     * @see gerenciadorCarteiras.fxml
     * @param event atributo que pega o clique do Mouse (javaFX)
     */
    @FXML
    void EntrarGerenciadorCarteiras(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navegarParaGerenciadorCarteiras(stage);

    }

    /**
     * Método identico ao do botão
     * @see EntrarGerenciadorCarteiras
     *      ,mas tive que adpatar porque não usei um botão aqui.
     * @param event
     */
    @FXML
    void EntrarGerenciadorCarteirasClicado(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navegarParaGerenciadorCarteiras(stage);
    }

    @FXML
    void EntrarCarteirasDisponiveis(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navegarParaCarteirasDisponiveis(stage);
    }

}
