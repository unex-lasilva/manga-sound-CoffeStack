package br.com.mangarosa.collections;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.io.IOException;

public class Musica {
    private String titulo;
    private int duracao;
    private String path;
    private String artista;

    public Musica(String titulo, String path, String artista) {
        this.titulo = titulo;
        this.path = path;
        this.artista = artista;
        this.duracao = calcularDuracaoMusica(this.path);

    }

    private int calcularDuracaoMusica(String path) {
        try {
            File file = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            float frameRate = format.getFrameRate();
            float durationInSeconds = frames / frameRate;
            return (int) durationInSeconds;
        } catch (UnsupportedAudioFileException | IOException e) {
            System.out.println("Erro ao calcular duração da música: " + e.getMessage());
            return 0;
        }
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