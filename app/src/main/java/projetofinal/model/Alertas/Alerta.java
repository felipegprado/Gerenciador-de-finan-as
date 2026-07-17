package projetofinal.model.Alertas;

import projetofinal.model.Interfaces.Notificavel;

/**
 * Classe abstrata para termos vários tipos de alertas diferentes
 * Alerta
 */
public abstract class Alerta implements Notificavel {
    private String mensagem;
    private boolean ativo;

    public Alerta(String mensagem) {
        this.mensagem = mensagem;
        this.ativo = true;
    }

    /**
     * Método abstrato para disparar o alarme com
     * notificação personalizada.
     */
    public abstract void disparar();

    /**
     * Método de acesso a mensagem do alarme
     * 
     * @return String representando a mensagem
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Método modificador da mensagem do alarme
     * 
     * @param mensagem
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Verifica se o Alarme está ativo
     * 
     * @return
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Método modificador se está ativo
     * 
     * @param ativo
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}