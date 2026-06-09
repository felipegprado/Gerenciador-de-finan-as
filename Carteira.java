import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private String nome;
    private List<Transacao> transacoes;
    private List<Alerta> alertas; // A carteira agora guarda os alertas do usuário

    public Carteira(String nome) {
        this.nome = nome;
        this.transacoes = new ArrayList<>();
        this.alertas = new ArrayList<>();
    }

    public void adicionarAlerta(Alerta alerta) {
        this.alertas.add(alerta);
    }

    public void adicionarTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
        
        // Toda vez que entra um gasto, a carteira checa os alertas de orçamento
        if (transacao instanceof Gasto) {
            checarAlertasDeOrcamento();
        }
    }

    private void checarAlertasDeOrcamento() {
        double totalGasto = 0;
        
        // 1. Calcula o total de gastos atuais na carteira
        for (Transacao t : transacoes) {
            if (t instanceof Gasto) {
                totalGasto += t.getValor();
            }
        }

        // 2. Atualiza e testa cada alerta de orçamento
        for (Alerta a : alertas) {
            if (a instanceof AlertaOrcamento) {
                ((AlertaOrcamento) a).atualizarValorAtual(totalGasto);
            }
        }
    }

    // Método para rodar uma verificação manual (ex: checar boletos vencendo)
    public void verificarAlertasGerais() {
        for (Alerta a : alertas) {
            if (a instanceof AlertaVencimento) {
                AlertaVencimento av = (AlertaVencimento) a;
                if (av.verificarGatilho()) {
                    av.disparar();
                }
            }
        }
    }

    public double calcularSaldo() {
        double saldoTotal = 0;
        for (Transacao t : transacoes) {
            saldoTotal += t.executarTransacao();
        }
        return saldoTotal;
    }

    public List<Transacao> getTransacoes() { return transacoes; }
}