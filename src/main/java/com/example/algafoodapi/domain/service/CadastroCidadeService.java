package com.example.algafoodapi.domain.service;

import com.example.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.example.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.example.algafoodapi.domain.model.Cidade;
import com.example.algafoodapi.domain.model.Estado;
import com.example.algafoodapi.domain.repository.CidadeRepository;
import com.example.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService
    {

        @Autowired
        private CidadeRepository cidadeRepository;

        @Autowired
        EstadoRepository estadoRepository;

        public Cidade salvar (Cidade cidade)
        {
            Long estadoId = cidade.getEstado().getId();
            Estado estado = estadoRepository.buscar(estadoId);

            if (estado == null)
            {
                throw  new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de Estado com o código %d", estadoId));
            }
            cidade.setEstado(estado);
            return cidadeRepository.salvar(cidade);
        }

        public void excluir(Long cidadeId)
        {
            try
            {
                estadoRepository.remover(cidadeId);

            } catch (EmptyResultDataAccessException e)
            {
                throw new EntidadeNaoEncontradaException(
                        String.format("Não existe um cadastro de cidade com código %d", cidadeId));

            } catch (DataIntegrityViolationException e)
            {
                throw new EntidadeEmUsoException(
                        String.format("Cidade de código %d não pode ser removido, pois está em uso", cidadeId));
            }
        }
    }
