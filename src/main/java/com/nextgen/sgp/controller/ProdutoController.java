package com.nextgen.sgp.controller;

import com.nextgen.sgp.domain.cadastro.Produto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.service.ProdutoService;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping()
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Integer codigoTipoProduto,
                                    @RequestParam (required = false) Integer codigoStatusProduto,
                                    @RequestParam (required = false) Boolean indicadorSemEstoque) {
        try {
            return ResponseEntity.ok(produtoService.buscar(codigo, nome, codigoTipoProduto, codigoStatusProduto, indicadorSemEstoque, null, null, null));
        } catch (Exception e) {
            loggerUtil.error("Erro ao buscar os produtos!", "buscar", e, ProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar os produtos! Contacte o suporte.");
        }
    }

    @PostMapping()
    public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {
        try {
            return ResponseEntity.ok(produtoService.cadastrar(produto));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao cadastrar o produto: \{produto.getNome()}!", "cadastrar", e, ProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao cadastrar o produto: \{produto.getNome()}! Contacte o suporte.");
        }
    }

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestBody Produto produto) {
        try {
            return ResponseEntity.ok(produtoService.atualizar(produto));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao atualizar o produto: \{produto.getCodigo()}!", "atualizar", e, ProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao atualizar o produto: \{produto.getCodigo()}! Contacte o suporte.");
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> inativar(@PathVariable Long codigo) {
        try {
            produtoService.inativar(codigo);
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao inativar o produto: \{codigo}!", "inativar", e, ProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao inativar o produto: \{codigo}! Contacte o suporte.");
        }
    }

}
