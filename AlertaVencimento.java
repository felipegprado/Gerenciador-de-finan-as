import java.time.LocalDate;

public class AlertaVencimento extends Alerta implements Notificavel {
    private LocalDate dataVencimento;

    public AlertaVencimento(String mensagem, LocalDate dataVencimento) {
        super(mensagem);
        this.dataVencimento = dataVencimento;
    }

    @Override
    public boolean verificarGatilho() {
        // Dispara se a data de hoje for igual ou depois do vencimento e o alerta ainda estiver ativo
        return isAtivo() && (LocalDate.now().isEqual(dataVencimento) || LocalDate.now().isAfter(dataVencimento));
    }

    @Override
    public void enviarNotificacao(String mensagem) {
        System.out.println("[ALERTA DE CONTA] " + mensagem);
    }

    @Override
    public void disparar() {
        enviarNotificacao(getMensagem() + " -> Venceu em: " + dataVencimento);
    }
}