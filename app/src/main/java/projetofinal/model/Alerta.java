public abstract class Alerta implements Notificavel {
    private String mensagem;
    private boolean ativo; 

    public Alerta(String mensagem) {
        this.mensagem = mensagem;
        this.ativo = true; 
    }

    public abstract void disparar();

    // Métodos de acesso (Getters e Setters) exigidos no UML
    public String getMensagem() { 
        return mensagem; 
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isAtivo() { 
        return ativo; 
    }

    public void setAtivo(boolean ativo) { 
        this.ativo = ativo; 
    }
}