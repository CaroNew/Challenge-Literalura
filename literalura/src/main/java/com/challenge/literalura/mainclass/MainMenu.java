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
import java.util.stream.Collectors;

public class MainMenu {
    private Scanner keyBoard = new Scanner(System.in);
    private final String BASE_URL = "https://gutendex.com/books";
    private List<Libro> bookSearched = new ArrayList<>();
    private List<Autor> authorsSearched = new ArrayList<>();
    private Print print = new Print();

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
            print.menu();
            //Me aseguro que la opcion sea un numero
            option = getNumberFromUser();

            switch (option) {
                case 1:
                    searchABookByTitle();
                    break;
                case 2:
                    getAllBooks();
                    break;
                case 3:
                    getAllAuthors();
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
                case 7:
                    getStadistics();
                    break;
                case 8:
                    getTop10Books();
                    break;
                case 9:
                    getAuthorbyName();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option != 6);

    }

    //Valida que la opcion sea un numero
    public int getNumberFromUser() {
        int number = 0;
        while (true) {
            try {
                number = keyBoard.nextInt();
                keyBoard.nextLine();
                if(number > 0){
                    return number;
                }
                System.out.println("Por favor, introduce un número válido.");
            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduce un número válido.");
                keyBoard.nextLine(); // consume el input invalido
            }
        }
    }

    private String getStringFromUser(String message) {
        String data = "";
        while (true) {
            System.out.println(message);
            data = keyBoard.nextLine();
            if (!data.isEmpty()) {
                return data;
            }
        }

    }

    public String getWebData(String title) {
        ApiRequest request = new ApiRequest();
        var url = BASE_URL + "/?search=" + title.replace(" ", "+");
        return request.getData(url);
    }

    public DatosLibros jsonToDatosLibros(String data) {
        DataConversion dataConversion = new DataConversion();
        return dataConversion.convertData(data, DatosLibros.class);
    }

    public DatosLibro getFirstBookWithAuthor(List<DatosLibro> libros) {
        return libros.stream()
                .filter(libro -> !libro.autor().isEmpty())
                .findFirst()
                .orElse(null);
    }

    public Libro searchOrSaveBook(Autor author, DatosLibro libro) {
        Libro bookToSave = null;
        List <Libro> books = author.getLibros();

        Optional <Libro> bookFromAuthor = books.stream()
                .filter(libro1 -> libro1.getTitulo().equals(libro.titulo()))
                .findFirst();

        if (bookFromAuthor.isPresent()) {
            System.out.println("El libro ya registrado!");
            bookToSave = bookFromAuthor.get();
        } else {

            bookToSave = new Libro(libro.titulo(), author,
                    libro.idioma().get(0), libro.numeroDeDescargas());

            author.setLibros(bookToSave);
            libroRepository.save(bookToSave);

            System.out.println("Libro guardado!");
        }
        return bookToSave;
    }

    public Autor searchOrSaveAuthor(DatosLibro libro) {
        Optional<Autor> autorBuscado = autorRepository.findByNombre(libro.autor().get(0).nombre());
        Autor authorToSave = null;


        if (!autorBuscado.isPresent()) {
            authorToSave = new Autor(libro.autor().get(0).nombre(),
                    libro.autor().get(0).nacimiento(), libro.autor().get(0).muerte());
            autorRepository.save(authorToSave);
            System.out.println("Autor guardado!");
        } else {
            authorToSave = autorBuscado.get();
            System.out.println("Autor ya registrado!");
        }
        return authorToSave;

    }

    //Buscar un libro en la web y lo guarda en la base de datos
    //en caso de que no este registrado
    public void searchABookByTitle() {

        String message = "Introduce el titulo del libro a buscar: ";
        var title = getStringFromUser(message);

        String data = getWebData(title);
        DatosLibros libros = jsonToDatosLibros(data);

        if (!libros.libros().isEmpty()) {
            DatosLibro libro = getFirstBookWithAuthor(libros.libros());

            Autor author = searchOrSaveAuthor(libro);
            Libro book = searchOrSaveBook(author, libro);
            System.out.println(author);
            System.out.println(book);

        } else {
            System.out.println("No se encontraron resultados");
        }
    }

    private void getAllBooks() {
        // findAll() retorna una lista de libros o
        //retorna un lista vacia si no encuentra nada
        bookSearched = libroRepository.findAll();
        if (bookSearched.isEmpty()) {
            System.out.println("No se encontraron libros registrados ");
        }
        bookSearched.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(libro -> {
                    System.out.println(libro.toString());
                });
    }

    private void getAllAuthors() {
        authorsSearched = autorRepository.findAll();
        if (bookSearched.isEmpty()) {
            System.out.println("No se encontraron autores registrados");
        }
        authorsSearched.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(autor -> {
                    System.out.println(autor.toString() /*+ autor.getLibros()*/);
                    //System.out.println("Libros: ");
                    System.out.println(autor.getLibros());
                });
    }

    private void getAuthorsAliveInYear() {
        System.out.println("Ingrese año: ");

        var year = getNumberFromUser();
        List<Autor> autoresVivos = autorRepository.getAliveAuthors(year);
        if (autoresVivos.isEmpty()) {
            System.out.println("No hay autores vivos registrados del año: " + year);
        } else {
            autoresVivos.stream()
                    .forEach(autor -> {
                        System.out.println(autor.toString());
                    });
        }

    }

    private void getBooksByLanguage() {

        //Imprimir los idiomas disponibles
        print.menuIdioma();

        String message= "Introduce el idioma: ";
        String language = getStringFromUser(message);

        List<Libro> librosPorIdioma = libroRepository.findBookByLanguage(language);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + language);
        } else {
            librosPorIdioma.stream()
                    .forEach(libro -> {
                        System.out.println(libro.toString());
                    });
        }
    }

    //Ejercicios extra

    private void getStadistics() {
        List<Libro> booksDownloads = libroRepository.findAll();
        DoubleSummaryStatistics stats = booksDownloads.stream()
                .filter(libro -> libro.getNumeroDeDescargas() >= 0)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));

        String msj = """
                -------------------------
                Estadisticas de descargas:
                    Promedio total de libros: %.2f
                    Libro menos descargado: %.2f
                    Libro mas descargado: %.2f
                    Total libros: %d
                -------------------------
                """.formatted(stats.getAverage(), stats.getMin(), stats.getMax(), stats.getCount());
        System.out.println(msj);
    }

    private void getTop10Books() {
        System.out.println("Top 10 libros mas descargados:");
        List<String> top10Books = libroRepository.findTop10Books();
        if (top10Books.isEmpty()) {
            System.out.println("No se encontraron libros");
        } else {
            int i = 0;
            for (String libro : top10Books) {
                System.out.println(++i + ". " + libro);
            }
        }

    }

    private Autor selectAuthor(List<Autor> autores) {
        int i = 0;

        System.out.println("Autores encontrados: ");

        for (Autor autor : autores) {
            System.out.println(++i + ". \n" + autor.toString());
        }

        System.out.println("Seleccione un autor: ");
        int option = getNumberFromUser();

        return autores.get(option - 1);
    }


    private void getAuthorbyName() {
        String apellido = "";

        String message = "Introduce el nombre del autor: ";
        String data = getStringFromUser(message);

        String[] names = data.split(" ");
        apellido = names[names.length - 1];

        List<Autor> autores = autorRepository.findAutorByName(apellido);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores con el apellido: " + apellido);
        } else {
            Autor autor = autores.size() > 1 ? selectAuthor(autores) : autores.get(0);
            System.out.println(autor.toString());
        }

    }



}
