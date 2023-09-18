package com.example.algafoodapi.infrastructure.repository;

import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.FormaPagamento;
import com.example.algafoodapi.domain.repository.FormaPagamentoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FormaPagamentoImpl implements FormaPagamentoRepository
    {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<FormaPagamento> listar()
        {
        return manager.createQuery("SELECT c FROM FormaPagamento c", FormaPagamento.class)
                .getResultList();
        }
    @Override
    public FormaPagamento buscar(Long id)
        {
        return manager.find(FormaPagamento.class, id);
        }
    @Transactional
    @Override
    public  FormaPagamento salvar(FormaPagamento formaPagamento)
        {
        return manager.merge(formaPagamento);
        }
    @Transactional
    @Override
    public void remover(FormaPagamento formaPagamento)
        {
        formaPagamento = buscar(formaPagamento.getId());
        manager.remove(formaPagamento);
        }
    }
