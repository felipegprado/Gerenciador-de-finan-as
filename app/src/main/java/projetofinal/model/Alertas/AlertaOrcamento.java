package projetofinal.model.Alertas;

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
        // TODO: Mudar para o JavaFX quando a view estiver pronta
        System.out.println("Notificacao: " + msg);
    }

    @Override
    public void disparar() {
        enviarNotificacao(getMensagem() + " | Limite: " + valorLimite + " | Total gasto: " + valorAtual);
    }

    // Métodos de acesso exigidos no UML
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