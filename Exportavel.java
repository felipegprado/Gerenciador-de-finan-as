import java.io.IOException;

public interface Exportavel {
    // Método pra salvar os dados num .txt ou .csv quando for exportar relatorio
    void exportarDados(String caminhoDoArquivo) throws IOException;
}