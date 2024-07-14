package com.alura.literatura;

import com.alura.literatura.main.Main;
import com.alura.literatura.repository.AuthorRepository;
import com.alura.literatura.repository.BookRepository;
import com.alura.literatura.repository.LanguageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private LanguageRepository languageRepository;

  public static void main(String[] args) {
    SpringApplication.run(LiteraturaApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Main main = new Main(bookRepository, authorRepository, languageRepository);
    main.showMenu();
  }
}
