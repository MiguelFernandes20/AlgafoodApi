package com.example.algafoodapi.api.controller;

import com.example.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.example.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Restaurante;
import com.example.algafoodapi.domain.repository.CozinhaRepository;
import com.example.algafoodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController
    {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Cozinha> listar()
        {
        return cozinhaRepository.findAll();
        }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId)
        {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

            if (cozinha.isPresent())
            {
                return ResponseEntity.ok(cozinha.get());
            }
            return ResponseEntity.notFound().build();

        }

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
    public Cozinha adiconar(@RequestBody Cozinha cozinha)
        {
        return cadastroCozinhaService.salvar(cozinha);
        }

@PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha)
        {
            Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);

            if (cozinhaAtual.isPresent())
                {
                BeanUtils.copyProperties(cozinha, cozinhaAtual.get() , "id");

               Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
                return ResponseEntity.ok(cozinhaSalva);
                }

            return ResponseEntity.notFound().build();
        }

        @DeleteMapping("/{cozinhaId}")
        public ResponseEntity<String> remover(@PathVariable Long cozinhaId) {
            try {
                cadastroCozinhaService.excluir(cozinhaId);
                return ResponseEntity.noContent().build();
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(e.getMessage());
            } catch (EntidadeEmUsoException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(e.getMessage());
            }
        }


    }
