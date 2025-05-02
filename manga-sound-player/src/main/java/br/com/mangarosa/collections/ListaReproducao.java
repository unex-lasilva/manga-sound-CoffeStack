package br.com.mangarosa.collections;

public class ListaReproducao {
    private ListaEncadeada lista;
    private String titulo;

    public ListaReproducao(String titulo) {
        this.lista = new ListaEncadeada();
        this.titulo = "";
    }

    public void addMusica(Musica musica) {
        lista.append(musica);
    }

    public void removerMusica(int posicao) {
        lista.remove(posicao);
    }

    public void inserirMusica(int posicao, Musica musica) {
        lista.insertAt(posicao , musica);
    }

    public boolean isVazia() {
        return lista.isEmpty();
    }

    public int tamanho() {
        return lista.size();
    }

    public int posicaoDaMusica(Musica musica) {
        return lista.indexOf(musica);
    }

    public boolean conterMusica(Musica musica) {
        return lista.contains(musica);
    }

    public Musica obterMusica(int posicao) {
        return lista.get(posicao);
    }

    public void limparLista() {
        lista.clear();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void criarListaAPartirDe(ListaReproducao outraLista) {
        this.lista = new ListaEncadeada();
        for (int i = 0; i < outraLista.tamanho(); i++) {
            Musica musica = outraLista.obterMusica(i);
            this.addMusica(musica);
        }
        this.titulo = outraLista.getTitulo();
    }
}