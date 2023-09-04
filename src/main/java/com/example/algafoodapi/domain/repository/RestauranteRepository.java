package com.example.algafoodapi.domain.repository;

import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {
    List<Restaurante> listar();
    Restaurante buscar(Long id);
    Restaurante salvar(Restaurante restaurante);
    void remover(Restaurante restaurante);

}

