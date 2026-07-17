package projetofinal.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import projetofinal.model.Carteira;
import projetofinal.model.Usuario;
import projetofinal.model.Transações.Ganho;
import projetofinal.model.Transações.Gasto;
import projetofinal.model.Transações.Transacao;

public class GraficosController {

    @FXML
    private PieChart graficoPizza;

    @FXML
    private BarChart<String, Number> graficoBarras;

    @FXML
    private ComboBox<String> comboFiltroCarteira;

    @FXML
    private LineChart<String, Number> graficoLinha;

    private Usuario usuarioAtual;

    /***
     * Método para inserir os dados da outra tela
     * @param usuario usuario logado no momento
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioAtual = usuario;
        comboFiltroCarteira.getItems().add("Todas as Carteiras");
        for (Carteira c : usuario.getCarteiras()) {
            comboFiltroCarteira.getItems().add(c.getNome());
        }

        comboFiltroCarteira.setValue("Todas as Carteiras");
        atualizarGraficos();
    }

    /**
     * Este método é acionado toda vez que o usuário muda o valor do ComboBox.
     * Basicamente vamos usar os gráficos do Javafx
     */
    @FXML
    void atualizarGraficos() {
        /* primeiro temos que limpar os dados */
        graficoPizza.getData().clear();
        graficoBarras.getData().clear();

        String selecao = comboFiltroCarteira.getValue();

        double ganhosTotais = 0.0;
        double gastosTotais = 0.0;

        ObservableList<PieChart.Data> dadosPizza = FXCollections.observableArrayList();

        /* modo como vamos filtrar as carteiras */
        if (selecao.equals("Todas as Carteiras")) {
            for (Carteira c : usuarioAtual.getCarteiras()) {
                double saldo = c.calcularSaldo();
                if (saldo > 0) {
                    dadosPizza.add(new PieChart.Data(c.getNome(), saldo));
                }
                // Soma para o gráfico de barras
                ganhosTotais += calcularTotalTipo(c, true);
                gastosTotais += calcularTotalTipo(c, false);
            }
            graficoPizza.setTitle("Saldo Distribuído entre Carteiras");
            graficoBarras.setTitle("Fluxo Total (Todas as Carteiras)");

        } else {
            /* caso seja uma carteira específica */
            for (Carteira c : usuarioAtual.getCarteiras()) {
                if (c.getNome().equals(selecao)) {
                    ganhosTotais = calcularTotalTipo(c, true);
                    gastosTotais = calcularTotalTipo(c, false);

                    /* Para apenas uma carteira a gente faz gastos vs ganhos */
                    dadosPizza.add(new PieChart.Data("Ganhos", ganhosTotais));
                    dadosPizza.add(new PieChart.Data("Gastos", gastosTotais));

                    graficoPizza.setTitle("Proporção na Carteira: " + c.getNome());
                    graficoBarras.setTitle("Fluxo da Carteira: " + c.getNome());
                    break;
                }
            }
        }

        /* lógica para calcular a porcentagem da fatia de pizza */
        double totalPizza = 0.0;
        for (PieChart.Data dado : dadosPizza) {
            totalPizza += dado.getPieValue();
        }

        if (totalPizza > 0) {
            for (PieChart.Data dado : dadosPizza) {
                double porcentagem = (dado.getPieValue() / totalPizza) * 100;
                String nomeComPorcentagem = String.format("%s (%.1f%%)", dado.getName(), porcentagem);
                dado.setName(nomeComPorcentagem);
            }
        }

        graficoPizza.setData(dadosPizza);
        XYChart.Series<String, Number> serieGanhos = new XYChart.Series<>();
        serieGanhos.setName("Entradas");
        serieGanhos.getData().add(new XYChart.Data<>("Ganhos", ganhosTotais));

        XYChart.Series<String, Number> serieGastos = new XYChart.Series<>();
        serieGastos.setName("Saídas");
        serieGastos.getData().add(new XYChart.Data<>("Gastos", gastosTotais));

        graficoBarras.getData().add(serieGanhos);
        graficoBarras.getData().add(serieGastos);
    }

    /**
     * Método auxiliar para somar apenas ganhos ou apenas gastos de uma carteira.
     * 
     * @param isGanho true se quiser somar ganhos, false se quiser somar gastos.
     */
    private double calcularTotalTipo(Carteira carteira, boolean isGanho) {
        double total = 0.0;
        for (Transacao t : carteira.getTransacoes()) {
            if (isGanho && t instanceof Ganho) {
                total += t.getValor();
            } else if (!isGanho && t instanceof Gasto) {
                total += Math.abs(t.getValor());
            }
        }
        return total;
    }
    /**
     * Método para voltar para a tela principal
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