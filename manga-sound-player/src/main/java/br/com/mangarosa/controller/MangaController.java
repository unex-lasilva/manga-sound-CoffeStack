package br.com.mangarosa.controller;

import br.com.mangarosa.collections.ListaReproducao;
import br.com.mangarosa.collections.Musica;
import br.com.mangarosa.player.ReprodutorLista;

import java.util.HashMap;
import java.util.Map;

public class MangaController {
    private Map<String, Musica> repositorioMusicas;
    private Map<String, ListaReproducao> listasReproducao;
    private String artista;
    private ReprodutorLista reprodutorLista;

    public MangaController(){
        this.repositorioMusicas = new HashMap<>();
        this.listasReproducao = new HashMap<>();
        this.reprodutorLista = new ReprodutorLista();
    }

    public void adicionarMusica(String titulo, String artista, String caminhoArquivo){
        if (titulo == null || titulo.isEmpty()){
            throw new IllegalArgumentException("O título da música não pode ser vazio.");
        }

        Musica musica = new Musica(titulo, artista, caminhoArquivo);
        repositorioMusicas.put(titulo, musica);
    }

    public void criarListaReproducao(String nomeLista){
        if (listasReproducao.containsKey(nomeLista)){
            throw new IllegalArgumentException("Já existe uma lista com esse nome!");
        }
        listasReproducao.put(nomeLista, new ListaReproducao(nomeLista));
    }

    public boolean excluirListaReproducao(String nomeLista){
        return listasReproducao.remove(nomeLista) != null;
    }

    public void removerMusicaReproducao(String nomeLista, String tituloMusica){
        ListaReproducao lista = listasReproducao.get(nomeLista);
        if (lista != null){
            lista.removerMusica(tituloMusica);
        }
    }

    public void removerMusicaPosicao(String nomeLista, int posicao){
        ListaReproducao lista = listasReproducao.get(nomeLista);
        if (lista != null){
            lista.removerMusica(posicao);
        }
    }

    public void executarMusica(){
        reprodutorLista.executar();
    }

    public void pausarMusica(){
        reprodutorLista.pausar();
    }

    public void pararMusica(){
        reprodutorLista.parar();
    }

    public void reiniciarMusica(){
        reprodutorLista.reiniciar();
    }

    public void voltarMusica(){
        reprodutorLista.voltar();
    }

    public void passarMusica(){
        reprodutorLista.proxima();
    }
}
