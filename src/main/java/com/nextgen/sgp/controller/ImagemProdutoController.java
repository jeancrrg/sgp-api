package com.nextgen.sgp.controller;

import com.nextgen.sgp.domain.cadastro.ImagemProduto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.service.ImagemProdutoService;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/imagens-produto")
public class ImagemProdutoController {

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private ImagemProdutoService imagemProdutoService;

    @GetMapping("")
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Long codigoProduto) {
        try {
            return ResponseEntity.ok(imagemProdutoService.buscar(codigo, nome, codigoProduto));
        } catch (Exception e) {
            loggerUtil.error(ImagemProdutoController.class, "Erro ao buscar as imagens do produto!", "buscar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar as imagens do produto!");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> cadastrar(@RequestBody List<ImagemProduto> listaImagensProduto) {
        try {
            return ResponseEntity.ok(imagemProdutoService.cadastrar(listaImagensProduto));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error(ImagemProdutoController.class, "Erro ao cadastrar as imagens do produto!", "cadastrar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar as imagens do produto! Contate o suporte.");
        }
    }

}
