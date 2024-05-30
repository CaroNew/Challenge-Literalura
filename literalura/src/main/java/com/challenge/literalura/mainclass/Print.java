package com.challenge.literalura.mainclass;

public class Print {

    public void menu() {
        String menu = """
                ******************************
                \tBienvenido a LiterAlura
       
                1. Buscar libro por titulo
                2. Listar libros registrados
                3. Listar autores registrados
                4. Listar autores vivos año
                5. Listar libros por idioma
                6. Salir
                
                ------------------------------
                7. Ver estadisticas
                8. Top 10 libros mas descargados
                9. Buscar autor por nombre
                ******************************
                Elige una opción:
                """;
        System.out.println(menu);
    }

    public void menuIdioma(){
        String msjIdioma = """
                ---------------------
                Idiomas disponibles:
                
                  -en  (Inglés)
                  -es  (Español)
                  -fr  (Francés)
                  -de  (Alemán)
                  -it  (Italiano)
                  -pt  (Portugués)
                  -ja  (Japonés)
                --------------------
                """;
        System.out.println(msjIdioma);
    }
}
