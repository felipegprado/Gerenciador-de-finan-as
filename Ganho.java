import java.time.LocalDate;

public class Ganho extends Transacao {
    private String origemDaGrana; // Ex: "Salário Estágio", "Bolsa Baja", "Pix do Pai"

    public Ganho(double valor, String desc, LocalDate data, String origem) {
        super(valor, desc, data);
        this.origemDaGrana = origem;
    }

    @Override
    public double executarTransacao() {
        // Se é ganho, o valor entra positivo
        return getValor();
    }

    public String getOrigemDaGrana() { 
        return origemDaGrana; 
    }
    
    public void setOrigemDaGrana(String origem) { 
        this.origemDaGrana = origem; 
    }
}