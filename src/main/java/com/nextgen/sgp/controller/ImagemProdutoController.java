package com.nextgen.sgp.controller;

import com.nextgen.sgp.domain.cadastro.ImagemProduto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.ConverterException;
import com.nextgen.sgp.exception.ArquivoAmazonException;
import com.nextgen.sgp.service.ImagemProdutoService;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    @GetMapping()
    public ResponseEntity<?> buscar(@RequestParam(required = false) Long codigo,
                                    @RequestParam (required = false) String nome,
                                    @RequestParam (required = false) Long codigoProduto) {
        try {
            return ResponseEntity.ok(imagemProdutoService.buscar(codigo, nome, codigoProduto));
        } catch (ConverterException e) {
            loggerUtil.error("Erro ao converter ao converter o arquivo na busca de imagens do produto!", "buscar", e, ImagemProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao converter ao converter o arquivo na busca de imagens do produto! Contacte o suporte.");
        } catch (ArquivoAmazonException e) {
            loggerUtil.error("Erro ao buscar as imagens do produto na amazon!", "buscar", e, ImagemProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar as imagens do produto no repositório de imagens! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error("Erro ao buscar as imagens do produto!", "buscar", e,  ImagemProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar as imagens do produto! Contacte o suporte.");
        }
    }

    @PostMapping()
    public ResponseEntity<?> cadastrar(@RequestBody List<ImagemProduto> listaImagensProduto) {
        try {
            return ResponseEntity.ok(imagemProdutoService.cadastrar(listaImagensProduto));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            loggerUtil.error("Erro ao cadastrar as imagens do produto!", "cadastrar", e, ImagemProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar as imagens do produto! Contacte o suporte.");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> baixarImagem(@RequestParam Long codigoProduto, @RequestParam String nomeImagemServidor) {
        try {
            byte[] imagem = imagemProdutoService.baixarImagem(codigoProduto, nomeImagemServidor);
            if (imagem == null || imagem.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(STR."Imagem: \{nomeImagemServidor} não encontrada para o produto: \{codigoProduto}");
            }
            HttpHeaders headers = imagemProdutoService.configurarHeaderRetornoImagem(nomeImagemServidor);
            return ResponseEntity.ok().headers(headers).body(imagem);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ArquivoAmazonException e) {
            loggerUtil.error(STR."Erro ao realizar o download da imagem: \{nomeImagemServidor} do produto: \{codigoProduto} na amazon!", "baixarImagem", e, ImagemProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao realizar o download da imagem: \{nomeImagemServidor} do produto: \{codigoProduto} no repositório de imagens! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao realizar o download da imagem: \{nomeImagemServidor} do produto: \{codigoProduto}!", "baixarImagem", e, ImagemProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao realizar o download da imagem: \{nomeImagemServidor} do produto: \{codigoProduto}! Contacte o suporte.");
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> excluirImagem(@RequestParam Long codigoProduto, @RequestParam Long codigoImagem, @RequestParam String nomeImagemServidor) {
        try {
            imagemProdutoService.excluirImagem(codigoProduto, codigoImagem, nomeImagemServidor);
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ArquivoAmazonException e) {
            loggerUtil.error(STR."Erro ao excluir a imagem: \{nomeImagemServidor} do produto: \{codigoProduto} na amazon!", "excluirImagem", e, ImagemProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao excluir a imagem: \{nomeImagemServidor} do produto: \{codigoProduto} no repositório de imagens! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error(STR."Erro ao excluir a imagem: \{nomeImagemServidor} do produto: \{codigoProduto}!", "excluirImagem", e, ImagemProdutoController.class);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Erro ao excluir a imagem: \{nomeImagemServidor} do produto: \{codigoProduto}! Contacte o suporte.");
        }
    }
    
}
