package com.challenge.literalura.mainclass;

import com.challenge.literalura.models.DatosLibro;
import com.challenge.literalura.models.DatosLibros;
import com.challenge.literalura.service.ApiRequest;
import com.challenge.literalura.service.DataConversion;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private Scanner keyBoard = new Scanner(System.in);
    private final String BASE_URL = "https://gutendex.com/books";
    private List<DatosLibro> library = new ArrayList<>();


    public void showMenu() {
        int option = 0;
        do {
            printMenu();
            //TODO: Validar que la opcion sea un numero
            option = keyBoard.nextInt();
            keyBoard.nextLine();
            switch (option) {
                case 1:
                    searchABookByTitle();
                    break;
                case 2:
                    getAllBooks();
                    break;
                case 3:
                    getAuthors();
                    break;
                case 4:
                    getAuthorsAliveInYear();
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
            library.add(libro);
            System.out.println("titulo: " + libro.titulo() +
                    " idioma: " + libro.idioma().get(0) +
                    " autor: " + libro.autor().get(0));

        }else{
            System.out.println("No se encontraron resultados");
        }


    }

    private void getAllBooks() {
        System.out.println("listar libros");
        library.stream()
                .forEach(libro -> {
            System.out.println("titulo: " + libro.titulo() +
                    " idioma: " + libro.idioma() +
                    " autor: " + libro.autor());
        });
    }


    private void getAuthors() {
        library.stream()
                .forEach(libro -> {
                    libro.autor().stream()
                            .forEach(autor -> {
                                System.out.println("autor: " + autor.nombre());
                            });
                });
    }
    //autores vivos en un año determinado
    private void getAuthorsAliveInYear() {
        System.out.println("ingrese año: ");
        //TODO: Validar que el año sea un numero
        var year = keyBoard.nextInt();
        keyBoard.nextLine();
        library.stream()
                .forEach(libro -> {
                    libro.autor().stream()
                            //Filtrar autores vivos en el año (usar filter!)
                            .forEach(autor -> {
                                if(autor.nacimiento() <= year && autor.muerte() >= year){
                                    System.out.println("autor: " + autor.nombre());
                                }else{
                                    System.out.println("No hay autores vivos en el año: " + year);
                                }
                            });
                });
    }
}
