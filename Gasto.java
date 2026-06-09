import java.time.LocalDate;

public class Gasto extends Transacao {
    private String frequencia; // "fixo", "parcelado", "à vista"
    private String localidade; // Ex: "Supermercado X"

    public Gasto(double valor, String descricao, LocalDate data, String frequencia, String localidade) {
        super(valor, descricao, data);
        this.frequencia = frequencia;
        this.localidade = localidade;
    }

    @Override
    public double executarTransacao() {
        // Gasto subtrai do saldo total
        return -getValor();
    }

    // Getters e Setters específicos
    public String getFrequencia() { return frequencia; }
    public void setFrequencia(String frequencia) { this.frequencia = frequencia; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
}