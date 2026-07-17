package projetofinal.model.Exceções;

import org.junit.jupiter.api.Test;

import projetofinal.model.Exceções.FormatoArquivoInvalidoException;

import static org.junit.jupiter.api.Assertions.*;

public class FormatoArquivoInvalidoExceptionTest {

    @Test
    public void deveArmazenarMensagemDeErro() {
        String mensagemErro = "O arquivo selecionado não é um CSV válido.";
        FormatoArquivoInvalidoException exception = new FormatoArquivoInvalidoException(mensagemErro);
        
        assertEquals(mensagemErro, exception.getMessage());
    }
}