package projetofinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import projetofinal.model.Carteira;
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

        // Futuro: carregar listaTransacoes com as transações desta carteira
    }

    @FXML
    void abrirCadastroTransacao(ActionEvent event) {
        // Lógica para abrir tela de Adicionar Gasto/Ganho
    }

    @FXML
    void abrirGatilhos(ActionEvent event) {
        // Lógica para configurar Alertas
    }

    @FXML
    void voltar(ActionEvent event) {
        // Lógica para voltar à tela de Carteiras Disponíveis
    }
}