import java.util.List;

public interface Pesquisavel {
    // Procura nas despesas uma palavra chave (tipo "Baja" ou "Mercado")
    List<Transacao> fazerBusca(String palavraChave);
}