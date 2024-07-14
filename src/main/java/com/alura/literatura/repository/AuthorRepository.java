package com.alura.literatura.repository;

import java.util.Optional;

import com.alura.literatura.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

  @Query("SELECT a FROM Author a WHERE a.name = :name AND a.birthYear = :birthYear AND a.deathYear = :deathYear")
  Optional<Author> findByNameAndBirthAndDeath(String name, int birthYear, int deathYear);

  Optional<Author> findByName(String name);
}