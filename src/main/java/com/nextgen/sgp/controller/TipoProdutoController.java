package com.nextgen.sgp.controller;

import com.nextgen.sgp.service.TipoProdutoService;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tipos-produto")
public class TipoProdutoController {

    @Autowired
    private TipoProdutoService tipoProdutoService;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping()
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String descricao) {
        try {
            return ResponseEntity.ok(tipoProdutoService.buscar(codigo, descricao));
        } catch (Exception e) {
            loggerUtil.error("Erro ao buscar os tipos de produto!", "buscar", e, TipoProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar os tipos de produto! Contacte o suporte!");
        }
    }

}
