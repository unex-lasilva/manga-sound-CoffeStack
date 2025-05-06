package br.com.mangarosa;

import br.com.mangarosa.controller.MangaController;
import br.com.mangarosa.collections.*;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MangaSoundApplication {
    public static void main(String[] args) {
        MangaController controller = new MangaController();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("🎵 Bem-vindo ao MangaSound 🎵");

        while (continuar) {
            System.out.println("\nMenu:");
            System.out.println("1 - Adicionar Música ao Repositório");
            System.out.println("2 - Mostrar todas as Listas de Reprodução");
            System.out.println("3 - Mostrar Múisicas do repositório");
            System.out.println("4 - Criar Lista de Reprodução");
            System.out.println("5 - Editar Lista de Reprodução");
            System.out.println("6 - Executar uma Listaa de Reprodução");
            System.out.println("7 - Sair do Mangasound");
            System.out.println("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    adicionarMusica(controller, scanner);
                break;
                case 2:
                    mostrarListas(controller);
                    break;
                case 3:
                    mostrarMusicasResources(controller);
                    break;
                case 4:
                    criarLista(controller, scanner);
                    break;
                case 5:
                    editarLista(controller, scanner);
                    break;
                case 6:
                    executarLista(controller, scanner);
                    break;
                case 7:
                    continuar = false;
                    System.out.println("Encerrando o MangaSound. Até mais! 🎵");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void adicionarMusica(MangaController controller, Scanner scanner) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resourceUrl = classLoader.getResource("audios");

            if (resourceUrl == null) {
                System.out.println("Diretório 'audios' não encontrado dentro de resources.");
                return;
            }

            File audiosDir = new File(resourceUrl.toURI());
            File[] arquivos = audiosDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

            if (arquivos == null || arquivos.length == 0) {
                System.out.println("Nenhum arquivo .wav disponível para adicionar.");
                return;
            }

            System.out.println("🎶 Arquivos disponíveis:");
            for (int i = 0; i < arquivos.length; i++) {
                System.out.println((i + 1) + " - " + arquivos[i].getName());
            }

            System.out.print("Digite o número da música que deseja adicionar: ");
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            if (escolha < 1 || escolha > arquivos.length) {
                System.out.println("Escolha inválida.");
                return;
            }

            String nomeArquivoEscolhido = arquivos[escolha - 1].getName();

            System.out.print("Digite o título para a música: ");
            String titulo = scanner.nextLine();

            System.out.print("Digite o nome do artista: ");
            String artista = scanner.nextLine();

            File repositorio = new File("repositorio");
            if (!repositorio.exists()) {
                repositorio.mkdirs();
            }

            File destino = new File(repositorio, nomeArquivoEscolhido);

            try (InputStream inputStream = classLoader.getResourceAsStream("audios/" + nomeArquivoEscolhido)) {
                if (inputStream == null) {
                    System.out.println("Não foi possível localizar o arquivo dentro dos recursos!");
                    return;
                }
                Files.copy(inputStream, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            String caminhoRelativo = destino.getPath();
            controller.adicionarMusica(titulo, caminhoRelativo, artista);

            System.out.println("🎵 Música adicionada ao repositório com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao copiar o arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }


    private static void criarLista(MangaController controller, Scanner scanner) {
        System.out.print("Digite o nome da nova lista de reprodução: ");
        String tituloLista = scanner.nextLine();

        try {
            controller.criarListaReproducao(tituloLista);
            System.out.println("\uD83D\uDCC0 Lista de reprodução '" + tituloLista + "' criada com sucesso!");

            System.out.print("Deseja adicionar músicas agora? (S/N): ");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("S")) {
                boolean adicionando = true;
                while (adicionando) {
                    System.out.println("\nMúsicas disponíveis para adicionar:");
                    mostrarMusicasResources(controller);
                    System.out.print("Digite o título da música para adicionar (ou 'sair' para voltar): ");
                    String tituloMusica = scanner.nextLine();
                    if (tituloMusica.equalsIgnoreCase("sair")) {
                        adicionando = false;
                    } else {
                        controller.adicionarMusicaListaReproducao(tituloMusica, tituloLista);
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void editarLista(MangaController controller, Scanner scanner) {
        System.out.print("Digite o nome da lista de reprodução que deseja editar: ");
        String tituloLista = scanner.nextLine();

        ListaReproducao lista = controller.getListaReproducao(tituloLista);
        if (lista == null) {
            System.out.println("Lista não encontrada");
            return;
        }
        if (lista.isVazia()) {
            System.out.println("A lista está vazia. Adicione músicas antes de editar.");
            return;
        }
        System.out.println("🎵 Músicas da lista '" + tituloLista + "':");
        for (int i = 0; i < lista.tamanho(); i++) {
            Musica musica = lista.obterMusica(i);
            System.out.println(i + ". " + musica.getTitulo() + " - " + musica.getArtista());
        }
        System.out.print("\nDigite o número da música que deseja mover: ");
        int musicaAtual = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        System.out.print("Digite a nova posição para essa música: ");
        int novaPosicao = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        if (musicaAtual < 0 || musicaAtual >= lista.tamanho() || novaPosicao < 0 || novaPosicao >= lista.tamanho()) {
            System.out.println("Posições inválidas. Operação cancelada.");
            return;
        }

        Musica musicaParaMover = lista.obterMusica(musicaAtual);
        lista.removerMusica(musicaAtual);
        lista.inserirMusica(novaPosicao, musicaParaMover);

        System.out.println("✅ Música movida com sucesso!");
    }

    private static void mostrarListas(MangaController controller) {
        if (controller.listarListas().isEmpty()) {
            System.out.println("Nenhuma lista de reprodução cadastrada.");
        } else {
            System.out.println("📀 Listas de Reprodução:");
            for (String nomeLista : controller.listarListas()) {
                System.out.println("- " + nomeLista);
            }
        }
    }

    private static void mostrarMusicasResources(MangaController controller) {
        System.out.println("🎵 Músicas disponíveis no repositório:");

        java.util.List<Musica> musicas = controller.listarMusicas();

        if (musicas.isEmpty()) {
            System.out.println("Nenhuma música cadastrada.");
        } else {
            for (Musica musica : musicas) {
                System.out.println("- " + musica.getTitulo() + " | Artista: " + musica.getArtista() + " | Caminho: " + musica.getPath());
            }
        }

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resourceUrl = classLoader.getResource("audios");

            if (resourceUrl != null) {
                File audiosDir = new File(resourceUrl.toURI());
                File[] arquivos = audiosDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

                if (arquivos != null && arquivos.length > 0) {
                    System.out.println("\nArquivos encontrados no diretório 'resources/audios':");
                    for (File arquivo : arquivos) {
                        System.out.println("- " + arquivo.getName());
                    }
                } else {
                    System.out.println("\nNenhum arquivo .wav encontrado em 'resources/audios'.");
                }
            } else {
                System.out.println("\nDiretório 'audios' não encontrado dentro de resources.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar listar músicas do resources/audios: " + e.getMessage());
        }
    }


    private static void executarLista(MangaController controller, Scanner scanner) {
        System.out.print("Digite o nome da lista de reprodução que deseja executar: ");
        String tituloLista = scanner.nextLine();
        ListaReproducao lista = controller.getListaReproducao(tituloLista);
        if (lista == null) {
            System.out.println("Lista não encontrada");
            return;
        }
        controller.reproduzirListaDeReproducao(tituloLista);

        boolean tocando = true;

        while (tocando) {
            System.out.println("\n\uD83C\uDFB5 Controle de reprodução: ");
            System.out.println("1 - Pausar música");
            System.out.println("2 - Reiniciar música");
            System.out.println("3 - Proxima música");
            System.out.println("4 - Voltar música");
            System.out.println("5 - Parar Lista de Reprodução");
            System.out.println("6 - Reiniciar Lista de Reprodução");
            System.out.println("7 - Sair da Lista de Reprodução");
            System.out.println("Escolha uma opção: ");

            int escolha = scanner. nextInt();

            switch (escolha) {
                case 1:
                    controller.pausarMusica();
                    break;
                case 2:
                    controller.reiniciarMusica();
                    break;
                case 3:
                    controller.passarMusica();
                    break;
                case 4:
                    controller.voltarMusica();
                    break;
                case 5:
                    controller.pararLista();
                    break;
                case 6:
                    controller.reiniciarLista();
                    break;
                case 7:
                    controller.pararLista();
                    tocando = false;
                    System.out.println("⏹️ Saindo da reprodução...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        }
    }
}