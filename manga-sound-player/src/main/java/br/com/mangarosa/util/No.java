package br.com.mangarosa.util;

import br.com.mangarosa.collections.Musica;

/**
 * Classe que representa um nó da lista encadeada.
 */
public class No {
    private Musica valor;  // Objeto Musica a ser armazenado
    private No prox;       // Referência para o próximo nó

    /**
     * Construtor padrão que inicializa um nó vazio.
     */
    public No() {
        this.valor = null;
        this.prox = null;
    }

    // Construtor que inicializa um nó com uma música.
    public No(Musica valor) {
        this.valor = valor;
        this.prox = null;
    }

    // --- Getters e Setters ---


    // Retorna a música armazenada no nó.
    public Musica getValor() {
        return this.valor;
    }

    // Retorna a referência para o próximo nó.

    public No getProx() {
        return this.prox;
    }

    // Define a música armazenada no nó.

    public void setValor(Musica valor) {
        this.valor = valor;
    }

    // Define o próximo nó na sequência.
    public void setProx(No prox) {
        this.prox = prox;
    }
}
