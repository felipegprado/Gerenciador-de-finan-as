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
import javafx.stage.Stage;
import projetofinal.model.Carteira;
import projetofinal.model.Usuario;
import projetofinal.model.Transações.Transacao;

import java.util.List;

/**
 * Controller para a tela de Busca
 * 
 * @see BuscaTransacoes.fxml
 *      BuscaTransacoesController
 */
public class BuscaTransacoesController {

    @FXML
    private TextField campoBusca;

    @FXML
    private ListView<String> listaResultados;

    private Usuario usuarioAtual;

    /**
     * Método setter para modificar os atributos do controller
     * 
     * @param usuario usuario atual no sistema.
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioAtual = usuario;
        Label mensagemVazia = new Label("Digite um termo acima e clique em 'Pesquisar'.");
        mensagemVazia.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a4242;");
        listaResultados.setPlaceholder(mensagemVazia);
    }

    /**
     * Método para fazer a pesquisa usando a interface Pesquisável
     * 
     * @see Pesquisavel
     * @param event botão (javaFX)
     */
    @FXML
    void pesquisar(ActionEvent event) {
        String termo = campoBusca.getText();

        if (termo == null || termo.trim().isEmpty()) {
            return;
        }

        ObservableList<String> resultadosVisuais = FXCollections.observableArrayList();

        if (usuarioAtual.getCarteiras() != null) {
            for (Carteira carteira : usuarioAtual.getCarteiras()) {
                List<Transacao> encontradas = carteira.fazerBusca(termo);
                for (Transacao t : encontradas) {
                    String linha = String.format("[%s] %s | %s | R$ %.2f",
                            carteira.getNome(), t.getData().toString(), t.getDescricao(), t.getValor());
                    resultadosVisuais.add(linha);
                }
            }
        }

        if (resultadosVisuais.isEmpty()) {
            resultadosVisuais.add("Nenhuma transação encontrada para: " + termo);
        }

        listaResultados.setItems(resultadosVisuais);
    }

    /**
     * Método para retornar a tela principal
     * 
     * @param event botão (javaFX)
     */
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
}
