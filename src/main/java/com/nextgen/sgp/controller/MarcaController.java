package com.nextgen.sgp.controller;

import com.nextgen.sgp.domain.cadastro.Marca;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.service.MarcaService;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping()
    public ResponseEntity<?> buscar(@RequestParam (required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Boolean indicadorAtivo) {
        try {
            return ResponseEntity.ok(marcaService.buscar(codigo, nome, indicadorAtivo));
        } catch (Exception e) {
            loggerUtil.error("Erro ao buscar as marcas!", "buscar", e, MarcaController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar as marcas! Contacte o suporte.");
        }
    }

    @PostMapping()
    public ResponseEntity<?> cadastrar(@RequestBody Marca marca) {
        try {
            return ResponseEntity.ok(marcaService.cadastrar(marca));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao cadastrar a marca: \{marca.getNome()}!", "cadastrar", e, MarcaController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao cadastrar a marca: \{marca.getNome()}! Contacte o suporte.");
        }
    }

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestBody Marca marca) {
        try {
            return ResponseEntity.ok(marcaService.atualizar(marca));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao atualizar a marca: \{marca.getNome()}!", "atualizar", e, MarcaController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao atualizar a marca: \{marca.getCodigo()}! Contacte o suporte.");
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> inativar(@PathVariable Long codigo) {
        try {
            marcaService.inativar(codigo);
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao inativar a marca: \{codigo}!", "inativar", e, MarcaController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao inativar a marca: \{codigo}! Contacte o suporte.");
        }
    }

}
