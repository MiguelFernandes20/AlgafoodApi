package com.example.algafoodapi.domain.repository;

import com.example.algafoodapi.domain.model.Cidade;
import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>
    {

    }
