package br.com.mangarosa.player;

import br.com.mangarosa.collections.Musica;
import br.com.mangarosa.collections.ListaReproducao;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;

public class ReprodutorLista {
    private ListaReproducao listaReproducao;
    private String status;
    private Clip clip;
    private int indiceMusicaAtual;
    private long minutoPausa = 0;

    public ReprodutorLista() {
        this.listaReproducao = new ListaReproducao("Lista Vazia");
        this.status = "parado";
        this.clip = null;
        this.indiceMusicaAtual = 0;
    }
    public ReprodutorLista(ListaReproducao listaReproducao) {
        this.listaReproducao = listaReproducao;
        status = "parado";

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    new File(listaReproducao.obterMusica(indiceMusicaAtual).getPath()));
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
        } catch (Exception e) {
            System.out.println("Erro ao carregar a música: " + e.getMessage());
        }
    }

    public ListaReproducao getListaReproducao() {
        return this.listaReproducao;
    }

    public void setListaReproducao(ListaReproducao lista) {
        if(lista == null || lista.isVazia()){
            System.out.println("A lista está vázia ou inválida");
            return;
        }

        this.listaReproducao = lista;
        this.indiceMusicaAtual = 0;
        this.status = "parado";

        System.out.println("Lista de reprodução carregada: " + lista.getTitulo());
    }

    public void executar() {
        if(listaReproducao.isVazia()) {
            System.out.println("A lista de reprodução está vazia");
            return;
        }

        // Verifica se o índice está dentro dos limites da lista
        if(indiceMusicaAtual < 0 || indiceMusicaAtual >= listaReproducao.tamanho()) {
            System.out.println("Índice da música inválido");
            return;
        }

        // Obtem a música atual da lista
        Musica musica = listaReproducao.obterMusica(indiceMusicaAtual);
        if (musica == null || musica.getPath() == null || musica.getPath().isEmpty()) {
            System.out.println("Caminho da música inválido");
            return;
        }

        // Tenta abrir o arquivo .wav e tocar
        try {
            File arquivo = new File(musica.getPath());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);

            // Fecha o clip anterior se ainda estiver aberto
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close();
            }

            // Cria novo clip
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            status = "tocando";
            System.out.println("Tocando: " + musica.getTitulo());

        } catch (Exception e) {
            System.out.println("Erro ao tentar reproduzir a música. " + e.getMessage());
        }
    }

    public void pausar() {
        if (clip != null && clip.isRunning()) {
            minutoPausa = clip.getMicrosecondPosition();
            clip.stop();
            status = "pausado";
        }
    }

    public void parar() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            status = "parado";
            minutoPausa = 0;
        }
    }

    public void reiniciarMusica() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            clip.start();
            status = "tocando";
            minutoPausa = 0;
        }
    }

    public void reiniciarLista() {
        if (listaReproducao != null && !listaReproducao.isVazia()) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            indiceMusicaAtual = 0;
            executar();
        }
    }

    public void proxima() {
        if(listaReproducao != null && !listaReproducao.isVazia()) {
            if (indiceMusicaAtual + 1 < listaReproducao.tamanho()) {
                indiceMusicaAtual++;

                if (clip != null && clip.isRunning()) {
                    clip.stop();
                    clip.close();

                    executar();
                }
                }else {
                System.out.println("Você está na ultima música da sua lista de reprodução.");
            }
        }
    }

        public void voltar() {
            if (clip != null && clip.isRunning()) {
                long posicaoAtual = clip.getMicrosecondLength();

                if (posicaoAtual > 10_000_000) {
                    reiniciarMusica();
                    System.out.println("A música foi reiniciada.");
                    return;
                }
            }

            if(listaReproducao != null && !listaReproducao.isVazia()) {
                if (indiceMusicaAtual > 0) {
                indiceMusicaAtual--;

                    if (clip != null && clip.isRunning()) {
                    clip.stop();
                    clip.close();

                    executar();
                    }
                }else {
                System.out.println("Você está na primeira música da sua lista de reprodução.");
            }
        }
    }
}
