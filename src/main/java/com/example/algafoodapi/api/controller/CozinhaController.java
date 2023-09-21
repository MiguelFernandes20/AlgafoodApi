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
        return cozinhaRepository.listar();
        }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId)
        {
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

            if (cozinha!= null)
            {
                return ResponseEntity.ok(cozinha);
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
            Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);

            if (cozinhaAtual != null)
                {
                BeanUtils.copyProperties(cozinha, cozinhaAtual , "id");

               cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
                return ResponseEntity.ok(cozinhaAtual);
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
