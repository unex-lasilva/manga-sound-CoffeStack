package br.com.mangarosa.collections;

public class Musica {
    private String titulo;
    private int duracao;
    private String path;
    private String artista;

    public Musica(String titulo, int duracao, String path, String artista) {
        this.titulo = titulo;
        this.duracao = duracao;
        this.path = path;
        this.artista = artista;

    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getPath() {
        return path;
    }

    public String getArtista() {
        return artista;
    }

    @Override
    public String toString() {
        return titulo + " - " + artista + " (" + path + ", " + duracao + "s)";
    }

}