package projetofinal.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import projetofinal.model.Ganho;
import projetofinal.model.Gasto;
import projetofinal.model.Transacao;
import projetofinal.model.Usuario;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path; // coloca o nosso classpath
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsuarioRepository {

    private Gson gson;
    private Path caminhoDoArquivo = Paths.get("usuarios.json");

    public UsuarioRepository() {
        GsonBuilder builder = new GsonBuilder();

        // Registramos um adaptador para ensinar o Gson a ler a classe Transacao
        builder.registerTypeAdapter(Transacao.class, new JsonDeserializer<Transacao>() {
            @Override
            public Transacao deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();

                // Verifica se o JSON possui o atributo "fonte" (exclusivo de Ganho)
                if (jsonObject.has("fonte")) {
                    return context.deserialize(json, Ganho.class);
                }
                // Verifica se o JSON possui o atributo "localidade" ou "frequencia" (exclusivos
                // de Gasto)
                else if (jsonObject.has("localidade") || jsonObject.has("frequencia")) {
                    return context.deserialize(json, Gasto.class);
                }

                throw new JsonParseException("Tipo de transação desconhecido no JSON.");
            }
        });

        // Cria a instância do Gson com as nossas regras customizadas
        this.gson = builder.create();
    }

    public List<Usuario> lerDoJson() {
        try {
            /* caso não exista o arquivo */
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
            /*
             * esse método vai transformar a minha lista de objetos em uma string no formato
             * do Json
             */
            String meuJson = gson.toJson(lista);
            /* escreve toda a String no meu arquivo */
            Files.writeString(caminhoDoArquivo, meuJson, StandardCharsets.UTF_8);
            System.out.println("vamos ver se passou aqui");
        } catch (Exception e) {
            System.out.println("deu ruim para cadastrar pessoa no json");
        }

    }

    public void atualizarUsuario(Usuario usuarioAtualizado) {
        List<Usuario> usuarios = lerDoJson();
        /*
         * essa variavel foi o jeito que encontrei para salvar o json só quando encontra
         * o usuário
         */
        boolean atualizou = false;

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(usuarioAtualizado.getEmail())) {
                usuarios.set(i, usuarioAtualizado);
                atualizou = true;
                break;
            }
        }

        /* vamos atualizar o json */
        if (atualizou) {
            try {
                String meuJson = gson.toJson(usuarios);

                Files.writeString(caminhoDoArquivo, meuJson, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}