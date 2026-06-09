import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Inicializando Carteira do Arthur ===\n");

        Carteira carteira = new Carteira("Finanças Pessoais");

        // 1. O usuário cria uma meta: Não gastar mais do que R$ 200,00 no total
        AlertaOrcamento limiteGeral = new AlertaOrcamento("Alerta: Você passou do limite de gastos do mês!", 200.0);
        carteira.adicionarAlerta(limiteGeral);

        // 2. O usuário cadastra um aviso de boleto para HOJE
        AlertaVencimento internet = new AlertaVencimento("Boleto da Internet vencendo hoje!", LocalDate.now());
        carteira.adicionarAlerta(internet);

        // 3. Recebendo dinheiro (Saldo inicial)
        System.out.println("-> Depositando saldo...");
        Ganho mesada = new Ganho(500.00, "Mesada", LocalDate.now(), "Pais");
        carteira.adicionarTransacao(mesada);

        // 4. Primeiro gasto: R$ 50,00 (Abaixo do limite de 200, tudo certo)
        System.out.println("-> Comprando peças na oficina (R$ 50,00)...");
        Gasto pecaBaja = new Gasto(50.00, "Parafusos Prototipação", LocalDate.now(), "à vista", "Oficina");
        carteira.adicionarTransacao(pecaBaja);

        // 5. Segundo gasto: R$ 160,00 (Total acumulado: R$ 210,00. Vai estourar o limite!)
        System.out.println("-> Comprando mercado (R$ 160,00)...");
        Gasto mercado = new Gasto(160.00, "Compras do Mês", LocalDate.now(), "à vista", "Supermercado");
        
        // Ao rodar a linha abaixo, o alerta deve disparar sozinho no console!
        carteira.adicionarTransacao(mercado);

        // 6. Verificação de contas do dia
        System.out.println("\n-> Rodando checagem de contas a pagar...");
        carteira.verificarAlertasGerais();

        // 7. Mostrando saldo final
        System.out.printf("%nSaldo final na carteira: R$ %.2f%n", carteira.calcularSaldo());
    }
}