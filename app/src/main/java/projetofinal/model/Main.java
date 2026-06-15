import java.time.LocalDate;
import java.util.ArrayList;
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
        // Criando a lista de tags do jeito mais manual e explícito possível
        List<String> tagsSalario = new ArrayList<>();
        tagsSalario.add("salario");
        tagsSalario.add("trabalho");
        
        Ganho salario = new Ganho(500.00, "Salário Mensal", LocalDate.now(), tagsSalario, "Transferência Bancária");
        
        System.out.println("-> Registrando despesa (Material de Escritório)...");
        List<String> tagsEscritorio = new ArrayList<>();
        tagsEscritorio.add("escritorio");
        tagsEscritorio.add("material");
        
        Gasto materialEscritorio = new Gasto(50.00, "Cadernos e Canetas", LocalDate.now(), tagsEscritorio, "Fixo", "Papelaria Central");

        System.out.println("-> Registrando despesa (Supermercado)...");
        List<String> tagsMercado = new ArrayList<>();
        tagsMercado.add("alimentacao");
        tagsMercado.add("essencial");
        
        Gasto mercado = new Gasto(160.00, "Compras do Mês", LocalDate.now(), tagsMercado, "Variável", "Supermercado Local");

        // --- 3. ADICIONANDO NA CARTEIRA ---
        minhaCarteira.adicionarTransacao(salario);
        minhaCarteira.adicionarTransacao(materialEscritorio);
        minhaCarteira.adicionarTransacao(mercado); 

        // --- 4. CHECAGENS MANUAIS E RELATÓRIOS ---
        System.out.println("\n-> Verificando alertas de vencimento pendentes...");
        minhaCarteira.checarTodosAlertas(); 

        System.out.println("\n-> Buscando transações com a tag 'escritorio'...");
        List<Transacao> resultadoBusca = minhaCarteira.fazerBusca("escritorio");
        for (Transacao t : resultadoBusca) {
            System.out.println("   Registro encontrado: " + t.getDescricao() + " | Valor Real: R$ " + t.getValor());
        }

        System.out.printf("\nSaldo final da carteira: R$ %.2f\n", minhaCarteira.calcularSaldo());

        System.out.println("\n-> Exportando relatório de transações...");
        minhaCarteira.exportarDados("extrato_financeiro.txt"); 
    }
}