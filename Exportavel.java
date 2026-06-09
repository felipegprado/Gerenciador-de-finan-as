import java.io.IOException;

public interface Exportavel {
    // Exige a lógica para escrever os dados internamente em um arquivo local
    void exportarDados(String caminhoArquivo) throws IOException;
}