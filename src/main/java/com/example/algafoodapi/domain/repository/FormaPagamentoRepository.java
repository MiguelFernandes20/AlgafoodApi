package com.example.algafoodapi.domain.repository;

import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository
    {
    List<FormaPagamento> listar();
    FormaPagamento buscar(Long id);
    FormaPagamento salvar (FormaPagamento formaPagamento);
    void remover(FormaPagamento formaPagamento);
    }
