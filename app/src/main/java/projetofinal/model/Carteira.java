package projetofinal.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                // Ajustado para setar o valor absoluto e evitar duplicações no +=
                alertaGasto.setValorAtual(totalJaGasto);
                
                if (alertaGasto.verificarGatilho()) {
                    alertaGasto.disparar();
                }
            }
        }
    }

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

    // Métodos de acesso exigidos pelo UML
    public String getNome() { 
        return nome; 
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Transacao> getTransacoes() { 
        return transacoes; 
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public boolean removerTransacao(Transacao t) {
        return this.transacoes.remove(t);
    }

    public boolean removerAlerta(Alerta a) {
        return this.alertas.remove(a);
    }

    // Métodos stubs para os mapas do UML (serão implementados conforme regras de negócio posteriores)
    public Map<String, Double> getDadosPorTag() {
        return new HashMap<>();
    }

    public Map<String, Double> getDadosPorMes() {
        return new HashMap<>();
    }
}