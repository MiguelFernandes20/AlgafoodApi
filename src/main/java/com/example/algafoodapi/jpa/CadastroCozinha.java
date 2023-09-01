package com.example.algafoodapi.jpa;

import com.example.algafoodapi.domain.model.Cozinha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar(){
        return manager.createQuery("SELECT c FROM Cozinha c", Cozinha.class)
                .getResultList();
    }
}
