
// MUDANÇA AQUI: A classe abstrata agora obriga todo mundo a ser Notificavel
public abstract class Alerta implements Notificavel {
    private String mensagem;
    private boolean ativo; 

    public Alerta(String msg) {
        this.mensagem = msg;
        this.ativo = true; 
    }

    public abstract void disparar();

    // Getters e setters padroes...
    public String getMensagem() { 
        return mensagem; 
    }

    public boolean isAtivo() { 
        return ativo; 
    }

    public void setAtivo(boolean ativo) { 
        this.ativo = ativo; 
    }
}