package projetofinal.model.Alertas;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertaOrcamento extends Alerta {
    private double valorLimite;
    private double valorAtual;

    public AlertaOrcamento(String mensagem, double valorLimite) {
        super(mensagem);
        this.valorLimite = valorLimite;
        this.valorAtual = 0.0;
    }

    public void atualizarValorAtual(double novoValor) {
        this.valorAtual += novoValor;

        if (verificarGatilho()) {
            disparar();
        }
    }

    @Override
    public boolean verificarGatilho() {
        if (!isAtivo()) {
            return false;
        }
        return this.valorAtual >= this.valorLimite;
    }

    @Override
    public void enviarNotificacao(String msg) {
        Alert janelaAviso = new Alert(AlertType.WARNING);
        janelaAviso.setTitle("Alerta de Orçamento");
        janelaAviso.setHeaderText("Atenção! Orçamento Estourado!");
        janelaAviso.setContentText(msg);
        janelaAviso.showAndWait();
    }

    @Override
    public void disparar() {
        enviarNotificacao(getMensagem() + "\nLimite: R$ " + valorLimite + "\nTotal gasto: R$ " + valorAtual);
    }

    public double getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(double valorLimite) {
        this.valorLimite = valorLimite;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }
}