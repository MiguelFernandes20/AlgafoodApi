package com.example.algafoodapi.domain.service;

import com.example.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.example.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Restaurante;
import com.example.algafoodapi.domain.repository.CozinhaRepository;
import com.example.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestauranteService
    {
        @Autowired
        private RestauranteRepository restauranteRepository;

        @Autowired
        private CozinhaRepository cozinhaRepository;

        public Restaurante salvar(Restaurante restaurante)
            {
                Long cozinhaId = restaurante.getCozinha().getId();
                Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

                if (cozinha.isEmpty())
                    {
                        throw  new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com o código %d", cozinhaId));
                    }
                restaurante.setCozinha(cozinha.get());
                return restauranteRepository.save(restaurante);
            }

        public void excluir (Long restauranteId)
        {
            try
            {
                restauranteRepository.deleteById(restauranteId);

            } catch (EmptyResultDataAccessException e)
            {
                throw new EntidadeNaoEncontradaException(
                        String.format("Não existe um cadastro de restaurante com código %d", restauranteId));

            } catch (DataIntegrityViolationException e)
            {
                throw new EntidadeEmUsoException(
                        String.format("Cidade de código %d não pode ser removido, pois está em uso", restauranteId));
            }
        }
    }
