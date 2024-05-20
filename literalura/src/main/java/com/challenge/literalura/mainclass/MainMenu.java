package com.challenge.literalura.mainclass;

import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);


    public void showMenu() {
        int option = 0;
        do {
            printMenu();
            option = scanner.nextInt();
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
        System.out.println("Buscar y agregar un libro");
    }
}
