package projetofinal.model.Interfaces;

import java.util.List;

import projetofinal.model.Transações.Transacao;

/**
 * Interface que é implementada pelas classes que fazem a busca
 * 
 * @see Carteira
 *      Pesquisavel
 */
public interface Pesquisavel {
    List<Transacao> fazerBusca(String termo);
}