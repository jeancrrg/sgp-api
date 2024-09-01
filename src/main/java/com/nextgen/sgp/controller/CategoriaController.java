package com.nextgen.sgp.controller;

import com.nextgen.sgp.domain.Categoria;
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

    @GetMapping("")
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Boolean indicadorAtivo,
                                    @RequestParam (required = false) Long codigocategoria) {
        try {
            return ResponseEntity.ok(categoriaService.buscar(codigo, nome, indicadorAtivo, codigocategoria));
        } catch (Exception e) {
            loggerUtil.error(CategoriaController.class, "Erro ao buscar as categorias!", "buscar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar as categorias!");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> salvar(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok(categoriaService.salvar(categoria));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(CategoriaController.class, "Erro ao salvar a categoria: " + categoria.getNome() + "!", "salvar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a categoria: " + categoria.getNome() + "!");
        }
    }

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok(categoriaService.atualizar(categoria));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(CategoriaController.class, "Erro ao atualizar a categoria: " + categoria.getNome() + "!", "atualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a categoria: " + categoria.getNome() + "!");
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
            loggerUtil.error(CategoriaController.class, "Erro ao inativar a categoria: " + codigo + "!", "inativar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao inativar a categoria: " + codigo + "!");
        }
    }

}
