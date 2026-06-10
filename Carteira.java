import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Carteira implements Pesquisavel, Exportavel {
    private String nomeCarteira;
    private List<Transacao> minhasTransacoes;
    private List<Alerta> meusAlertas; 

    public Carteira(String nome) {
        this.nomeCarteira = nome;
        this.minhasTransacoes = new ArrayList<>();
        this.meusAlertas = new ArrayList<>();
    }

    public void adicionarAlerta(Alerta novoAlerta) {
        this.meusAlertas.add(novoAlerta);
    }

    public void adicionarTransacao(Transacao t) {
        this.minhasTransacoes.add(t);
        
        // Verifica se a transação é um gasto. Se for, já checa o orçamento
        if (t instanceof Gasto) {
            checarOrcamento();
        }
    }

    private void checarOrcamento() {
        double totalJaGasto = 0.0;
        
        // 1. Fazendo o for na mão pra somar tudo que já gastou até agora
        for (int i = 0; i < minhasTransacoes.size(); i++) {
            Transacao transacao = minhasTransacoes.get(i);
            if (transacao instanceof Gasto) {
                totalJaGasto = totalJaGasto + transacao.getValor();
            }
        }

        // 2. Avisa os alertas de orçamento como tá a situação
        for (int i = 0; i < meusAlertas.size(); i++) {
            Alerta alerta = meusAlertas.get(i);
            
            if (alerta instanceof AlertaOrcamento) {
                // Tem que fazer o cast pra poder chamar o método que não tá na classe mãe
                AlertaOrcamento alertaGasto = (AlertaOrcamento) alerta;
                alertaGasto.atualizarValorAtual(totalJaGasto);
            }
        }
    }

    public void checarTodosAlertas() {
        for (Alerta alerta : meusAlertas) {
            // Como todo alerta agora implementa Notificavel, podemos chamar direto
            if (alerta.verificarGatilho() == true) {
                alerta.disparar();
            }
        }
    }

    @Override
    public List<Transacao> fazerBusca(String palavraChave) {
        List<Transacao> resultados = new ArrayList<>();
        palavraChave = palavraChave.toLowerCase(); // pra ignorar maiusculas e minusculas

        for (Transacao t : minhasTransacoes) {
            // 1. Procura na descricao
            if (t.getDescricao().toLowerCase().contains(palavraChave)) {
                resultados.add(t);
            } else {
                // 2. Se não achou na descricao, procura nas Tags
                for (String tag : t.getTags()) {
                    if (tag.toLowerCase().contains(palavraChave)) {
                        resultados.add(t);
                        break; // Já achou essa transacao, sai do loop interno das tags
                    }
                }
            }
        }
        return resultados;
    }

    @Override
    public void exportarDados(String caminhoDoArquivo) {
        try {
            FileWriter escritor = new FileWriter(caminhoDoArquivo);
            escritor.write("=== Extrato da Carteira: " + this.nomeCarteira + " ===\n");
            
            for (Transacao t : minhasTransacoes) {
                escritor.write(t.getData() + " | " + t.getDescricao() + " | R$ " + t.getValor() + "\n");
            }
            
            escritor.write("\nSaldo Final: R$ " + calcularSaldoFinal() + "\n");
            escritor.close();
            
            System.out.println("-> [SUCESSO] Arquivo salvo lá em: " + caminhoDoArquivo);
            
        } catch (IOException erro) {
            System.out.println("-> [DEU RUIM] Erro ao salvar arquivo: " + erro.getMessage());
        }
    }

    public double calcularSaldoFinal() {
        double saldo = 0.0;
        for (Transacao t : minhasTransacoes) {
            // O metodo executarTransacao() já sabe se tem que somar (ganho) ou subtrair (gasto)
            saldo = saldo + t.executarTransacao();
        }
        return saldo;
    }

    public List<Transacao> getTransacoes() { 
        return minhasTransacoes; 
    }




    // Método para remover uma transação específica
    public boolean removerTransacao(Transacao transacao) {
        // O método .remove() da lista busca o objeto e o remove. 
        // Retorna true se encontrou e removeu, e false se não encontrou.
        return this.minhasTransacoes.remove(transacao);
    }

    // Método para remover um alerta específico
    public boolean removerAlerta(Alerta alerta) {
        return this.meusAlertas.remove(alerta);
    }
}