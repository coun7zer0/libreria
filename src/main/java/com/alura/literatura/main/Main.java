package com.alura.literatura.main;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.alura.literatura.model.BookData;
import com.alura.literatura.model.ListBookData;
import com.alura.literatura.service.ApiClient;
import com.alura.literatura.service.DataConverter;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ApiClient apiClient = new ApiClient();
    private DataConverter dataConverter = new DataConverter();
    private final String URL_BASE = "https://gutendex.com/books/";

    public void showMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu =
                "\nMENU" +
                "\n--------------------" +
                "\n1 - Buscar libro por título" +
                "\n2 - Listar libros registrados" +
                "\n3 - Listar autores registrados" +
                "\n4 - Listar autores vivos en un determinado año" +
                "\n5 - Listar libros por idioma" +
                "\n0 - Salir\n" +
                "--------------------" +
                "\nElije la opción a través de un número: ";
            System.out.print(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
            case 1:
                SearchBookWeb();
                break;

            case 0:
                System.out.println("\nCerrando la aplicación...\n");
                break;

            default:
                System.out.println("\nOpción inválida\n");
            }
        }
    }

    private BookData getBookData() {
        System.out.print("\nIngrese el nombre del libro que desea buscar: ");
        String bookName = scanner.nextLine().trim().replace(" ", "%20").toLowerCase();
        String json = apiClient.getData(URL_BASE + "?search=" + bookName);

        List<BookData> books = dataConverter.getData(json, ListBookData.class).books();
        if (books.size() == 0)
            return null;

        return books.get(0);
    }

    private void SearchBookWeb() {
        BookData data = getBookData();
        if (data == null) {
            System.out.println("\n¡¡Libro no encontrado!!");
            return;
        }

        System.out.println("\n" + data);
    }

}