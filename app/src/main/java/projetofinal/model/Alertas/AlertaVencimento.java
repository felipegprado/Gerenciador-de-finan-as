package projetofinal.model.Alertas;

import java.time.LocalDate;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertaVencimento extends Alerta {
    private LocalDate dataVencimento;

    public AlertaVencimento(String mensagem, LocalDate dataVencimento) {
        super(mensagem);
        this.dataVencimento = dataVencimento;
    }

    @Override
    public boolean verificarGatilho() {
        if (!isAtivo()) {
            return false;
        }

        LocalDate hoje = LocalDate.now();
        return hoje.isEqual(dataVencimento) || hoje.isAfter(dataVencimento);
    }

    @Override
    public void enviarNotificacao(String msg) {
        Alert janelaAviso = new Alert(AlertType.WARNING);
        janelaAviso.setTitle("Alerta de Vencimento");
        janelaAviso.setHeaderText("Atenção! sua conta está prestes a vencer!");
        janelaAviso.setContentText(msg);
        
        // Mostra a janela na tela
        janelaAviso.showAndWait();
    }

    @Override
    public void disparar() {
        enviarNotificacao(getMensagem() + "\nVence em: " + dataVencimento.toString());
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}