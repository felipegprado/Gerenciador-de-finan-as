package projetofinal.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import projetofinal.model.Usuario;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private Gson gson = new Gson();

    public List<Usuario> lerDoJson() {
            /*InputStream pega o arquivo em bytes */
        try (InputStream arq = getClass().getResourceAsStream("/dados/usuarios.json")) {
            if (arq == null) {
                System.out.println("Arquivo não encontrado!");
                return new ArrayList<>();
            }
            
            Reader reader = new InputStreamReader(arq); //para transformar em texto os bytes
            // Definimos o tipo como uma lista de usuários
            Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            return gson.fromJson(reader, listType);
            
        } catch (Exception e) {
            e.printStackTrace();
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
        
        
        try (FileWriter writer = new FileWriter("/dados/usuarios.json")) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}