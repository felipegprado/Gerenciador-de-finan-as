package projetofinal.repository;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path; // coloca o nosso classpath
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import projetofinal.model.Usuario;
import projetofinal.model.Alertas.Alerta;
import projetofinal.model.Alertas.AlertaOrcamento;
import projetofinal.model.Alertas.AlertaVencimento;
import projetofinal.model.Transações.Ganho;
import projetofinal.model.Transações.Gasto;
import projetofinal.model.Transações.Transacao;

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

        builder.registerTypeAdapter(Alerta.class, new JsonDeserializer<Alerta>() {
            @Override
            public Alerta deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();

                if (jsonObject.has("valorLimite")) {
                    return context.deserialize(json, AlertaOrcamento.class);
                }
                else if (jsonObject.has("dataVencimento")) {
                    return context.deserialize(json, AlertaVencimento.class);
                }

                throw new JsonParseException("Tipo de alerta desconhecido no JSON.");
            }
        });

        builder.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public JsonElement serialize(LocalDate data, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(data.toString()); // Salva no formato "YYYY-MM-DD"
            }
        });

        builder.registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return LocalDate.parse(json.getAsString()); // Lê o formato "YYYY-MM-DD"
            }
        });

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