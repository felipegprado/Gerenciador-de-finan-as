package projetofinal.controller;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projetofinal.model.Carteira;
import projetofinal.model.Usuario;
import projetofinal.model.Exceções.OrcamentoEstouradoException;
import projetofinal.model.Transações.Ganho;
import projetofinal.model.Transações.Gasto;
import projetofinal.model.Transações.Transacao;
import projetofinal.repository.UsuarioRepository;

public class CadastroTransacaoController {
    @FXML
    private TextField campoDescricao;

    @FXML
    private TextField campoValor;

    @FXML
    private DatePicker campoData;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private Label mensagemStatus;

    private Carteira carteiraAtual;

    private Usuario usuarioAtual; /* preciso disso para reconstruir a tela anterior */

    private UsuarioRepository repositorio = new UsuarioRepository();

    @FXML
    private TextField campoTags;

    /**
     * Este método é chamado automaticamente quando a tela é carregada.
     * Vamos usá-lo para preencher as opções do nosso ComboBox.
     */
    @FXML
    public void initialize() {
        // Adiciona as opções "Ganho" e "Gasto" no menu suspenso
        comboTipo.getItems().addAll("Ganho", "Gasto");
    }

    /**
     * Método para receber a carteira selecionada da tela anterior.
     * 
     * @param carteira A carteira onde a transação será adicionada.
     */
    public void setDados(Carteira carteira, Usuario usuario) {
        this.carteiraAtual = carteira;
        this.usuarioAtual = usuario;
    }

    /**
     * Método chamado quando o usuário clica no botão "Cadastrar Transação".
     */
    @FXML
    void cadastrarTransacao(ActionEvent event) {
        try {
            String descricao = campoDescricao.getText();
            String valorTexto = campoValor.getText();
            LocalDate dataSelecionada = campoData.getValue();
            String tipo = comboTipo.getValue();

            if (descricao.isEmpty() || valorTexto.isEmpty() || dataSelecionada == null || tipo == null) {
                exibirMensagemErro("Por favor, preencha todos os campos!");
                return;
            }

            double valor = Double.parseDouble(valorTexto.replace(",", "."));

            String textoTags = campoTags.getText();
            java.util.List<String> listaTags = new java.util.ArrayList<>();

            if (textoTags != null && !textoTags.trim().isEmpty()) {
                String[] tagsSeparadas = textoTags.split(",");

                for (String tag : tagsSeparadas) {
                    listaTags.add(tag.trim());
                }
            }
            /*lógica de adicinonar a transação */
            Transacao novaTransacao;

            if (tipo.equals("Ganho")) {
                novaTransacao = new Ganho(valor, descricao, dataSelecionada.toString(), listaTags, "Não informada");
            } else {
                novaTransacao = new Gasto(valor, descricao, dataSelecionada.toString(), listaTags, "Único",
                        "Não informado");
            }

            if (carteiraAtual != null) {
                try {
                    carteiraAtual.adicionarTransacao(novaTransacao);

                    exibirMensagemSucesso("Transação cadastrada com sucesso!");
                    repositorio.atualizarUsuario(usuarioAtual);
                    limparFormulario();
                    campoTags.clear();

                } catch (OrcamentoEstouradoException erroOrcamento) {
                    exibirMensagemErro(erroOrcamento.getMessage());
                }
            } else {
                exibirMensagemErro("Erro: Nenhuma carteira selecionada.");
            }

        } catch (NumberFormatException e) {
            exibirMensagemErro("O valor deve ser um número válido!");
        }
    }

    /**
     * Método para voltar ao menu Carteira.
     */
    @FXML
    void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetofinal/menuCarteira.fxml"));
            Parent novaTela = loader.load();

            MenuCarteiraController controller = loader.getController();
            controller.setDados(usuarioAtual, carteiraAtual);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(novaTela));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exibirMensagemErro(String msg) {
        mensagemStatus.setText(msg);
        mensagemStatus.setStyle("-fx-text-fill: red;");
        mensagemStatus.setVisible(true);
    }

    private void exibirMensagemSucesso(String msg) {
        mensagemStatus.setText(msg);
        mensagemStatus.setStyle("-fx-text-fill: green;");
        mensagemStatus.setVisible(true);
    }

    private void limparFormulario() {
        campoDescricao.clear();
        campoValor.clear();
        campoData.setValue(null);
        comboTipo.setValue(null);
    }
}