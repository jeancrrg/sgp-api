package com.nextgen.sgp.controller;

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

}
