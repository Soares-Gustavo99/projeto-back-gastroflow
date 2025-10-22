package com.fiec.projeto_back_gastroflow.features.products.controllers;


import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import com.fiec.projeto_back_gastroflow.features.products.services.impl.ProdutoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/produtos")
public class ProdutoController {

    private final ProdutoServiceImpl produtoService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void createProduto(@RequestBody ProdutoDTO produtoDTO){

        produtoService.createProduto(produtoDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public ProdutoDTO getById(@RequestParam Long id){
        return produtoService.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "nome", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public List<ProdutoDTO> getAllByNome(@RequestParam String nome){
        return produtoService.getAllByNome(nome);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "produtos", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public List<ProdutoDTO> findAll(){
        return produtoService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void updateProdutoById(@RequestParam Long id, @RequestBody ProdutoDTO produtoDTO){
        produtoService.updateProdutoById(id, produtoDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping()
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void deleteProdutoById(@RequestParam Long id){
        produtoService.deleteProdutoById(id);
    }
}

