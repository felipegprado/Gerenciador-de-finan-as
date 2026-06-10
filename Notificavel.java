public interface Notificavel {
    // Checa se a regra bateu (estourou o limite de grana ou chegou o dia do boleto)
    boolean verificarGatilho();
    
    // Manda o aviso pro usuário
    void enviarNotificacao(String msg);
}