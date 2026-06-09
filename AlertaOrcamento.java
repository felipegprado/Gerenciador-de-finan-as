public class AlertaOrcamento extends Alerta implements Notificavel {
    private double valorLimite;
    private double valorAtual;

    public AlertaOrcamento(String mensagem, double valorLimite) {
        super(mensagem);
        this.valorLimite = valorLimite;
        this.valorAtual = 0.0;
    }

    // Atualiza quanto o usuário já gastou nessa categoria
    public void atualizarValorAtual(double novoValor) {
        this.valorAtual = novoValor;
        // Se bateu o gatilho, já avisa na hora
        if (verificarGatilho()) {
            disparar();
        }
    }

    @Override
    public boolean verificarGatilho() {
        // Dispara se o gasto atual passar do limite definido
        return isAtivo() && (this.valorAtual >= this.valorLimite);
    }

    @Override
    public void enviarNotificacao(String mensagem) {
        // Aqui no console é só um print, mas depois vai pro JavaFX
        System.out.println("[NOTIFICAÇÃO DE SISTEMA] " + mensagem);
    }

    @Override
    public void disparar() {
        enviarNotificacao(getMensagem() + " | Limite: R$ " + valorLimite + " | Gasto atual: R$ " + valorAtual);
    }
}