package projetofinal.model.Interfaces;

import java.util.List;

import projetofinal.model.Transações.Transacao;

public interface Pesquisavel {
    List<Transacao> fazerBusca(String termo);
}