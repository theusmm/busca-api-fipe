package br.com.theusmm.buscafipeapi.principal;

import br.com.theusmm.buscafipeapi.service.ConsumoApi;
import java.util.Scanner;

/* https://deividfortuna.github.io/fipe/ */

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();

    private String endereco = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("""
                --- OPÇÕES ---
                CÓDIGO - TIPO DE VEÍCULO
                     1 - Carros
                     2 - Motos
                     3 - Caminhões
                     
                Digite o código de um dos tipos de veículos:""");

        var opcao = leitura.nextLine();

        if (opcao.equals("1")) {
            endereco += "carros/marcas";
        } else if (opcao.equals("2")) {
            endereco += "motos/marcas";
        } else if (opcao.equals("3")) {
            endereco += "caminhoes/marcas";
        } else {
            System.out.println("Código invalido!");
            System.exit(0);
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);
    }
}
