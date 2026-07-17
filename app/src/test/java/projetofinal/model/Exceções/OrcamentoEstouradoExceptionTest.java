package projetofinal.model.Exceções;

import org.junit.jupiter.api.Test;

import projetofinal.model.Exceções.OrcamentoEstouradoException;

import static org.junit.jupiter.api.Assertions.*;

public class OrcamentoEstouradoExceptionTest {

    @Test
    public void deveArmazenarMensagemDeErroCorretamente() {
        String mensagemAlerta = "Limite de orçamento atingido e superado!";
        OrcamentoEstouradoException exception = new OrcamentoEstouradoException(mensagemAlerta);
        
        assertEquals(mensagemAlerta, exception.getMessage());
    }
}