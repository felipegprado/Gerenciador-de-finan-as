package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private List<Carteira> carteiras;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.carteiras = new ArrayList<>();
        this.senha = senha;
    }

    /* criei esse construtor para conseuir usar o Gson */
    public Usuario() {
        super();
    }

    public void adicionarCarteira(Carteira c) {
        this.carteiras.add(c);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public String getSenha() {
        return this.senha;
    }

    /**
     * Processa o saldo de todas as carteiras para que o usuário saiba o saldo
     * total.
     * 
     * @return saldo total do usuário
     */
    public double getSaldoTotal() {
        double saldo = 0;
        if (this.carteiras == null)
            return saldo;

        for (Carteira carteira : this.carteiras)
            saldo += carteira.calcularSaldo();
        return saldo;

    }

    /**
     * Metodo para retirar uma carteira selecionada pelo usuário
     * 
     * @param nomeCarteira String com o nome da carteira.
     */
    public void removeCarteira(String nomeCarteira) {

        for (int i = 0; i < this.carteiras.size(); i++) {
            Carteira carteira = this.carteiras.get(i);

            if (carteira.getNome().equals(nomeCarteira)) {
                this.carteiras.remove(i);
                return;
            }
        }

    }

    /**
     * Método que Verifica se o utilizador já possui uma carteira com o nome
     * especificado.
     * 
     * @param nome O nome da carteira a ser verificado
     * @return true se o nome já estiver em uso, false se estiver disponível
     */
    public boolean temCarteiraComNome(String nome) {
        if (this.carteiras != null) {
            for (Carteira carteira : this.carteiras) {
                if (carteira.getNome().equalsIgnoreCase(nome.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
}