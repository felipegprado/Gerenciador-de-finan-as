import java.time.LocalDate;

public class Ganho extends Transacao {
    private String fonte; // Ex: "Salário Estágio", "Bolsa Baja"

    public Ganho(double valor, String descricao, LocalDate data, String fonte) {
        super(valor, descricao, data);
        this.fonte = fonte;
    }

    @Override
    public double executarTransacao() {
        // Ganho soma ao saldo total
        return getValor();
    }

    // Getter e Setter específico
    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
}