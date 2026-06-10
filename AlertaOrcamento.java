public class AlertaOrcamento extends Alerta implements Notificavel {
    private double valorLimite;
    private double valorAtual;

    public AlertaOrcamento(String msg, double limite) {
        super(msg);
        this.valorLimite = limite;
        this.valorAtual = 0.0;
    }

    public void atualizarValorAtual(double novoValor) {
        // Vai somando os gastos novos no valor atual
        this.valorAtual = this.valorAtual + novoValor;
        
        if (verificarGatilho() == true) {
            disparar();
        }
    }

    @Override
    public boolean verificarGatilho() {
        // Mais legível separar as checagens
        if (isAtivo() == false) {
            return false;
        }
        
        if (this.valorAtual >= this.valorLimite) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void enviarNotificacao(String msg) {
        // TODO: mudar pro JavaFX quando a interface estiver pronta
        System.out.println("Notificacao: " + msg);
    }

    @Override
    public void disparar() {
        enviarNotificacao(getMensagem() + " | Limite: " + valorLimite + " | Total gasto: " + valorAtual);
    }
}