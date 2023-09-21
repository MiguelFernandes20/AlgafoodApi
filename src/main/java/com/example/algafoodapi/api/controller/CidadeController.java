package com.example.algafoodapi.api.controller;

import com.example.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.example.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.example.algafoodapi.domain.model.Cidade;
import com.example.algafoodapi.domain.repository.CidadeRepository;
import com.example.algafoodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController
    {
        @Autowired
        private CidadeRepository cidadeRepository;

        @Autowired
        private CadastroCidadeService cadastroCidadeService;

        @GetMapping
        public List<Cidade> listar()
        {
            return cidadeRepository.listar();
        }

        @GetMapping("/{cidadesId}")
        public ResponseEntity<Cidade> buscar (@PathVariable Long cidadesId)
        {
            Cidade cidade = cidadeRepository.buscar(cidadesId);
            if (cidade != null)
            {
                return  ResponseEntity.status(HttpStatus.OK).body(cidade);
            }
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }

        @PostMapping
        public ResponseEntity<?> adicionar(@RequestBody Cidade cidade)
        {
            try {
                cidade = cadastroCidadeService.salvar(cidade);
                return  ResponseEntity.status(HttpStatus.CREATED).body(cidade);
            }catch (EntidadeNaoEncontradaException e)
            {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @PutMapping("/{cidadeId}")
        public ResponseEntity<Cidade> atualizar (@PathVariable Long cidadeId, @RequestBody Cidade cidade)
        {
            Cidade cidadeAtual = cidadeRepository.buscar(cidadeId);

            if (cidadeAtual != null)
            {
                BeanUtils.copyProperties(cidade, cidadeAtual , "id");

                cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);
                return  ResponseEntity.ok(cidadeAtual);
            }
            return  ResponseEntity.notFound().build();
        }

        @DeleteMapping("/{cidadeId}")
        public ResponseEntity<?> remover (@PathVariable Long cidadeId)
        {
            try {
                cadastroCidadeService.excluir(cidadeId);
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
