package com.nextgen.sgp.controller;

import com.nextgen.sgp.domain.dto.Marca;
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
    public ResponseEntity<?> salvar(@RequestBody Marca marca) {
        try {
            return ResponseEntity.ok(marcaService.salvar(marca));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(MarcaController.class, "Erro ao salvar a marca: " + marca.getNome() + "!", "salvar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a marca: " + marca.getNome() + "!");
        }
    }

    /*
    @PostMapping("/{codigoMarca}")
    public ResponseEntity<?> salvar(@RequestBody Marca marca) {
        try {
            return ResponseEntity.ok(marcaService.salvar(marca));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(MarcaController.class, "Erro ao salvar a marca: " + marca.getNome() + "!", "salvar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a marca: " + marca.getNome() + "!");
        }
    }
    */

    /*
    @DeleteMapping("/{codigoMarca}")
    public ResponseEntity<?> excluir(@PathVariable Long codigoMarca) {
        try {
            return ResponseEntity.ok(marcaService.excluir(codigoMarca));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(MarcaController.class, "Erro ao salvar a marca: " + marca.getNome() + "!", "salvar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a marca: " + marca.getNome() + "!");
        }
    }
    */

}
