package br.com.mangarosa.controller;

import br.com.mangarosa.collections.ListaReproducao;
import br.com.mangarosa.collections.Musica;
import br.com.mangarosa.player.ReprodutorLista;


import java.util.ArrayList;
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

    public void adicionarMusica(String titulo, String path, String artista){
        if (titulo == null || titulo.isEmpty()){
            throw new IllegalArgumentException("O título da música não pode ser vazio.");
        }

        Musica musica = new Musica(titulo, path, artista);
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

    public void adicionarMusicaListaReproducao(String tituloMusica, String tituloLista){
        Musica musica = repositorioMusicas.get(tituloMusica);
        ListaReproducao lista = listasReproducao.get(tituloLista);

        if (musica != null && lista != null) {
            lista.addMusica(musica);
        } else {
            System.out.println("Música ou lista não encontrada.");
        }
    }

    public void adicionarMusicaListaReproducaoEmPosicao(String tituloMusica, String tituloLista, int posicao) {
        Musica musica = repositorioMusicas.get(tituloMusica);
        ListaReproducao lista = listasReproducao.get(tituloLista);

        if (musica != null && lista != null) {
            lista.inserirMusica(posicao, musica);
        } else {
            System.out.println("Música ou lista não encontrada.");
        }
    }

    public void removerMusicaListaReproducao(String tituloMusica, String tituloLista) {
        ListaReproducao lista = listasReproducao.get(tituloLista);

        if (lista != null) {
            for (int i = 0; i < lista.tamanho(); i++) {
                Musica musica = lista.obterMusica(i);
                if (musica.getTitulo().equalsIgnoreCase(tituloMusica)) {
                    lista.removerMusica(i);
                    System.out.println("Música " + musica.getTitulo() + " removida com sucesso.");
                    return;
                }
            }
            System.out.println("Música não encontrada na lista.");
        }
    }

    public void removerMusicaListaReproducaoEmPosicao(String tituloLista, int posicao) {
        ListaReproducao lista = listasReproducao.get(tituloLista);
        if (lista != null) {
            lista.removerMusica(posicao);
        }
    }

    public void reproduzirListaDeReproducao(String tituloLista){
        ListaReproducao lista = listasReproducao.get(tituloLista);
        if (lista != null) {
            reprodutorLista.setListaReproducao(lista);
            reprodutorLista.executar();
        } else {
            System.out.println("Lista de reprodução não encontrada.");
        }
    }

    public void executarMusica(){
        reprodutorLista.executar();
    }

    public void pausarMusica(){
        reprodutorLista.pausar();
    }

    public void pararLista(){
        reprodutorLista.parar();
    }

    public void reiniciarLista(){
        reprodutorLista.reiniciarLista();
    }

    public void voltarMusica(){
        reprodutorLista.voltar();
    }

    public void passarMusica(){
        reprodutorLista.proxima();
    }

    public void reiniciarMusica(){
        reprodutorLista.reiniciarMusica();
    }

    public ListaReproducao getListaReproducao(String nomeLista) {
        return listasReproducao.get(nomeLista);
    }

    public java.util.Set<String> listarListas() {
        return listasReproducao.keySet();
    }

    public java.util.List<Musica> listarMusicas() {
        return new ArrayList<>(repositorioMusicas.values());
    }


}