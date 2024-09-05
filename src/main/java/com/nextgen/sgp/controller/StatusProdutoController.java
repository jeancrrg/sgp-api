package com.nextgen.sgp.controller;

import com.nextgen.sgp.service.StatusProdutoService;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/status-produto")
public class StatusProdutoController {

    @Autowired
    private StatusProdutoService statusProdutoService;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping("")
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String descricao) {
        try {
            return ResponseEntity.ok(statusProdutoService.buscar(codigo, descricao));
        } catch (Exception e) {
            loggerUtil.error(StatusProdutoController.class, "Erro ao buscar os status dos produtos!", "buscar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar os status dos produtos!");
        }
    }

}
