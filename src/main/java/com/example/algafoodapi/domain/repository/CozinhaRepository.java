package com.example.algafoodapi.domain.repository;

import com.example.algafoodapi.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>
    {
        List<Cozinha> findTodasByNomeContaining (String nome);
    }
