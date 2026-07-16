package projetofinal.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import projetofinal.model.Usuario;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioRepositoryTest {

    private UsuarioRepository repository;
    private Path arquivoTeste;

    @BeforeEach
    public void configurarTeste(@TempDir Path pastaTemporaria) throws Exception {
        // Inicializa o repositório
        repository = new UsuarioRepository();
        
        // Define um arquivo temporário seguro para os testes
        arquivoTeste = pastaTemporaria.resolve("usuarios_teste.json");

        // Usa Reflection para trocar o caminho hardcoded pelo arquivo temporário
        Field campoCaminho = UsuarioRepository.class.getDeclaredField("caminhoDoArquivo");
        campoCaminho.setAccessible(true);
        campoCaminho.set(repository, arquivoTeste);
    }

    @Test
    public void lerDoJson_deveRetornarListaVaziaSeArquivoNaoExistir() {
        // Como o arquivo_teste.json ainda não foi criado, deve retornar lista vazia
        List<Usuario> usuarios = repository.lerDoJson();
        assertNotNull(usuarios);
        assertTrue(usuarios.isEmpty());
    }

    @Test
    public void cadastrarUsuario_e_lerDoJson_devemFuncionarCorretamente() {
        Usuario novoUsuario = new Usuario("Arthur", "arthur@email.com", "senha123");
        
        repository.cadastrarUsuario(novoUsuario);

        // Verifica se o arquivo foi realmente criado no disco
        assertTrue(Files.exists(arquivoTeste));

        // Lê os dados e verifica se o usuário foi salvo corretamente
        List<Usuario> usuariosLidos = repository.lerDoJson();
        assertEquals(1, usuariosLidos.size());
        assertEquals("Arthur", usuariosLidos.get(0).getNome());
        assertEquals("arthur@email.com", usuariosLidos.get(0).getEmail());
    }

    @Test
    public void verificarLogin_comCredenciaisValidas_deveRetornarUsuario() {
        Usuario usuario = new Usuario("Lucas", "lucas@email.com", "senha456");
        repository.cadastrarUsuario(usuario);

        Usuario usuarioLogado = repository.verificarLogin("lucas@email.com", "senha456");

        assertNotNull(usuarioLogado);
        assertEquals("Lucas", usuarioLogado.getNome());
    }

    @Test
    public void verificarLogin_comCredenciaisInvalidas_deveRetornarNull() {
        Usuario usuario = new Usuario("Maria", "maria@email.com", "1234");
        repository.cadastrarUsuario(usuario);

        // Testa senha errada
        Usuario loginSenhaErrada = repository.verificarLogin("maria@email.com", "senha_errada");
        assertNull(loginSenhaErrada);

        // Testa e-mail não cadastrado
        Usuario loginEmailErrado = repository.verificarLogin("naoexiste@email.com", "1234");
        assertNull(loginEmailErrado);
    }

    @Test
    public void atualizarUsuario_deveSalvarAlteracoesNoJson() {
        // 1. Cadastra o usuário inicial
        Usuario usuarioOriginal = new Usuario("João", "joao@email.com", "senhaAntiga");
        repository.cadastrarUsuario(usuarioOriginal);

        // 2. Modifica o usuário
        Usuario usuarioAtualizado = new Usuario("João Silva", "joao@email.com", "novaSenha123");
        repository.atualizarUsuario(usuarioAtualizado);

        // 3. Lê do arquivo novamente para garantir que atualizou
        List<Usuario> usuariosLidos = repository.lerDoJson();
        assertEquals(1, usuariosLidos.size());
        assertEquals("João Silva", usuariosLidos.get(0).getNome()); // Nome atualizado
        assertEquals("novaSenha123", usuariosLidos.get(0).getSenha()); // Senha atualizada
    }
}