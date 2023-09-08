package com.example.algafoodapi.infrastructure.repository;

import com.example.algafoodapi.domain.model.Restaurante;
import com.example.algafoodapi.domain.repository.RestauranteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository  {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> listar(){
        return manager.createQuery("SELECT c FROM Restaurante c", Restaurante.class)
                .getResultList();
    }

    @Override
    public Restaurante buscar(Long id){
        return manager.find(Restaurante.class, id);
    }

    @Transactional
    @Override
    public  Restaurante salvar(Restaurante restaurante){
        return manager.merge(restaurante);
    }


    @Transactional
    @Override
    public void remover(Restaurante restaurante){
        restaurante = buscar(restaurante.getId());
        manager.remove(restaurante);
    }
}