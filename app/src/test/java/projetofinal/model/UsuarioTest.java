package projetofinal.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    // Classe auxiliar para isolar o teste garantindo o retorno de um saldo fixo
    private class CarteiraMock extends Carteira {
        private double saldoFixo;
        
        public CarteiraMock(String nome, double saldoFixo) {
            super(nome);
            this.saldoFixo = saldoFixo;
        }

        @Override
        public double calcularSaldo() {
            return saldoFixo;
        }
    }

    @Test
    public void deveInicializarUsuarioCorretamente() {
        Usuario user = new Usuario("João Silva", "joao@email.com", "senha123");
        
        assertEquals("João Silva", user.getNome());
        assertEquals("joao@email.com", user.getEmail());
        assertEquals("senha123", user.getSenha());
        assertNotNull(user.getCarteiras());
        assertTrue(user.getCarteiras().isEmpty());
    }
    
    @Test
    public void deveInicializarUsuarioComConstrutorVazio() {
        Usuario user = new Usuario();
        assertNull(user.getNome());
        assertNull(user.getCarteiras()); // O construtor vazio invoca o super() e não inicializa a lista
    }

    @Test
    public void deveAdicionarERemoverCarteiraPeloNome() {
        Usuario user = new Usuario("Maria", "maria@email.com", "1234");
        Carteira c1 = new Carteira("Principal");
        Carteira c2 = new Carteira("Viagem");
        
        user.adicionarCarteira(c1);
        user.adicionarCarteira(c2);
        assertEquals(2, user.getCarteiras().size());
        
        // Testa o método de remoção varrendo a lista
        user.removeCarteira("Principal");
        assertEquals(1, user.getCarteiras().size());
        assertEquals("Viagem", user.getCarteiras().get(0).getNome());
    }

    @Test
    public void deveCalcularSaldoTotalDasCarteiras() {
        Usuario user = new Usuario("Carlos", "carlos@email.com", "1234");
        
        // Usamos as carteiras com saldo injetado manualmente (Mocks)
        user.adicionarCarteira(new CarteiraMock("Corrente", 1500.0));
        user.adicionarCarteira(new CarteiraMock("Poupança", 500.0));
        
        assertEquals(2000.0, user.getSaldoTotal());
    }
    
    @Test
    public void deveRetornarZeroNoSaldoTotalSeListaForNula() {
        Usuario userGson = new Usuario(); // A lista de carteiras fica null
        assertEquals(0.0, userGson.getSaldoTotal());
    }

    @Test
    public void deveVerificarSeTemCarteiraComNomeIgnorandoMaiusculasEEspacos() {
        Usuario user = new Usuario("Ana", "ana@email.com", "1234");
        Carteira carteira = new Carteira("Investimentos"); // Nome padrão
        user.adicionarCarteira(carteira);
        
        // Verifica com exatidão
        assertTrue(user.temCarteiraComNome("Investimentos"));
        // Verifica ignorando o case sensitivo (maiúsculas/minúsculas) e com espaços em branco ao redor
        assertTrue(user.temCarteiraComNome(" investimentos "));
        assertTrue(user.temCarteiraComNome(" INVESTIMENTOS"));
        
        // Verifica um nome que não existe
        assertFalse(user.temCarteiraComNome("Despesas"));
    }
}