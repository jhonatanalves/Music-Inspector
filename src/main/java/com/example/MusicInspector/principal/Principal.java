package com.example.MusicInspector.principal;

import com.example.MusicInspector.model.Cantor;
import com.example.MusicInspector.model.GeneroMusical;
import com.example.MusicInspector.model.Musica;
import com.example.MusicInspector.model.TipoCantor;
import com.example.MusicInspector.repository.CantorRepository;

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
                    1 - salvar um cantor
                    2 - salvar musicas de um cantor
                                     
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
        var cantor = leitura.nextLine();
        Optional<Cantor> cantorOptional = cantorRepository.buscarCantor(cantor);


        if (cantorOptional.isPresent()) {
            System.out.println("Cantor encontrado!");
          //  serie.setEpisodios(episodios);
          //  serieRepositorio.save(serie);
        } else {
            System.out.println("Cantor não encontrada!");
        }


        var musicas = new ArrayList<Musica>();

        // verificar se o artista existe no banco para salvar as musicas
        //apos salvar uma musica, perguntar se quer salvar outra

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

            Musica musica = new Musica(nome,letra,duracao_,genero_);
            System.out.println(musica);

            musicas.add(musica);

            System.out.println("Deseja salvar uma nova música? (y/n)");
            opcao = leitura.nextLine();

        }


    }
}
