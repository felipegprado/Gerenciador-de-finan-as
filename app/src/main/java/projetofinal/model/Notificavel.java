package projetofinal.model;
public interface Notificavel {
    boolean verificarGatilho();
    void enviarNotificacao(String mensagem);
}