package projetofinal.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import projetofinal.model.Carteira;
import projetofinal.model.Ganho;
import projetofinal.model.Gasto;
import projetofinal.model.Transacao;
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

    @FXML
    private ListView<Transacao> listaHistorico;

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
        this.carregarHistorico();
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

    private void navegarParaGraficos(Stage stageAtual) {
        try {
            String caminho = "/projetofinal/graficos.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent novaTela = loader.load();

            GraficosController controller = loader.getController();
            controller.setUsuario(this.usuarioAtual);

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
     * Método identico ao do botão ,mas tive que adpatar porque não usei um botão
     * aqui.
     * 
     * @see EntrarGerenciadorCarteiras
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

    @FXML
    void EntrarCarteirasDisponiveisClicado(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navegarParaCarteirasDisponiveis(stage);

    }

    @FXML
    void EntrarGraficos(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navegarParaGraficos(stage);
    }

    @FXML
    void EntrarGraficosClicado(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        navegarParaGraficos(stage);
    }

    /**
     * Método que tem a função de pegar todas as transações do usuário
     * e listar na tela principal em ordem cronologica.
     */
    private void carregarHistorico() {
        ObservableList<Transacao> historicoVisual = FXCollections.observableArrayList();
        List<Transacao> todasTransacoes = new ArrayList<>();

        if (usuarioAtual.getCarteiras() != null) {
            for (Carteira c : usuarioAtual.getCarteiras()) {
                if (c.getTransacoes() != null) {
                    todasTransacoes.addAll(c.getTransacoes());
                }
            }
        }

        todasTransacoes.sort((t1, t2) -> t2.getData().compareTo(t1.getData()));

        /* quero definir que vai ter no máixmo 7 operações */
        int limite = Math.min(todasTransacoes.size(), 7);
        for (int i = 0; i < limite; i++) {
            historicoVisual.add(todasTransacoes.get(i));
        }

        listaHistorico.setItems(historicoVisual);

        /* posso refatorar depois no arquivo css */
        listaHistorico.setCellFactory(listView -> new ListCell<Transacao>() {
            @Override
            protected void updateItem(Transacao item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;"); 
                } else {
                    String linha = String.format("%s  |  %s  |  R$ %.2f", 
                        item.getData().toString(), 
                        item.getDescricao(), 
                        item.getValor());
                    
                    setText(linha);
                    
                    if (item instanceof Ganho) {
                        setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold; -fx-background-color: transparent;"); // Verde
                    } else if (item instanceof Gasto) {
                        setStyle("-fx-text-fill: #F44336; -fx-font-weight: bold; -fx-background-color: transparent;"); // Vermelho
                    }
                }
            }
        });
    }

}
