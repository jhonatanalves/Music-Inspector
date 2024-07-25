package com.example.MusicInspector.principal;

import com.example.MusicInspector.model.Cantor;
import com.example.MusicInspector.model.GeneroMusical;
import com.example.MusicInspector.model.Musica;
import com.example.MusicInspector.model.TipoCantor;
import com.example.MusicInspector.repository.CantorRepository;
import com.example.MusicInspector.service.ConsultaChatGPT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Principal {

    private final Scanner leitura;
    private CantorRepository cantorRepository;

    public Principal(CantorRepository cantorRepository) {
        leitura = new Scanner(System.in);
        this.cantorRepository = cantorRepository;
    }

    public void exibeMenu() {

        var opcao = -1;
        while (opcao != 0) {

            var menu = """
                    \s
                    1- Salvar um cantor
                    2- Salvar músicas de um cantor
                    3- Listar músicas de um cantor
                    4- Listar todas as musicas
                    5- Pesquisar dados sobre um cantor
                    \s
                    0 - Sair\s
                    \s""";

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 0:
                    System.out.println("Saindo...");
                    break;
                case 1:
                    salvarCantor();
                    break;
                case 2:
                    salvarMusicas();
                    break;
                case 3:
                    listarMusicasDeUmCantor();
                    break;
                case 4:
                    listarMusicasDeTodosCantores();
                    break;
                case 5:
                    pesquisarCantor();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }

    private void salvarCantor() {

        System.out.println("Qual o nome do cantor(a)?");
        var nome = leitura.nextLine();

        System.out.println("Qual a idade?");
        var idade = Integer.parseInt(leitura.nextLine());

        System.out.println("Ele(a) canta solo, em dupla ou banda?");
        var tipoCantor = leitura.nextLine();
        TipoCantor tipo = TipoCantor.valueOf(tipoCantor.toUpperCase());

        System.out.println("Qual a data de nascimento (dd/MM/yyyy)?");
        var dataNacimento = leitura.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data;
        try {
            data = formatter.parse(dataNacimento);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Qual a nacionalidade?");
        var nacionalidade = leitura.nextLine();

        Cantor cantor = new Cantor(nome, idade, data, nacionalidade, tipo);
        System.out.println(cantor);
        cantorRepository.save(cantor);
    }

    private List<Cantor> buscarCantores() {
        return cantorRepository.findAll();
    }

    private void salvarMusicas() {

        List<Cantor> cantores = buscarCantores();
        System.out.println("Lista de cantores cadastrados: ");
        cantores.forEach(System.out::println);

        System.out.println("Para qual cantor você deseja cadastrar uma música?");
        var cantorNome = leitura.nextLine();
        Optional<Cantor> cantorOptional = cantorRepository.buscarCantor(cantorNome);

        if (cantorOptional.isPresent()) {
            Cantor cantor = cantorOptional.get();
            System.out.println("Cantor encontrado!");
            var musicas = new HashSet<Musica>();

            var opcao = "y";
            while (opcao.equalsIgnoreCase("y")) {

                System.out.println("Qual o nome da música?");
                var nome = leitura.nextLine();

                System.out.println("Qual a duração  (formato MM:SS)?");
                var duracao = leitura.nextLine();
                Duration duracao_ = null;

                try {
                    String[] partes = duracao.split(":");
                    int minutos = Integer.parseInt(partes[0]);
                    int segundos = Integer.parseInt(partes[1]);

                    duracao_ = Duration.ofMinutes(minutos).plusSeconds(segundos);

                } catch (Exception e) {
                    // lançar uma exceção adequada
                    System.out.println("Formato de duração inválido. Use MM:SS.");
                }

                System.out.println("Qual o gênero?");
                var genero = leitura.nextLine();
                GeneroMusical genero_ = GeneroMusical.valueOf(genero.toUpperCase());

                //buscar essa info futuramente com o ChatGPT
                System.out.println("Qual a letra?");
                var letra = leitura.nextLine();

                Musica musica = new Musica(nome, letra, duracao_, genero_);
                //musica.setCantores();
                System.out.println(musica);

                musicas.add(musica);

                System.out.printf("Deseja salvar uma nova música para %s? (y/n)%n", cantor.getNome());
                opcao = leitura.nextLine();
            }

            cantor.setMusicas(musicas);
            cantorRepository.save(cantor);

        } else {
            System.out.println("Cantor(a) não encontrado(a)!");
        }
    }

    private void listarMusicasDeUmCantor() {
        List<Cantor> cantores = buscarCantores();
        System.out.println("Lista de cantores cadastrados: ");
        cantores.forEach(System.out::println);

        System.out.println("De qual cantor você deseja listar as músicas?");
        var cantorNome = leitura.nextLine();
        Optional<Cantor> cantorOptional = cantorRepository.buscarCantor(cantorNome);

        if (cantorOptional.isPresent()) {
            Cantor cantor = cantorOptional.get();
            System.out.println("Cantor encontrado!");
            var musicas = cantorRepository.buscarMusicasDeUmCantor(cantor.getNome());
            musicas.forEach(System.out::println);
        }
    }

    private void listarMusicasDeTodosCantores() {

        Set<Musica> musicas = cantorRepository.buscarMusicasDeTodosCantores();
        if (!musicas.isEmpty()) {
            musicas.forEach(System.out::println);
        }
    }

    private void pesquisarCantor() {

        List<Cantor> cantores = buscarCantores();
        System.out.println("Lista de cantores cadastrados: ");
        cantores.forEach(System.out::println);

        System.out.println("Para qual cantor você deseja pesquisar informações?");
        var cantorNome = leitura.nextLine();
        Optional<Cantor> cantorOptional = cantorRepository.buscarCantor(cantorNome);

        if (cantorOptional.isPresent()) {

            var opcao = "y";
            while (opcao.equalsIgnoreCase("y")) {

                System.out.printf("O que você gostaria de saber sobre o(a) cantor(a) %s%n", cantorNome);
                var pergunta = leitura.nextLine();

                var prompt = String.format("responda sucintamente a seguinte pergunta sobre o(a) cantor(a) '%s': '%s'",
                        cantorNome, pergunta);

                var resposta = ConsultaChatGPT.consultar(prompt);
                System.out.println(resposta);

                System.out.printf("Gostaria de fazer mais perguntas sobre o(a) cantor(a) %s%n? (y/n)", cantorNome);
                opcao = leitura.nextLine();

            }
        }
    }
}
