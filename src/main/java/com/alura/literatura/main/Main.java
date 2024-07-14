package com.alura.literatura.main;

import com.alura.literatura.model.Author;
import com.alura.literatura.model.Book;
import com.alura.literatura.model.BookData;
import com.alura.literatura.model.Language;
import com.alura.literatura.model.ListBookData;
import com.alura.literatura.repository.AuthorRepository;
import com.alura.literatura.repository.BookRepository;
import com.alura.literatura.repository.LanguageRepository;
import com.alura.literatura.service.ApiClient;
import com.alura.literatura.service.DataConverter;
import jakarta.transaction.Transactional;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
  private Scanner scanner = new Scanner(System.in);
  private ApiClient apiClient = new ApiClient();
  private DataConverter dataConverter = new DataConverter();
  private final String URL_BASE = "https://gutendex.com/books/";

  private BookRepository bookRepository;
  private AuthorRepository authorRepository;
  private LanguageRepository languageRepository;

  public Main(
      BookRepository bookRepository,
      AuthorRepository authorRepository,
      LanguageRepository languageRepository) {

    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.languageRepository = languageRepository;
  }

  public void showMenu() {
    int opcion = -1;
    while (opcion != 0) {
      String menu =
          "\nMENU"
              + "\n--------------------"
              + "\n1 - Buscar libro por título"
              + "\n2 - Listar libros registrados"
              + "\n3 - Listar autores registrados"
              + "\n4 - Listar autores vivos en un determinado año"
              + "\n5 - Listar libros por idioma"
              + "\n0 - Salir\n"
              + "--------------------"
              + "\nElije la opción a través de un número: ";
      System.out.print(menu);
      try {
        opcion = scanner.nextInt();
      } catch (InputMismatchException e) {
        opcion = -1;
      }
      scanner.nextLine(); // Limpia el buffer
      switch (opcion) {
        case 1:
          SearchBookWeb();
          break;

      case 2:
        findAllBooks();
        break;

      case 3:
        findAllAuthors();
        break;

      case 4:
        findAuthorsByYearAlive();
        break;

      case 0:
        System.out.println("\nCerrando la aplicación...\n");
        break;

      default:
        System.out.println("\nOpción inválida\n");
      }
    }
  }

  private BookData getBookData(String bookName) {
    bookName = bookName.replace(" ", "%20").toLowerCase();
    String json = apiClient.getData(URL_BASE + "?search=" + bookName);

    List<BookData> books = dataConverter.getData(json, ListBookData.class).books();
    if (books.size() == 0) return null;

    return books.get(0);
  }

  @Transactional
  public void SearchBookWeb() {
    System.out.print("\nIngrese el nombre del libro que desea buscar: ");
    String bookName = scanner.nextLine().trim();

    Optional<Book> optionalBook = bookRepository.findByTitleContaining(bookName);
    if (optionalBook.isPresent()) {
      System.out.println("\n" + optionalBook.get());
      return;
    }

    BookData data = getBookData(bookName);
    if (data == null) {
      System.out.println("\n¡¡Libro no encontrado!!");
      return;
    }

    Book book = new Book(data); // antes de realizar una relacion tengo que guardar las entidades o si no sale error al estar la misma en estado deteached
    bookRepository.save(book);

    Set<Author> authors =
      data.authors().stream()
      .map(author ->
           authorRepository
           .findByNameAndBirthAndDeath(
               author.name().split(", ")[1] + " " + author.name().split(", ")[0],
               author.birthYear(),
               author.deathYear())
           .orElseGet(() -> new Author(author)))
      .collect(Collectors.toSet());

    Set<Language> languages =
      data.languages().stream()
      .map(language ->
           languageRepository
           .findByName(language)
           .orElseGet(() -> new Language(language)))
      .collect(Collectors.toSet());

    book.setAuthors(authors);
    book.setLanguages(languages);
    bookRepository.save(book);
    System.out.println(book);
  }

  public void findAllBooks() {
    bookRepository.findAll().forEach(System.out::println);
  }

  public void findAllAuthors() {
    authorRepository.findAll().forEach(System.out::println);
  }

  public void findAuthorsByYearAlive() {
    Integer year = null;
    while (year == null) {
      System.out.println("\nIngrese el año en el que quiere buscar autores vivos: ");
      try {
        year = scanner.nextInt();
      } catch (Exception e) {
        System.out.println("\nAño invalido...");
        year = null;
      }
      scanner.nextLine();
    }
    System.out.println(year);
    authorRepository.findByYearAlive(year).forEach(System.out::println);
  }
}
