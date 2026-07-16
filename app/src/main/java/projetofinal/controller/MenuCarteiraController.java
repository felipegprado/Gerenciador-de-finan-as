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
import projetofinal.model.Transacao;
import projetofinal.model.Usuario;

public class MenuCarteiraController {

    @FXML
    private Label labelNomeCarteira;
    @FXML
    private Label labelSaldoCarteira;
    @FXML
    private ListView<String> listaTransacoes;

    private Carteira carteiraSelecionada;
    private Usuario usuarioAtual;

    /**
     * Método para pegarmos os dados da carteira selecionada
     * 
     * @param usuario  usuario atual
     * @param carteira carteira escolhida pelo usuário
     */
    public void setDados(Usuario usuario, Carteira carteira) {
        this.usuarioAtual = usuario;
        this.carteiraSelecionada = carteira;

        labelNomeCarteira.setText(carteira.getNome());
        labelSaldoCarteira.setText(String.format("Saldo: R$ %.2f", carteira.calcularSaldo()));
        atualizarListaTransacoes();
    }

    /**
     * Atualiza o ListView de transações. Se a lista estiver vazia,
     * exibe uma mensagem amigável para o usuário.
     */
    private void atualizarListaTransacoes() {
        Label mensagemVazia = new Label(
                "Nenhuma transação registrada nesta carteira.\nClique em 'Adicionar Transação' para novas adições!!");
        mensagemVazia.setStyle(
                "-fx-font-size: 14px; -fx-text-fill: #4a4242; -fx-alignment: center; -fx-text-alignment: center;");
        ObservableList<String> nomesDasTransacoes = FXCollections.observableArrayList();
        listaTransacoes.setPlaceholder(mensagemVazia);

        if (carteiraSelecionada.getTransacoes() != null) {
            for (Transacao t : carteiraSelecionada.getTransacoes()) {
                String linha = String.format("%s - R$ %.2f", t.getDescricao(), t.getValor());
                nomesDasTransacoes.add(linha);
            }
        }
        listaTransacoes.setItems(nomesDasTransacoes);
    }

    /**
     * Método para entrar na tela de transações.
     * 
     * @param event
     */
    @FXML
    void abrirCadastroTransacao(ActionEvent event) {
        Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            String caminho = "/projetofinal/cadastroTransacao.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Parent novaTela = loader.load();

            CadastroTransacaoController controller = loader.getController();
            controller.setDados(this.carteiraSelecionada, this.usuarioAtual);

            stageAtual.setScene(new Scene(novaTela));
            stageAtual.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirGatilhos(ActionEvent event) {
        // Lógica para configurar Alertas
    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetofinal/carteiras.fxml"));
            Parent novaTela = loader.load();

            CarteirasController controller = loader.getController();
            controller.setUsuarioLogado(usuarioAtual);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(novaTela));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}