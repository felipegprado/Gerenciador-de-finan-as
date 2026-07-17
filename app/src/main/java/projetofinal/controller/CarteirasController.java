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
import javafx.stage.Stage;
import projetofinal.model.Carteira;
import projetofinal.model.Usuario;

/**
 * Controller para a tela de carteiras
 * 
 * @see carteiras.fxml
 *      CarteirasController
 */
public class CarteirasController {

    @FXML
    private ListView<String> listaCarteiras;

    private Usuario usuarioAtual;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioAtual = usuario;
        atualizarLista();
    }

    @FXML
    void abrirCarteiraSelecionada(ActionEvent event) {
        String nomeSelecionado = listaCarteiras.getSelectionModel().getSelectedItem();

        if (nomeSelecionado != null) {
            Carteira carteiraEscolhida = null;
            for (Carteira c : usuarioAtual.getCarteiras()) {
                if (c.getNome().equals(nomeSelecionado)) {
                    carteiraEscolhida = c;
                    break;
                }
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetofinal/menuCarteira.fxml"));
                Parent novaTela = loader.load();

                // Obter o controlador da nova tela e passar os dados
                MenuCarteiraController controller = loader.getController();
                controller.setDados(usuarioAtual, carteiraEscolhida);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(novaTela));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetofinal/telaPrincipal.fxml"));
            Parent novaTela = loader.load();

            TelaPrincipalController controller = loader.getController();
            controller.setUsuario(usuarioAtual);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(novaTela));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void atualizarLista() {
        Label mensagemVazia = new Label(
                "Nenhuma carteira cadastrada ainda.\nCrie uma carteira em Gerenciar Carteiras !!");

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

}
