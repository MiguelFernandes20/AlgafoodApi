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

import java.util.Optional;

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
            Estado estado = estadoRepository.findById(estadoId)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(
                            String.format("Não existe cadastro de estado com código %d", estadoId)));
            cidade.setEstado(estado);
            return cidadeRepository.save(cidade);
        }

        public void remover (Long cidadeId)
        {
            try
            {
                cidadeRepository.deleteById(cidadeId);

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
