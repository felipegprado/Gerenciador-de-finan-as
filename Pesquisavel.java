import java.util.List;

public interface Pesquisavel {
    // Retorna uma lista de transações que batem com o termo pesquisado (na descrição ou tags)
    List<Transacao> fazerBusca(String termo);
}