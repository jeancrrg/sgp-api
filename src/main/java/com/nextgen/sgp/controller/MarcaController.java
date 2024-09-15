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

    @GetMapping("")
    public ResponseEntity<?> buscar(@RequestParam (required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Boolean indicadorAtivo) {
        try {
            return ResponseEntity.ok(marcaService.buscar(codigo, nome, indicadorAtivo));
        } catch (Exception e) {
            loggerUtil.error(MarcaController.class, "Erro ao buscar as marcas!", "buscar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar as marcas!");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> cadastrar(@RequestBody Marca marca) {
        try {
            return ResponseEntity.ok(marcaService.cadastrar(marca));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(MarcaController.class, "Erro ao cadastrar a marca: " + marca.getNome() + "!", "cadastrar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar a marca: " + marca.getNome() + "!");
        }
    }

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestBody Marca marca) {
        try {
            return ResponseEntity.ok(marcaService.atualizar(marca));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(MarcaController.class, "Erro ao atualizar a marca: " + marca.getNome() + "!", "atualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a marca: " + marca.getNome() + "!");
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
            loggerUtil.error(MarcaController.class, "Erro ao inativar a marca: " + codigo + "!", "inativar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao inativar a marca: " + codigo + "!");
        }
    }

}
