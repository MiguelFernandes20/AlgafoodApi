package com.example.algafoodapi.domain.repository;

import com.example.algafoodapi.domain.model.FormaPagamento;
import com.example.algafoodapi.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {
    List<Permissao> listar ();
    Permissao buscar(Long id);
    Permissao salvar (Permissao permissao);
    void remover(Permissao permissao);
}

