package br.com.mangarosa.collections;

import br.com.mangarosa.util.No;

/**
 * Implementação da lista encadeada com métodos especificados no diagrama.
 */
public class ListaEncadeada {
    private No cabeca;  // Primeiro nó da lista
    private int tamanho;  // Quantidade de elementos

    // Construtor
    public ListaEncadeada() {
        this.cabeca = null;
        this.tamanho = 0;
    }

    /**
     * Adiciona uma música no final da lista.
     */
    public void append(Musica value) {
        No novoNo = new No(value);
        if (cabeca == null) {
            cabeca = novoNo;
        } else {
            No atual = cabeca;
            while (atual.getProx() != null) {
                atual = atual.getProx();
            }
            atual.setProx(novoNo);
        }
        tamanho++;
    }

    /**
     * Remove o nó na posição especificada.
     */
    public boolean remove(int position) {
        if (position < 0 || position >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida: " + position);
        }

        if (position == 0) {
            cabeca = cabeca.getProx();
        } else {
            No anterior = cabeca;
            for (int i = 0; i < position - 1; i++) {
                anterior = anterior.getProx();
            }
            No removido = anterior.getProx();
            anterior.setProx(removido.getProx());
        }

        tamanho--;
        return true;
    }

    /**
     * Insere uma música em uma posição específica.
     */
    public void insertAt(int position, Musica value) {
        if (position < 0 || position > tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida: " + position);
        }

        No novoNo = new No(value);
        if (position == 0) {
            novoNo.setProx(cabeca);
            cabeca = novoNo;
        } else {
            No anterior = cabeca;
            for (int i = 0; i < position - 1; i++) {
                anterior = anterior.getProx();
            }
            novoNo.setProx(anterior.getProx());
            anterior.setProx(novoNo);
        }

        tamanho++;
    }

    /**
     * Verifica se a lista está vazia.
     */
    public boolean isEmpty() {
        return cabeca == null;
    }

    /**
     * Retorna o tamanho da lista.
     */
    public int size() {
        return tamanho;
    }

    /**
     * Adiciona todos os elementos de outra lista.
     */
    public void addAll(ListaEncadeada list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        No atual = list.cabeca;
        while (atual != null) {
            this.append(atual.getValor());
            atual = atual.getProx();
        }
    }

    /**
     * Retorna a posição da primeira ocorrência de uma música.
     */
    public int indexOf(Musica value) {
        if (value == null || cabeca == null) {
            return -1;
        }

        No atual = cabeca;
        int indice = 0;

        while (atual != null) {
            if (value.equals(atual.getValor())) {
                return indice;
            }
            atual = atual.getProx();
            indice++;
        }
        return -1;
    }

    /**
     * Verifica se a lista contém uma música.
     */
    public boolean contains(Musica value) {
        return indexOf(value) != -1;
    }

    /**
     * Limpa a lista.
     */
    public boolean clear() {
        if (isEmpty()) {
            return false;
        }
        cabeca = null;
        tamanho = 0;
        return true;
    }

    /**
     * Retorna a música na posição especificada.
     */
    public Musica get(int position) {
        if (position < 0 || position >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida: " + position);
        }

        No atual = cabeca;
        for (int i = 0; i < position; i++) {
            atual = atual.getProx();
        }

        return atual.getValor();
    }
}