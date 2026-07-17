package projetofinal.model;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import projetofinal.model.Exceções.FormatoArquivoInvalidoException;
import projetofinal.model.Transações.Ganho;
import projetofinal.model.Transações.Gasto;

public class GerenciadorArquivos {

    public void salvarDados(Carteira c, String caminho) {
        c.exportarDados(caminho);
    }

    public Carteira carregarDados(String caminho) throws FormatoArquivoInvalidoException {
        if (!caminho.endsWith(".txt")) {
            throw new FormatoArquivoInvalidoException("O sistema apenas aceita arquivos de texto (.txt)");
        }

        Carteira carteiraRecuperada = new Carteira("Carteira Recuperada");

        try {
            File arquivo = new File(caminho);
            Scanner leitor = new Scanner(arquivo);

            if (leitor.hasNextLine()) {
                leitor.nextLine();
            }

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();

                if (linha.isEmpty() || linha.startsWith("Saldo Final")) {
                    continue;
                }
                String[] partes = linha.split("\\|");
                if (partes.length >= 3) {
                    LocalDate data = LocalDate.parse(partes[0].trim());
                    String descricao = partes[1].trim();

                    String textoValor = partes[2].replace("R$", "").trim();
                    double valor = Double.parseDouble(textoValor);

                    if (valor >= 0) {
                        carteiraRecuperada
                                .adicionarTransacao(
                                        new Ganho(valor, descricao, data.toString(), new ArrayList<>(), "Recuperado"));
                    } else {
                        carteiraRecuperada.adicionarTransacao(
                                new Gasto(Math.abs(valor), descricao, data.toString(), new ArrayList<>(), "Fixo",
                                        "Desconhecido"));
                    }
                }
            }

            leitor.close();

        } catch (Exception e) {
            throw new FormatoArquivoInvalidoException("Erro ao ler o arquivo: " + e.getMessage());
        }

        return carteiraRecuperada;
    }
}