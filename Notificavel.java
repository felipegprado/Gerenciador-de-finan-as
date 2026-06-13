public interface Notificavel {
    boolean verificarGatilho();
    void enviarNotificacao(String mensagem);
}