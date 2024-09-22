package com.nextgen.sgp.controller;

import com.nextgen.sgp.domain.cadastro.Categoria;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.service.CategoriaService;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping()
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Boolean indicadorAtivo,
                                    @RequestParam (required = false) Long codigoDepartamento) {
        try {
            return ResponseEntity.ok(categoriaService.buscar(codigo, nome, indicadorAtivo, codigoDepartamento));
        } catch (Exception e) {
            loggerUtil.error("Erro ao buscar as categorias!", "buscar", e, CategoriaController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar as categorias! Contacte o suporte.");
        }
    }

    @PostMapping()
    public ResponseEntity<?> cadastrar(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok(categoriaService.cadastrar(categoria));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao cadastrar a categoria: \{categoria.getNome()}!", "cadastrar", e, CategoriaController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao cadastrar a categoria: \{categoria.getNome()}! Contacte o suporte.");
        }
    }

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok(categoriaService.atualizar(categoria));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao atualizar a categoria: \{categoria.getNome()}!", "atualizar", e, CategoriaController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao atualizar a categoria: \{categoria.getCodigo()}! Contacte o suporte.");
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> inativar(@PathVariable Long codigo) {
        try {
            categoriaService.inativar(codigo);
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao inativar a categoria: \{codigo}!", "inativar", e, CategoriaController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao inativar a categoria: \{codigo}! Contacte o suporte.");
        }
    }

}
