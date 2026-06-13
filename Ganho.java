import java.time.LocalDate;
import java.util.List;

public class Ganho extends Transacao {
    private String fonte; 

    public Ganho(double valor, String descricao, LocalDate data, List<String> tags, String fonte) {
        super(valor, descricao, data, tags);
        this.fonte = fonte;
    }

    @Override
    public void executarTransacao() {
        // Implementação do método abstrato da classe Mãe
        // O saldo final utiliza o getValor() de forma positiva no polimorfismo
    }

    @Override
    public double getValor() {
        // Garante retorno positivo para a soma da carteira
        return super.getValor();
    }

    // Métodos de acesso exigidos no UML
    public String getFonte() { 
        return fonte; 
    }
    
    public void setFonte(String fonte) { 
        this.fonte = fonte; 
    }
}