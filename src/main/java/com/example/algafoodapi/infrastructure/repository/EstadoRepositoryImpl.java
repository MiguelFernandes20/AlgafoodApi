package com.example.algafoodapi.infrastructure.repository;

import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Estado;
import com.example.algafoodapi.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository
    {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<Estado> listar()
        {
        return manager.createQuery("SELECT c FROM Estado c", Estado.class)
                .getResultList();
        }
    @Override
    public Estado buscar(Long id)
        {
        return manager.find(Estado.class, id);
        }
    @Transactional
    @Override
    public  Estado salvar(Estado estado)
        {
        return manager.merge(estado);
        }
    @Transactional
    @Override
    public void remover(Estado estado)
        {
        estado = buscar(estado.getId());
        manager.remove(estado);
        }
    }
