package com.example.algafoodapi.domain.repository;

import com.example.algafoodapi.domain.model.Estado;

import java.util.List;

public interface EstadoRepository
    {

    List<Estado> listar ();

    Estado buscar (Long id);

    Estado salvar (Estado estado);

    void remover(Long id);

    }
