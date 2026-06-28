package projetofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private List<Carteira> carteiras;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.carteiras = new ArrayList<>();
    }

    /*criei esse construtor para conseuir usar o Gson */
    public Usuario() {
        super();
    }

    public void adicionarCarteira(Carteira c) {
        this.carteiras.add(c);
    }

    // Getters e Setters exigidos no UML
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Carteira> getCarteiras() { return carteiras; }
}