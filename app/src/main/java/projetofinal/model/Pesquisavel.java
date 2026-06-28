package projetofinal.model;

import java.util.List;

public interface Pesquisavel {
    List<Transacao> fazerBusca(String termo);
}