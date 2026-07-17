package projetofinal.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projetofinal.model.Carteira;
import projetofinal.model.Usuario;
import projetofinal.repository.UsuarioRepository;

/**
 * Controller da tela do gereciador de carteiras
 * 
 * @see gerenciarCarteiras.fxml
 *      GerenciarCarteirasController
 */
public class GerenciarCarteirasController {

    @FXML
    private TextField campoNomeNovaCarteira;

    @FXML
    private Label mensagemErro;

    @FXML
    private ListView<String> listaCarteiras;

    private Usuario usuarioAtual;
    private UsuarioRepository repositorio = new UsuarioRepository();

    /**
     * Recebe o usuário da tela principal e já preenche a lista na tela
     */
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioAtual = usuario;
        atualizarListaNaTela();
    }

    /**
     * Método para mostrar todas as carteiras do usuário.
     */
    private void atualizarListaNaTela() {
        Label mensagemVazia = new Label("Nenhuma carteira cadastrada ainda.\nCrie uma nova carteira ao lado!");

        /*
         * posso colocar direto no css depois para deixar mais organizado as
         * responsabilidades
         */
        mensagemVazia.setStyle(
                "-fx-font-size: 14px; -fx-text-fill: #4a4242; -fx-alignment: center; -fx-text-alignment: center;");
        ObservableList<String> nomesDasCarteiras = FXCollections.observableArrayList();
        listaCarteiras.setPlaceholder(mensagemVazia); // caso não tenha nenhuma carteira cadastrada.

        if (usuarioAtual.getCarteiras() != null) {
            for (Carteira c : usuarioAtual.getCarteiras()) {
                nomesDasCarteiras.add(c.getNome());
            }
        }

        listaCarteiras.setItems(nomesDasCarteiras);
    }

    /**
     * Cria a carteira, adiciona ao usuário, atualiza a tela e salva
     * 
     * @param event atributo que pega o clique do Mouse (javaFX)
     */
    @FXML
    void criarNovaCarteira(ActionEvent event) {
        String nome = campoNomeNovaCarteira.getText();

        if (nome != null && !nome.trim().isEmpty()) {

            if (usuarioAtual.temCarteiraComNome(nome)) {
                mensagemErro.setVisible(true);
                return;
            }
            Carteira novaCarteira = new Carteira(nome.trim());
            usuarioAtual.adicionarCarteira(novaCarteira);

            /* aqui eu salvo a mudança no json */
            repositorio.atualizarUsuario(usuarioAtual);

            campoNomeNovaCarteira.clear();
            atualizarListaNaTela();
        }
    }

    @FXML
    public void limparMensagemErro(MouseEvent event) {
        campoNomeNovaCarteira.clear();
        mensagemErro.setVisible(false);
    }

    /**
     * Método para gerenciar o botão de remover a carteira do usuario.
     * 
     * @param event atributo que pega o clique do Mouse (javaFX)
     */
    @FXML
    void removerCarteira(ActionEvent event) {
        String nomeSelecionado = listaCarteiras.getSelectionModel().getSelectedItem();

        if (nomeSelecionado != null) {
            usuarioAtual.removeCarteira(nomeSelecionado);
            repositorio.atualizarUsuario(usuarioAtual);
            atualizarListaNaTela();
        }
        System.out.println("entrou aqui");
    }

    /**
     * Método para controlar o Botão para voltar a tela principal.
     * 
     * @param event atributo que pega o clique do Mouse (javaFX)
     */
    @FXML
    void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetofinal/telaPrincipal.fxml"));
            Parent novaTela = loader.load();

            /* tenho que mandar o usuário de novo pq o JavaFX reconstroi a tela do zero */
            TelaPrincipalController controller = loader.getController();
            controller.setUsuario(usuarioAtual);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(novaTela));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}