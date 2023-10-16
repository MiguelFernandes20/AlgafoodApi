package com.example.algafoodapi.api.controller;

import com.example.algafoodapi.domain.exception.EntidadeEmUsoException;
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
import java.util.Optional;

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
            return restauranteRepository.findAll();
            }
        @GetMapping("/{restauranteId}")
        public ResponseEntity<Restaurante> buscar (@PathVariable Long restauranteId)
            {
            Optional <Restaurante> restaurante = restauranteRepository.findById(restauranteId);
            if (restaurante.isPresent())
                {
                return  ResponseEntity.ok(restaurante.get());
                }
            return ResponseEntity.notFound().build();
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
            Optional <Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

            if (restauranteAtual.isPresent())
                {
                BeanUtils.copyProperties(restaurante, restauranteAtual , "id");

                Restaurante restaurantesalvar = cadastroRestauranteService.salvar(restauranteAtual.get());
                return ResponseEntity.ok(restaurantesalvar);
                }

            return ResponseEntity.notFound().build();
            }

        @DeleteMapping("/{restauranteId}")
        public ResponseEntity<?> remover (@PathVariable Long restauranteId)
        {
            try {
                cadastroRestauranteService.excluir(restauranteId);
                return ResponseEntity.noContent().build();

            }catch (EntidadeNaoEncontradaException e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(e.getMessage());

            }catch (EntidadeEmUsoException e){
                return  ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(e.getMessage());
            }
        }




    }
