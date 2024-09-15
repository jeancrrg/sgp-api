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

    @GetMapping("")
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Integer codigoTipoProduto,
                                    @RequestParam (required = false) Integer codigoStatusProduto,
                                    @RequestParam (required = false) Boolean indicadorSemEstoque) {
        try {
            return ResponseEntity.ok(produtoService.buscar(codigo, nome, codigoTipoProduto, codigoStatusProduto, indicadorSemEstoque));
        } catch (Exception e) {
            loggerUtil.error(ProdutoController.class, "Erro ao buscar os produtos!", "buscar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar os produtos!");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {
        try {
            return ResponseEntity.ok(produtoService.cadastrar(produto));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(ProdutoController.class, "Erro ao cadastrar o produto: " + produto.getNome() + "!", "cadastrar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o produto: " + produto.getNome() + "!");
        }
    }

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestBody Produto produto) {
        try {
            return ResponseEntity.ok(produtoService.atualizar(produto));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(ProdutoController.class, "Erro ao atualizar o produto: " + produto.getCodigo() + "!", "atualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o produto: " + produto.getCodigo() + "!");
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
            loggerUtil.error(ProdutoController.class, "Erro ao inativar o produto: " + codigo + "!", "inativar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao inativar o produto: " + codigo + "!");
        }
    }

}
