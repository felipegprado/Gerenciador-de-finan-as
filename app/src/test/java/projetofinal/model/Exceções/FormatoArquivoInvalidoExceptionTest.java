package projetofinal.model.Exceções;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FormatoArquivoInvalidoExceptionTest {

    @Test
    public void deveArmazenarMensagemDeErro() {
        String mensagemErro = "O arquivo selecionado não é um CSV válido.";
        FormatoArquivoInvalidoException exception = new FormatoArquivoInvalidoException(mensagemErro);
        
        assertEquals(mensagemErro, exception.getMessage());
    }
}