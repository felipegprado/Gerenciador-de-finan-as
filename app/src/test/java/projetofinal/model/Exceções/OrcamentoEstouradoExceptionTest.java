package projetofinal.model.Exceções;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OrcamentoEstouradoExceptionTest {

    @Test
    public void deveArmazenarMensagemDeErroCorretamente() {
        String mensagemAlerta = "Limite de orçamento atingido e superado!";
        OrcamentoEstouradoException exception = new OrcamentoEstouradoException(mensagemAlerta);
        
        assertEquals(mensagemAlerta, exception.getMessage());
    }
}