package br.com.theusmm.buscafipeapi.principal;

import br.com.theusmm.buscafipeapi.model.Dados;
import br.com.theusmm.buscafipeapi.model.Modelos;
import br.com.theusmm.buscafipeapi.model.Veiculo;
import br.com.theusmm.buscafipeapi.service.ConsumoApi;
import br.com.theusmm.buscafipeapi.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        System.out.println("\nMarcas de " + opcao);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("\nDigite o código da marca desejada: ");
        endereco += "/" + leitura.nextLine() + "/modelos";

        json = consumo.obterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca");

        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para buscar os valores");

        endereco += "/" + leitura.nextLine() + "/anos";
        json = consumo.obterDados(endereco);

        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);

            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados por ano: ");
        veiculos.forEach(System.out::println);

    }
}