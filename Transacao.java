import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Transacao {
    private double valor;
    private String descricao;
    private LocalDate data;
    private List<String> tags;

    public Transacao(double valor, String descricao, LocalDate data) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.tags = new ArrayList<>();
    }

    // Método Abstrato (Polimorfismo nas classes filhas)
    // Retorna o impacto dessa transação no saldo total
    public abstract double executarTransacao();

    // Getters e Setters
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public List<String> getTags() { return tags; }
    public void adicionarTag(String tag) { this.tags.add(tag); }
}