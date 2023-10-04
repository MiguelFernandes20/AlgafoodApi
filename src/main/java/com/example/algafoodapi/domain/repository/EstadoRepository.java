package com.example.algafoodapi.domain.repository;

import com.example.algafoodapi.domain.model.Cidade;
import com.example.algafoodapi.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstadoRepository extends JpaRepository<Estado, Long>
    {

    }
