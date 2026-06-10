import java.time.LocalDate;

public class Gasto extends Transacao {
    private String tipoDePagamento; // Ex: "fixo", "parcelado no cartao", "pix"
    private String ondeGastou; // Ex: "RU", "Supermercado X", "Xerox"

    public Gasto(double valor, String desc, LocalDate data, String tipo, String onde) {
        super(valor, desc, data);
        this.tipoDePagamento = tipo;
        this.ondeGastou = onde;
    }

    @Override
    public double executarTransacao() {
        // Gasto tem que tirar do saldo, então o retorno é negativo
        return -getValor(); 
    }

    public String getTipoDePagamento() { 
        return tipoDePagamento; 
    }
    
    public void setTipoDePagamento(String tipo) { 
        this.tipoDePagamento = tipo; 
    }

    public String getOndeGastou() { 
        return ondeGastou; 
    }
    
    public void setOndeGastou(String onde) { 
        this.ondeGastou = onde; 
    }
}