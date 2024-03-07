package br.com.theusmm.buscafipeapi.principal;

import br.com.theusmm.buscafipeapi.model.Dados;
import br.com.theusmm.buscafipeapi.model.Modelos;
import br.com.theusmm.buscafipeapi.service.ConsumoApi;
import br.com.theusmm.buscafipeapi.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

/* https://deividfortuna.github.io/fipe/ */

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private String endereco = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("""
                \n
                --- OPÇÕES ---
                CÓDIGO - TIPO DE VEÍCULO
                     1 - Carros
                     2 - Motos
                     3 - Caminhões
                     
                Digite o código de um dos tipos de veículos:""");

        var opcao = leitura.nextLine();

        if (opcao.equals("1")) {
            opcao = "carros";
        } else if (opcao.equals("2")) {
            opcao = "motos";
        } else if (opcao.equals("3")) {
            opcao = "caminhoes";
        } else {
            System.out.println("Código invalido!");
            System.exit(0);
        }

        endereco += opcao + "/marcas";
        var json = consumo.obterDados(endereco);

        System.out.println("\n Marcas de " + opcao);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("\nDigite o código da marca desejada: ");
        endereco += "/" + leitura.nextLine() + "/modelos";

        json = consumo.obterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\n Modelos dessa marca");

        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);
    }
}
