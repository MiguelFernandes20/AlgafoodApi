package com.example.algafoodapi.infrastructure.repository;

import com.example.algafoodapi.domain.model.FormaPagamento;
import com.example.algafoodapi.domain.model.Permissao;
import com.example.algafoodapi.domain.repository.PermissaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Permissao> listar(){
        return manager.createQuery("SELECT c FROM Permissao c", Permissao.class)
                .getResultList();
    }

    @Override
    public Permissao buscar(Long id){
        return manager.find(Permissao.class, id);
    }

    @Transactional
    @Override
    public  Permissao salvar(Permissao permissao){
        return manager.merge(permissao);
    }


    @Transactional
    @Override
    public void remover(Permissao permissao){
        permissao = buscar(permissao.getId());
        manager.remove(permissao);
    }

}
