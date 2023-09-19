package com.example.algafoodapi.api.controller;

import com.example.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Restaurante;
import com.example.algafoodapi.domain.repository.RestauranteRepository;
import com.example.algafoodapi.domain.service.CadastroCozinhaService;
import com.example.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController
    {
        @Autowired
        private RestauranteRepository restauranteRepository;

        @Autowired
        private CadastroRestauranteService cadastroRestauranteService;

        @GetMapping
        public List<Restaurante> listar()
            {
            return restauranteRepository.listar();
            }
        @GetMapping("/{restauranteId}")
        public ResponseEntity<Restaurante> buscar (@PathVariable Long restauranteId)
            {
            Restaurante restaurante = restauranteRepository.buscar(restauranteId);
            if (restaurante != null)
                {
                return  ResponseEntity.status(HttpStatus.OK).body(restaurante);
                }
            return ResponseEntity.status(HttpStatus.FOUND).build();
            }
        @PostMapping

         public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante)
            {
                try
                    {
                    restaurante =  cadastroRestauranteService.salvar(restaurante);
                    return  ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
                    }catch (EntidadeNaoEncontradaException e)
                    {
                        return  ResponseEntity.badRequest().body(e.getMessage());
                    }
            }
        @PutMapping("/{restauranteId}")
        public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante)
            {
            Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);

            if (restauranteAtual != null)
                {
                BeanUtils.copyProperties(restaurante, restauranteAtual , "id");

                restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);
                return ResponseEntity.ok(restauranteAtual);
                }

            return ResponseEntity.notFound().build();
            }




    }
