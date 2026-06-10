import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Gestão Financeira ===\n"); 

        Carteira minhaCarteira = new Carteira("Carteira Principal");

        // --- 1. CONFIGURANDO OS ALERTAS ---
        minhaCarteira.adicionarAlerta(new AlertaOrcamento("Atenção: Você passou do limite de gastos.", 200.0));
        minhaCarteira.adicionarAlerta(new AlertaVencimento("Aviso: Há um boleto com vencimento para hoje.", LocalDate.now()));

        // --- 2. CRIANDO AS TRANSAÇÕES ---
        System.out.println("-> Registrando receita inicial...");
        Ganho salario = new Ganho(500.00, "Salário Mensal", LocalDate.now(), "Transferência Bancária");
        
        System.out.println("-> Registrando despesa (Material de Escritório)...");
        Gasto materialEscritorio = new Gasto(50.00, "Cadernos e Canetas", LocalDate.now(), "Pix", "Papelaria Central");
        materialEscritorio.adicionarTag("escritorio");
        materialEscritorio.adicionarTag("material");

        System.out.println("-> Registrando despesa (Supermercado)...");
        Gasto mercado = new Gasto(160.00, "Compras do Mês", LocalDate.now(), "Cartão de Crédito", "Supermercado Local");
        mercado.adicionarTag("alimentacao");
        mercado.adicionarTag("essencial");

        // --- 3. ADICIONANDO NA CARTEIRA ---
        minhaCarteira.adicionarTransacao(salario);
        minhaCarteira.adicionarTransacao(materialEscritorio);
        
        // Ao adicionar o mercado, o limite de R$ 200,00 será ultrapassado (50 + 160 = 210)
        // O alerta de orçamento será disparado automaticamente no console.
        minhaCarteira.adicionarTransacao(mercado); 

        // --- 4. CHECAGENS MANUAIS E RELATÓRIOS ---
        System.out.println("\n-> Verificando alertas de vencimento pendentes...");
        minhaCarteira.checarTodosAlertas(); 

        System.out.println("\n-> Buscando transações com a tag 'escritorio'...");
        List<Transacao> resultadoBusca = minhaCarteira.fazerBusca("escritorio");
        for (Transacao t : resultadoBusca) {
            System.out.println("   Registro encontrado: " + t.getDescricao() + " | Valor: R$ " + t.getValor());
        }

        System.out.printf("\nSaldo final da carteira: R$ %.2f\n", minhaCarteira.calcularSaldoFinal());

        System.out.println("\n-> Exportando relatório de transações...");
        // O arquivo será gerado no diretório raiz do projeto
        minhaCarteira.exportarDados("extrato_financeiro.txt"); 
    }
}