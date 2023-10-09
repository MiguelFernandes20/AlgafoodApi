package com.example.algafoodapi.api.controller;

import com.example.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.example.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Estado;
import com.example.algafoodapi.domain.model.Restaurante;
import com.example.algafoodapi.domain.repository.EstadoRepository;
import com.example.algafoodapi.domain.service.CadastroCozinhaService;
import com.example.algafoodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController
    {
    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoRepository estadoRepository;


    @GetMapping
    public List<Estado> listar()
        {
        return estadoRepository.findAll();
        }
    @GetMapping("/{estadoId}")
        public ResponseEntity<Estado> buscar(@PathVariable Long estadoId)
        {
            Optional <Estado> estado = estadoRepository.findById(estadoId);

            if (estado.isPresent())
                {
                return ResponseEntity.ok(estado.get());
                }
            return ResponseEntity.notFound().build();
        }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
        public Estado adicionar(@RequestBody Estado estado)
        {
            return cadastroEstadoService.salvar(estado);
        }

        @PutMapping("/{estadoId}")
        public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId,
                                                @RequestBody Estado estado) {
            Optional<Estado> estadoAtualOptional = estadoRepository.findById(estadoId);

            if (estadoAtualOptional.isPresent()) {
                Estado estadoAtual = estadoAtualOptional.get();

                BeanUtils.copyProperties(estado, estadoAtual, "id");

                Estado estadoSalvo = cadastroEstadoService.salvar(estadoAtual);
                return ResponseEntity.ok(estadoSalvo);
            }

            return ResponseEntity.notFound().build();
        }

        @DeleteMapping("/{estadoId}")
        public ResponseEntity<?> remover(@PathVariable Long estadoId) {
            try {
                cadastroEstadoService.excluir(estadoId);
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
