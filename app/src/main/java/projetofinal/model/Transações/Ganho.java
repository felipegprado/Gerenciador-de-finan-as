package projetofinal.model.Transações;

import java.util.List;

/**
 * Classe concreta que herda de transação e representa um
 * saldo positivo na transação.
 * Ganho
 */
public class Ganho extends Transacao {
    private String fonte;

    public Ganho(double valor, String descricao, String data, List<String> tags, String fonte) {
        super(valor, descricao, data, tags);
        this.fonte = fonte;
    }

    @Override
    public double executarTransacao() {
        return getValor();
    }

    @Override
    public double getValor() {

        return super.getValor();
    }

    /**
     * Método para adicionar colocar a fonte da transação
     * 
     * @return
     */
    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }
}