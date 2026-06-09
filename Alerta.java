public abstract class Alerta {
    private String mensagem;
    private boolean ativo;

    public Alerta(String mensagem) {
        this.mensagem = mensagem;
        this.ativo = true; // Por padrão, o alerta nasce ativo
    }

    // Cada tipo de alerta vai disparar do seu jeito
    public abstract void disparar();

    // Getters e Setters básicos
    public String getMensagem() { return mensagem; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}