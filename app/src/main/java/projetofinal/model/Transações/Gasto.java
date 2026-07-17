package projetofinal.model.Transações;

import java.util.List;

/**
 * Classe concreta para as regras de negócio da transação específica de gasto
 * Gasto
 */
public class Gasto extends Transacao {
    private String frequencia;
    private String localidade;

    public Gasto(double valor, String descricao, String data, List<String> tags, String frequencia, String localidade) {
        super(valor, descricao, data, tags);
        this.frequencia = frequencia;
        this.localidade = localidade;
    }

    @Override
    public double executarTransacao() {
        return -getValor();
    }

    /**
     * Método acessor para a frequência do gasto
     */
    public String getFrequencia() {
        return frequencia;
    }

    /**
     * Método para mudar a frequencia do gasto.
     * 
     * @param frequencia texto com a frequencia
     */
    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    /**
     * Método para pegar o local do gasto caso seja informado
     * 
     * @return Texto com a localidade
     */
    public String getLocalidade() {
        return localidade;
    }

    /**
     * Método para mudar a localidade caso ela seja definida
     * 
     * @param localidade texto com a localidade
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
}