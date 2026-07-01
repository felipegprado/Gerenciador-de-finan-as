package projetofinal.repository;

import com.google.gson.Gson;
import projetofinal.model.Usuario;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path; // coloca o nosso classpath
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsuarioRepository {

    private Gson gson = new Gson();
    private Path caminhoDoArquivo = Paths.get("usuarios.json");

    public List<Usuario> lerDoJson() {
        try {
            /*caso não exista o arquivo */
            if (!Files.exists(caminhoDoArquivo)) {
                System.out.println("Não encontramos o arquivo json.");
                return new ArrayList<>();
            }

            /* transformei o meu arquivo em uma String para o Gson manipular */
            String meuJson = Files.readString(caminhoDoArquivo, StandardCharsets.UTF_8);
            Usuario[] arrayDeUsuarios = gson.fromJson(meuJson, Usuario[].class); // um vetor ainda

            if (arrayDeUsuarios == null)
                return new ArrayList<>();
            return new ArrayList<>(Arrays.asList(arrayDeUsuarios));

        } catch (Exception e) {
            System.out.println("Erro no nosso arquivo JSON");
            return new ArrayList<>();
        }
    }

    public Usuario verificarLogin(String email, String senha) {
        List<Usuario> usuarios = lerDoJson();

        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u; // Login encontrado
            }
        }
        return null;
    }

    public void cadastrarUsuario(Usuario novoUsuario) {
        List<Usuario> lista = lerDoJson();
        lista.add(novoUsuario);
        try {
            /*esse método vai transformar a minha lista de objetos em uma string no formato do Json */
            String meuJson = gson.toJson(lista);
            /*escreve toda a String no meu arquivo */
            Files.writeString(caminhoDoArquivo, meuJson, StandardCharsets.UTF_8); 
            System.out.println("vamos ver se passou aqui");
        } catch (Exception e) {
            System.out.println("deu ruim para cadastrar pessoa no json");
        }

    }
}