package com.fiec.projeto_back_gastroflow.features.products.controllers;


import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import com.fiec.projeto_back_gastroflow.features.products.services.impl.ProdutoServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "produto")
@Slf4j
public class ProdutoController {


    private final ProdutoServiceImpl produtoService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createProduto(@RequestBody ProdutoDTO produtoDTO, @AuthenticationPrincipal User userDetails){

        produtoService.createProduto(produtoDTO, userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ProdutoDTO getById(@RequestParam Long id){
        return produtoService.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "nome", produces = APPLICATION_JSON_VALUE)
    public List<ProdutoDTO> getAllByNome(@RequestParam String nome, @AuthenticationPrincipal UserDetails userDetails){
        return produtoService.getAllByNome(nome);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "produtos", produces = APPLICATION_JSON_VALUE)
    public List<ProdutoDTO> findAll(){
        return produtoService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    public void updateProdutoById(@RequestParam Long id, @RequestBody ProdutoDTO produtoDTO){
        produtoService.updateProdutoById(id, produtoDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping()
    public void deleteClienteById(@RequestParam Long id){
        produtoService.deleteProdutoById(id);
    }
}

