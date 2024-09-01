package com.nextgen.sgp.controller;

import com.nextgen.sgp.domain.Departamento;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.service.DepartamentoService;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping("")
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Boolean indicadorAtivo) {
        try {
            return ResponseEntity.ok(departamentoService.buscar(codigo, nome, indicadorAtivo));
        } catch (Exception e) {
            loggerUtil.error(DepartamentoController.class, "Erro ao buscar os departamentos!", "buscar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar os departamentos!");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> salvar(@RequestBody Departamento departamento) {
        try {
            return ResponseEntity.ok(departamentoService.salvar(departamento));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(DepartamentoController.class, "Erro ao salvar o departamento: " + departamento.getNome() + "!", "salvar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o departamento: " + departamento.getNome() + "!");
        }
    }

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestBody Departamento departamento) {
        try {
            return ResponseEntity.ok(departamentoService.atualizar(departamento));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(DepartamentoController.class, "Erro ao atualizar o departamento: " + departamento.getNome() + "!", "atualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o departamento: " + departamento.getNome() + "!");
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> inativar(@PathVariable Long codigo) {
        try {
            departamentoService.inativar(codigo);
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(DepartamentoController.class, "Erro ao inativar o departamento: " + codigo + "!", "inativar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao inativar o departamento: " + codigo + "!");
        }
    }

}
