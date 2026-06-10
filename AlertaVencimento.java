import java.time.LocalDate;

public class AlertaVencimento extends Alerta implements Notificavel {
    
    private LocalDate dataVencimento;

    public AlertaVencimento(String msg, LocalDate data) {
        super(msg);
        this.dataVencimento = data;
    }

    @Override
    public boolean verificarGatilho() {
        if (isAtivo() == false) {
            return false;
        }

        LocalDate hoje = LocalDate.now();
        
        // Verifica se a data de hj é igual ou já passou do vencimento
        if (hoje.isEqual(dataVencimento) || hoje.isAfter(dataVencimento)) {
            return true;
        }
        
        return false;
    }

    @Override
    public void enviarNotificacao(String msg) {
        System.out.println("[ALERTA] " + msg);
    }

    @Override
    public void disparar() {
        enviarNotificacao(getMensagem() + " - Vence em: " + dataVencimento.toString());
    }
}