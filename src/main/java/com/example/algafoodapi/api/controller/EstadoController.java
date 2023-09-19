package com.example.algafoodapi.api.controller;

import com.example.algafoodapi.domain.model.Cozinha;
import com.example.algafoodapi.domain.model.Estado;
import com.example.algafoodapi.domain.repository.EstadoRepository;
import com.example.algafoodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController
    {
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
        CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Estado> listar()
        {
        return estadoRepository.listar();
        }
    @GetMapping("/{estadoId}")
        public ResponseEntity<Estado> buscar(@PathVariable Long estadoId)
        {
            Estado estado = estadoRepository.buscar(estadoId);

            if (estado != null)
            {
                return ResponseEntity.status(HttpStatus.OK).body(estado);
            }
            return ResponseEntity.status(HttpStatus.FOUND).build();

        }

    }
