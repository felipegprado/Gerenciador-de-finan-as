import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Transacao {
    private double valorTransacao;
    private String desc; // 'descricao' tava meio longo
    private LocalDate dataTransacao;
    private List<String> listaDeTags; // Mais intuitivo saber que é uma lista pelo nome

    public Transacao(double valor, String desc, LocalDate data) {
        this.valorTransacao = valor;
        this.desc = desc;
        this.dataTransacao = data;
        this.listaDeTags = new ArrayList<String>(); // Inicializa a lista vazia pra não dar NullPointer
    }

    // Método Abstrato (Aqui que rola o polimorfismo das classes filhas)
    // Retorna positivo se for ganho e negativo se for gasto
    public abstract double executarTransacao();

    // Getters e Setters gerados pelo atalho da IDE
    public double getValor() { 
        return valorTransacao; 
    }
    
    public void setValor(double valor) { 
        this.valorTransacao = valor; 
    }

    public String getDescricao() { 
        return desc; 
    }
    
    public void setDescricao(String desc) { 
        this.desc = desc; 
    }

    public LocalDate getData() { 
        return dataTransacao; 
    }
    
    public void setData(LocalDate data) { 
        this.dataTransacao = data; 
    }

    public List<String> getTags() { 
        return listaDeTags; 
    }
    
    public void adicionarTag(String novaTag) { 
        this.listaDeTags.add(novaTag); 
    }
}