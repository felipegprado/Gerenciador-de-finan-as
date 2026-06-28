package projetofinal.model;

import java.time.LocalDate;

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
        System.out.println("[ALERTA] " + msg);
    }

    @Override
    public void disparar() {
        enviarNotificacao(getMensagem() + " - Vence em: " + dataVencimento.toString());
    }

    // Métodos de acesso exigidos no UML
    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}