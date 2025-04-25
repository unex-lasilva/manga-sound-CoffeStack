package br.com.mangarosa.collections;

public class ListaReproducao {
    private ListaEncadeada<Musica> lista;
    private String titulo;

    public ListaReproducao() {
        this.lista = new ListaEncadeada<>();
        this.titulo = "";
    }

    public void addMusica(Musica musica) {
        lista.adicionar(musica);
    }

    public void removerMusica(int posicao) {
        lista.remover(posicao);
    }

    public void inserirMusica(int posicao, Musica musica) {
        lista.inserir(posicao , musica);
    }

    public boolean isVazia() {
        return lista.estaVazia();
    }

    public int tamanho() {
        return lista.getTamanho();
    }

    public int posicaoDaMusica(Musica musica) {
        return lista.indiceDe(musica);
    }

    public boolean conterMusica(Musica musica) {
        return lista.obter(posicao);
    }

    public Musica obterMusica(int posicao) {
        return lista.obter(posicao);
    }

    public void limparLista() {
        lista.limpar();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void criarListaAPartirDe(ListaReproducao outraLista) {
        this.lista = new ListaEncadeada<>();
        for (int i = 0; i < outraLista.tamanho(); i++) {
            Musica musica = outraLista.obterMusica(i);
            this.addMusica(musica);
        }
        this.titulo = outraLista.getTitulo();
    }
}