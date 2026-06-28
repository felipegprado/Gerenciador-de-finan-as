package projetofinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Transacao {
    private double valor;
    private String descricao;
    private String data;
    private List<String> tags;

    public Transacao(double valor, String descricao, String data, List<String> tags) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        // Evita NullPointerException se passarem uma lista nula
        this.tags = (tags != null) ? tags : new ArrayList<>();
    }

    // Método Abstrato que define o comportamento polimórfico
    public abstract double executarTransacao();

    // Métodos de acesso (Getters e Setters) idênticos ao UML
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /*salvamos como string mas na hora de entregar passamos para localData */
    public LocalDate getData() {
        return LocalDate.parse(this.data);
    }

    public void setData(LocalDate data) {
        this.data = data.toString();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // Método auxiliar útil para adicionar tags individualmente
    public void adicionarTag(String tag) {
        this.tags.add(tag);
    }
}