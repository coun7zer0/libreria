package com.alura.literatura.repository;

import java.util.Optional;

import com.alura.literatura.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
  Optional<Book> findByTitleContaining(String title);
}