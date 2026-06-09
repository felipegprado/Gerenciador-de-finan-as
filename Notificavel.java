public interface Notificavel {
    // Método para avaliar se a regra de negócio foi violada (ex: teto atingido ou data próxima)
    boolean verificarGatilho();
    
    // Método que executa a ação de alertar o usuário
    void enviarNotificacao(String mensagem);
}