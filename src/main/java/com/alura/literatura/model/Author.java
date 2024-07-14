package com.alura.literatura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
    name = "authors",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_name_birth_death",
            columnNames = {"name", "birth_year", "death_year"}))
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Integer birthYear;
  private Integer deathYear;

  @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
  private Set<Book> books = new HashSet<>();

  public Author() {}

  public Author(Long id, String name, Integer birthYear, Integer deathYear) {
    this.id = id;
    this.name = name;
    this.birthYear = birthYear;
    this.deathYear = deathYear;
  }

  public Author(AuthorData authorData) {
    List<String> nameAux = Arrays.asList(authorData.name().split(", "));
    this.name = nameAux.get(1) + " " + nameAux.get(0);
    this.birthYear = authorData.birthYear();
    this.deathYear = authorData.deathYear();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public Integer getDeathYear() {
    return deathYear;
  }

  public void setDeathYear(Integer deathYear) {
    this.deathYear = deathYear;
  }

  public Set<Book> getBooks() {
    return books;
  }

  public void setBooks(Set<Book> books) {
    books.forEach(book -> book.getAuthors().add(this));
    this.books = books;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((birthYear == null) ? 0 : birthYear.hashCode());
    result = prime * result + ((deathYear == null) ? 0 : deathYear.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Author other = (Author) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (birthYear == null) {
      if (other.birthYear != null) return false;
    } else if (!birthYear.equals(other.birthYear)) return false;
    if (deathYear == null) {
      if (other.deathYear != null) return false;
    } else if (!deathYear.equals(other.deathYear)) return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder stringBooks = new StringBuilder();
    this.books.forEach(book -> stringBooks.append(book.getTitle() + ", "));
    return "\nAutor: "
      + this.name
      + "\nAño de nacimiento: "
      + this.birthYear
      + "\nAño de fallecimiento: "
      + this.deathYear
      + "\nLibros: "
      + "[" + stringBooks.toString().substring(0, stringBooks.toString().length() - 2) + "]";
  }
}
