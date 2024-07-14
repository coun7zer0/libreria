package com.alura.literatura.repository;

import java.util.Optional;

import com.alura.literatura.model.Language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long>{
  Optional<Language> findByName(String name);
}