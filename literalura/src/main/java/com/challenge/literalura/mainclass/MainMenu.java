package com.challenge.literalura.mainclass;

import com.challenge.literalura.models.Autor;
import com.challenge.literalura.models.DatosLibro;
import com.challenge.literalura.models.DatosLibros;
import com.challenge.literalura.models.Libro;
import com.challenge.literalura.repository.AutorRepository;
import com.challenge.literalura.repository.LibroRepository;
import com.challenge.literalura.service.ApiRequest;
import com.challenge.literalura.service.DataConversion;

import java.util.*;

public class MainMenu {
    private Scanner keyBoard = new Scanner(System.in);
    private final String BASE_URL = "https://gutendex.com/books";
    private List<Libro> bookSearched = new ArrayList<>();
    private List<Autor> authorsSearched = new ArrayList<>();

    //Inyeccion de dependencias
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public MainMenu(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


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
                    getBooksByLanguage();
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

    //Obtiene datos de la web
    public String getWebData(String title) {
        ApiRequest request = new ApiRequest();
        var url = BASE_URL + "/?search=" + title.replace(" ", "+");
        return request.getData(url);
    }
    //Convierte los datos de la web a un objeto DatosLibros
    public DatosLibros jsonToDatosLibros(String data) {
        DataConversion dataConversion = new DataConversion();
        return dataConversion.convertData(data, DatosLibros.class);
    }
    //Guarda el primer libro que tenga autor
    public DatosLibro getFirstWithAuthor(List<DatosLibro> libros) {
        return libros.stream()
                .filter(libro -> !libro.autor().isEmpty())
                .findFirst()
                .orElse(null);
    }

    public void searchABookByTitle() {

        System.out.println("Introduce el titulo del libro a buscar: ");
        var title = keyBoard.nextLine();

        String data = getWebData(title);
        DatosLibros libros = jsonToDatosLibros(data);

        if(!libros.libros().isEmpty()) {
            Autor authorToSave = null;
            Libro bookToSave = null;
            //hay libros que no tienen autor
            //Por conveniencia solo se guardara el primer libro que tenga autor
            DatosLibro libro = getFirstWithAuthor(libros.libros());

            Optional<Libro> libroBuscado = libroRepository.findByTituloContainsIgnoreCase(libro.titulo());
            if (libroBuscado.isPresent()) {
                System.out.println("El libro ya se encuentra registrado");
            } else {
                //Guardar en la base de datos
                Optional<Autor> autorBuscado = autorRepository.findByNombre(libro.autor().get(0).nombre());
                if (autorBuscado.isPresent()) {
                    authorToSave = autorBuscado.get();
                } else {
                    authorToSave = new Autor(libro.autor().get(0).nombre(),
                            libro.autor().get(0).nacimiento(), libro.autor().get(0).muerte());
                    autorRepository.save(authorToSave);
                }
                bookToSave = new Libro(libro.titulo(), authorToSave,
                        libro.idioma().get(0), libro.numeroDeDescargas());

                authorToSave.setLibros(bookToSave);
                libroRepository.save(bookToSave);
                System.out.println(bookToSave.toString());
            }
        }else{
            System.out.println("No se encontraron resultados");
        }


    }
    //Listar todos los libros registrados
    private void getAllBooks() {
        // findAll() retorna una lista de libros o
        //retorna un lista vacia si no encuentra nada
        bookSearched = libroRepository.findAll();
        if(bookSearched.isEmpty()){
            System.out.print("No se encontraron libros registrados ");
        }
        bookSearched.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(libro -> {
                    System.out.println(libro.toString());
        });
    }


    private void getAuthors() {
        authorsSearched = autorRepository.findAll();
        authorsSearched.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(autor -> {
                    System.out.println(autor.toString() /*+ autor.getLibros()*/);
                    //System.out.println("Libros: ");
                    System.out.println(autor.getLibros());
                });
    }

    //autores vivos en un año determinado
    private void getAuthorsAliveInYear() {
        System.out.println("ingrese año: ");
        //TODO: Validar que el año sea un numero
        var year = keyBoard.nextInt();
        keyBoard.nextLine();
        List<Autor> autoresVivos = autorRepository.getAliveAuthors(year);
        if(autoresVivos.isEmpty()){
            System.out.println("No hay autores vivos en el año: " + year);
        }else{
            autoresVivos.stream()
                    .forEach(autor -> {
                        System.out.println(autor.toString());
                    });
        }

    }

    private void getBooksByLanguage() {
        System.out.println("Introduce el idioma: ");
        String msjIdioma = """
                Idiomas disponibles:
                -en (Inglés)
                -es (Español)
                -fr (Francés)
                -de (Alemán)
                -it (Italiano)
                -pt (Portugués)
                -ja (Japonés)
                """;
        System.out.println(msjIdioma);
        var language = keyBoard.nextLine();
        List<Libro> librosPorIdioma = libroRepository.findByIdiomaContainsIgnoreCase(language);
        if(librosPorIdioma.isEmpty()){
            System.out.println("No se encontraron libros en el idioma: " + language);
        }else{
            librosPorIdioma.stream()
                    .forEach(libro -> {
                        System.out.println(libro.toString());
                    });
        }
    }

}
