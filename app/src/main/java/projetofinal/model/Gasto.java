package projetofinal.model;

import java.util.List;

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
        return -getValor(); // Retorna negativo
    }


    // Métodos de acesso exigidos no UML
    public String getFrequencia() { 
        return frequencia; 
    }
    
    public void setFrequencia(String frequencia) { 
        this.frequencia = frequencia; 
    }

    public String getLocalidade() { 
        return localidade; 
    }
    
    public void setLocalidade(String localidade) { 
        this.localidade = localidade; 
    }
}