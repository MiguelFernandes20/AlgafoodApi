package com.example.algafoodapi.domain.service;

import com.example.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Restaurante;
import com.example.algafoodapi.domain.repository.CozinhaRepository;
import com.example.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

                if (cozinha == null)
                    {
                        throw  new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com o código %d", cozinhaId));
                    }
                restaurante.setCozinha(cozinha);
                return restauranteRepository.salvar(restaurante);
            }
    }
