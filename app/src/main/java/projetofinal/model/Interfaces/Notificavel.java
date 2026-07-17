package projetofinal.model.Interfaces;

/**
 * interface inicial para poder fazer a notificação.
 * Notificavel
 */
public interface Notificavel {
    boolean verificarGatilho();

    void enviarNotificacao(String mensagem);
}