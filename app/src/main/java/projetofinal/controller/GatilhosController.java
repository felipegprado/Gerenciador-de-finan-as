package projetofinal.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projetofinal.model.*;
import projetofinal.model.Alertas.Alerta;
import projetofinal.model.Alertas.AlertaOrcamento;
import projetofinal.model.Alertas.AlertaVencimento;
import projetofinal.repository.UsuarioRepository;

import java.time.LocalDate;

public class GatilhosController {

    @FXML private ListView<String> listaAlertas;
    @FXML private ComboBox<String> comboTipoAlerta;
    @FXML private TextField campoMensagem;
    
    @FXML private VBox caixaValor;
    @FXML private TextField campoValorLimite;
    
    @FXML private VBox caixaData;
    @FXML private DatePicker campoDataVencimento;
    
    @FXML private Label mensagemStatus;

    private Usuario usuarioAtual;
    private Carteira carteiraSelecionada;
    private UsuarioRepository repositorio = new UsuarioRepository();

    @FXML
    public void initialize() {
        comboTipoAlerta.getItems().addAll("Orçamento (Valor Limite)", "Vencimento (Data Limite)");
    }

    public void setDados(Usuario usuario, Carteira carteira) {
        this.usuarioAtual = usuario;
        this.carteiraSelecionada = carteira;
        atualizarListaAlertas();
    }

    /**
     * Mostra ou esconde os campos de Data ou Valor dependendo do que o usuário escolheu no ComboBox.
     */
    @FXML
    void aoMudarTipoAlerta(ActionEvent event) {
        String selecionado = comboTipoAlerta.getValue();
        
        if (selecionado != null) {
            if (selecionado.contains("Orçamento")) {
                caixaValor.setVisible(true);
                caixaValor.setManaged(true);
                caixaData.setVisible(false);
                caixaData.setManaged(false);
            } else if (selecionado.contains("Vencimento")) {
                caixaData.setVisible(true);
                caixaData.setManaged(true);
                caixaValor.setVisible(false);
                caixaValor.setManaged(false);
            }
        }
    }

    @FXML
    void salvarAlerta(ActionEvent event) {
        String tipo = comboTipoAlerta.getValue();
        String mensagem = campoMensagem.getText();

        if (tipo == null || mensagem.isEmpty()) {
            exibirMensagemErro("Preencha o tipo e a mensagem do alerta!");
            return;
        }

        try {
            Alerta novoAlerta = null;

            if (tipo.contains("Orçamento")) {
                String valorTexto = campoValorLimite.getText().replace(",", ".");
                double limite = Double.parseDouble(valorTexto);
                novoAlerta = new AlertaOrcamento(mensagem, limite);
                
            } else if (tipo.contains("Vencimento")) {
                LocalDate data = campoDataVencimento.getValue();
                if (data == null) {
                    exibirMensagemErro("Selecione uma data válida!");
                    return;
                }
                novoAlerta = new AlertaVencimento(mensagem, data);
            }

            if (novoAlerta != null) {
                carteiraSelecionada.adicionarAlerta(novoAlerta);
                repositorio.atualizarUsuario(usuarioAtual);
                
                exibirMensagemSucesso("Alerta cadastrado com sucesso!");
                limparFormulario();
                atualizarListaAlertas();
            }

        } catch (NumberFormatException e) {
            exibirMensagemErro("Digite um valor numérico válido para o limite!");
        }
    }

    private void atualizarListaAlertas() {
        ObservableList<String> alertasVisuais = FXCollections.observableArrayList();
        
        if (carteiraSelecionada.getAlertas() != null) {
            for (Alerta alerta : carteiraSelecionada.getAlertas()) {
                String info = "";
                if (alerta instanceof AlertaOrcamento) {
                    AlertaOrcamento a = (AlertaOrcamento) alerta;
                    info = String.format("[Orçamento] %s (Limite: R$ %.2f)", a.getMensagem(), a.getValorLimite());
                } else if (alerta instanceof AlertaVencimento) {
                    AlertaVencimento a = (AlertaVencimento) alerta;
                    info = String.format("[Vencimento] %s (Data: %s)", a.getMensagem(), a.getDataVencimento().toString());
                }
                alertasVisuais.add(info);
            }
        }
        
        if (alertasVisuais.isEmpty()) {
            alertasVisuais.add("Nenhum alerta configurado.");
        }
        
        listaAlertas.setItems(alertasVisuais);
    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetofinal/menuCarteira.fxml"));
            Parent novaTela = loader.load();
            MenuCarteiraController controller = loader.getController();
            controller.setDados(usuarioAtual, carteiraSelecionada);
            
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
        campoMensagem.clear();
        campoValorLimite.clear();
        campoDataVencimento.setValue(null);
    }
}