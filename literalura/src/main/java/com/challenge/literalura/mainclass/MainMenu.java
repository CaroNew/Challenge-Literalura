package com.challenge.literalura.mainclass;

import com.challenge.literalura.models.DatosLibro;
import com.challenge.literalura.models.DatosLibros;
import com.challenge.literalura.service.ApiRequest;
import com.challenge.literalura.service.DataConversion;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private Scanner keyBoard = new Scanner(System.in);
    private final String BASE_URL = "https://gutendex.com/books";


    public void showMenu() {
        int option = 0;
        do {
            printMenu();
            option = keyBoard.nextInt();
            keyBoard.nextLine();
            switch (option) {
                case 1:
                    searchABookByTitle();
                    break;
                case 2:
                    System.out.println("listar libros");
                    break;
                case 3:
                    System.out.println("listar autores");
                    break;
                case 4:
                    System.out.println("listar autores vivos año");
                    break;
                case 5:
                    System.out.println("Listar libros por idioma");
                    break;
                case 6:
                    System.out.println("Adios!");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option != 6);

    }

    public void printMenu() {
        String menu = """
                ******************************
                \tBienvenido a LiterAlura
       
                1. Buscar libro por titulo
                2. Listar libros registrados
                3. Listar autores registrados
                4. Listar autores vivos año
                5. Listar libros por idioma
                6. Salir
                *******************************
                Elige una opción:
                """;
        System.out.println(menu);
    }

    public void searchABookByTitle() {
        DataConversion dataobj = new DataConversion();
        System.out.println("Introduce el titulo del libro a buscar: ");
        var title = keyBoard.nextLine();
        var url = BASE_URL + "/?search=" + title.replace(" ", "+");
        ///?search=quijote+de+la+mancha
        //System.out.println(url);
        ApiRequest request = new ApiRequest();
        String data = request.getData(url);
        //System.out.println(data);
        DataConversion dataConversion = new DataConversion();
        DatosLibros libros = dataConversion.convertData(data, DatosLibros.class);
        //System.out.println("es vacia: " + libros.libros().isEmpty());

        if(!libros.libros().isEmpty()) {
            DatosLibro libro = libros.libros().get(0);
            System.out.println("titulo: " + libro.titulo() +
                    " idioma: " + libro.idioma() +
                    " autor: " + libro.autor());

        }else{
            System.out.println("No se encontraron resultados");
        }


    }
}
