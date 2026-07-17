package projetofinal.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projetofinal.model.Alertas.Alerta;
import projetofinal.model.Alertas.AlertaOrcamento;
import projetofinal.model.Interfaces.Exportavel;
import projetofinal.model.Interfaces.Pesquisavel;
import projetofinal.model.Transações.Gasto;
import projetofinal.model.Transações.Transacao;

public class Carteira implements Pesquisavel, Exportavel {
    private String nome;
    private List<Transacao> transacoes;
    private List<Alerta> alertas;

    public Carteira(String nome) {
        this.nome = nome;
        this.transacoes = new ArrayList<>();
        this.alertas = new ArrayList<>();
    }

    public void adicionarAlerta(Alerta novoAlerta) {
        this.alertas.add(novoAlerta);
    }

    public void adicionarTransacao(Transacao t) {
        this.transacoes.add(t);

        if (t instanceof Gasto) {
            checarOrcamento();
        }
    }

    private void checarOrcamento() {
        double totalJaGasto = 0.0;

        for (Transacao transacao : transacoes) {
            if (transacao instanceof Gasto) {
                totalJaGasto += transacao.getValor();
            }
        }

        for (Alerta alerta : alertas) {
            if (alerta instanceof AlertaOrcamento) {
                AlertaOrcamento alertaGasto = (AlertaOrcamento) alerta;
                alertaGasto.setValorAtual(totalJaGasto);

                if (alertaGasto.verificarGatilho()) {
                    alertaGasto.disparar();
                }
            }
        }
    }

    /**
     * Método para disparar todos os alertas de uma carteira assim que usamos a
     * carteira
     */
    public void checarTodosAlertas() {
        for (Alerta alerta : alertas) {
            if (alerta.verificarGatilho()) {
                alerta.disparar();
            }
        }
    }

    @Override
    public List<Transacao> fazerBusca(String termo) {
        List<Transacao> resultados = new ArrayList<>();
        String palavraChave = termo.toLowerCase();

        for (Transacao t : transacoes) {
            if (t.getDescricao().toLowerCase().contains(palavraChave)) {
                resultados.add(t);
            } else {
                for (String tag : t.getTags()) {
                    if (tag.toLowerCase().contains(palavraChave)) {
                        resultados.add(t);
                        break;
                    }
                }
            }
        }
        return resultados;
    }

    @Override
    public void exportarDados(String caminho) {
        try (FileWriter escritor = new FileWriter(caminho)) {
            escritor.write("=== Extrato da Carteira: " + this.nome + " ===\n");

            for (Transacao t : transacoes) {
                escritor.write(t.getData() + " | " + t.getDescricao() + " | R$ " + t.getValor() + "\n");
            }

            escritor.write("\nSaldo Final: R$ " + calcularSaldo() + "\n");
            System.out.println("-> [SUCESSO] Arquivo salvo em: " + caminho);

        } catch (IOException erro) {
            System.out.println("-> [ERRO] Falha ao salvar arquivo: " + erro.getMessage());
        }
    }

    public double calcularSaldo() {
        double saldo = 0.0;
        for (Transacao t : transacoes) {
            saldo += t.executarTransacao();
        }
        return saldo;
    }

    /**
     *  Méotodo de acesso de nome
     * @return String com o nome
     */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *  Méotodo de acesso da transação
     * @return lista com as transações
     */
    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    /**
     *  Méotodo de acesso dos alertas
     * @return lista com os alertas
     */
    public List<Alerta> getAlertas() {
        return alertas;
    }

    /**
     * Método para remover uma transação selecionada
     * @param transacao transação selecionada
     * @return booleano que indica se foi possível remover ou não
     */
    public boolean removerTransacao(Transacao transacao) {
        return this.transacoes.remove(transacao);
    }

    /**
     * Método para remover um alerta selcionado
     * @param alerta alerta que vai ser retirado
     * @return booleano que indica se foi possível a remoção
     */
    public boolean removerAlerta(Alerta alerta) {
        return this.alertas.remove(alerta);
    }

    /**
     * Método para que seja possível agrupar dados por tags
     * 
     * @return retorna um HashMap cpm a tag e o saldo dela
     */
    public Map<String, Double> getDadosPorTag() {
        Map<String, Double> resumo = new HashMap<>();

        for (Transacao t : transacoes) {
            for (String tag : t.getTags()) {
                double valorAtual = resumo.getOrDefault(tag, 0.0);

                resumo.put(tag, valorAtual + Math.abs(t.getValor()));
            }
        }
        return resumo;
    }

    /**
     * Método para que seja agrupar dados por mês
     * @return retorna um hashMap com o mes e o saldo dele
     */
    public Map<String, Double> getDadosPorMes() {
        Map<String, Double> resumo = new HashMap<>();

        for (Transacao t : transacoes) {
            
            String anoMes = t.getData().toString().substring(0, 7);
            double saldoAtual = resumo.getOrDefault(anoMes, 0.0);
            resumo.put(anoMes, saldoAtual + t.executarTransacao());
        }
        return resumo;
    }
}