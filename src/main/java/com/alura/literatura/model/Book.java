package com.alura.literatura.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NaturalId
  @Column(unique = true)
  private String title;

  private Integer downloadCount;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  Set<Author> authors = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  Set<Language> languages = new HashSet<>();

  public Book() {}

  public Book(Long id, String title, Integer downloadCount) {
    this.id = id;
    this.title = title;
    this.downloadCount = downloadCount;
  }

  public Book(BookData bookData) {
    this.title = bookData.title();
    this.downloadCount = bookData.downloadCount();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getDownloadCount() {
    return downloadCount;
  }

  public void setDownloadCount(Integer downloadCount) {
    this.downloadCount = downloadCount;
  }

  public Set<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(Set<Author> authors) {
    authors.forEach(author -> author.getBooks().add(this));
    this.authors = authors;
  }

  public void addAuthor(Author author) {
    authors.add(author);
    author.getBooks().add(this);
  }

  public void removeAuthor(Author author) {
    authors.remove(author);
    author.getBooks().remove(this);
  }

  public Set<Language> getLanguages() {
    return languages;
  }

  public void setLanguages(Set<Language> languages) {
    languages.forEach(language -> language.getBooks().add(this));
    this.languages = languages;
  }

  public void addLanguage(Language language) {
    languages.add(language);
    language.getBooks().add(this);
  }

  public void removeLanguage(Language language) {
    languages.remove(language);
    language.getBooks().remove(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Book other = (Book) obj;
    if (title == null) {
      if (other.title != null) return false;
    } else if (!title.equals(other.title)) return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder stringAuthors = new StringBuilder();
    this.authors.forEach(author -> stringAuthors.append(author.getName() + ", "));

    StringBuilder stringLanguages = new StringBuilder();
    this.languages.forEach(language -> stringLanguages.append(language.getName() + ", "));
    return "\n******LIBRO******"
        + "\nTitulo: "
        + this.title
        + "\nAuthores: "
        + stringAuthors.toString().substring(0, stringAuthors.toString().length() - 2)
        + "\nIdiomas: "
        + stringLanguages.toString().substring(0, stringLanguages.toString().length() - 2)
        + "\nNumero de descargas: "
        + this.downloadCount
        + "\n******++-++******";
  }
}
