package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.cadastro.ImagemProduto;
import com.nextgen.sgp.domain.dto.UploadArquivoDTO;
import com.nextgen.sgp.domain.enums.Status;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.exception.UploadArquivoException;
import com.nextgen.sgp.repository.ImagemProdutoRepository;
import com.nextgen.sgp.service.ImagemProdutoService;
import com.nextgen.sgp.service.UploadArquivoService;
import com.nextgen.sgp.util.FormatterUtil;
import com.nextgen.sgp.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemProdutoServiceImpl implements ImagemProdutoService {

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    @Autowired
    private UploadArquivoService uploadArquivoService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private FormatterUtil formatterUtil;

    private final String CAMINHO_DIRETORIO_IMAGEM = "imagens/produtos";

    public List<ImagemProduto> buscar(Long codigo, String nome, Long codigoProduto) throws InternalServerErrorException {
        try {
            if (nome != null && !nome.isEmpty()) {
                String nomeFormatado = formatterUtil.removerAcentos(nome);
                nome = "%" + nomeFormatado.toUpperCase().trim() + "%";
            }
            return imagemProdutoRepository.buscar(codigo, nome, codigoProduto);
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar as imagens do produto! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public List<ImagemProduto> cadastrar(List<ImagemProduto> listaImagensProduto) throws BadRequestException, InternalServerErrorException {
        validarListaImagensProduto(listaImagensProduto);
        List<ImagemProduto> listaImagensProdutoSalvas = new ArrayList<>();
        for (ImagemProduto imagemProduto : listaImagensProduto) {
            try {
                ImagemProduto imagemProdutoSalva = processarImagemProduto(imagemProduto);
                imagemProdutoSalva.setStatusCadastro(Status.SUCESSO);
                listaImagensProdutoSalvas.add(imagemProdutoSalva);
            } catch (BadRequestException e) {
                definirStatusFalhaImagemProduto(imagemProduto, STR."Falha antes de cadastrar a imagem do produto! - \{e.getMessage()}", null, "cadastrar", e);
                listaImagensProdutoSalvas.add(imagemProduto);
            } catch (UploadArquivoException e) {
                definirStatusFalhaImagemProduto(imagemProduto, "Erro ao realizar upload da imagem do produto! Contate o suporte!", STR."ERRO: Erro ao realizar upload da imagem: \{imagemProduto.getNome()} para o produto: \{imagemProduto.getCodigoProduto()}! - MENSAGEM DO ERRO: \{e.getMessage()}", "cadastrar", e);
                listaImagensProdutoSalvas.add(imagemProduto);
            } catch (Exception e) {
                definirStatusFalhaImagemProduto(imagemProduto, "Erro ao cadastrar a imagem do produto! Contate o suporte!", STR."ERRO: Erro ao cadastrar imagem: \{imagemProduto.getNome()} para o produto: \{imagemProduto.getCodigoProduto()}! - MENSAGEM DO ERRO: \{e.getMessage()}", "cadastrar", e);
                listaImagensProdutoSalvas.add(imagemProduto);
            }
        }
        return listaImagensProdutoSalvas;
    }

    public void validarListaImagensProduto(List<ImagemProduto> listaImagensProduto) throws BadRequestException {
        if (listaImagensProduto == null || listaImagensProduto.isEmpty()) {
            throw new BadRequestException("Nenhuma imagem de produto encontrada para cadastrar!");
        }
    }

    public ImagemProduto processarImagemProduto(ImagemProduto imagemProduto) throws BadRequestException, UploadArquivoException, InternalServerErrorException {
        validarCamposImagemProduto(imagemProduto);
        ImagemProduto imagemProdutoSalva = salvarImagemProduto(imagemProduto);
        if (imagemProdutoSalva == null) {
            throw new InternalServerErrorException("Imagem do produto não encontrada cadastro para atualizar o nome da imagem do servidor!");
        }
        String nomeImagemServidor = imagemProdutoSalva.getCodigo() + imagemProdutoSalva.getTipoExtensaoImagem();
        uploadArquivoService.realizarUpload(new UploadArquivoDTO(nomeImagemServidor, STR."\{CAMINHO_DIRETORIO_IMAGEM}/\{imagemProdutoSalva.getCodigoProduto()}/\{nomeImagemServidor}", imagemProdutoSalva.getArquivoBase64()));
        return atualizarNomeImagemServidor(imagemProdutoSalva, nomeImagemServidor);
    }

    public void validarCamposImagemProduto(ImagemProduto imagemProduto) throws BadRequestException {
        if (imagemProduto == null) {
            throw new BadRequestException("Imagem do produto não encontrada!");
        }
        if (imagemProduto.getNome() == null || imagemProduto.getNome().isEmpty()) {
            throw new BadRequestException("Nome da imagem não encontrado!");
        }
        if (imagemProduto.getTamanhoImagemBytes() == null) {
            throw new BadRequestException("Tamanho da imagem não encontrado!");
        }
        if (imagemProduto.getCodigoProduto() == null) {
            throw new BadRequestException("Código do produto da imagem não encontrado!");
        }
        if (imagemProduto.getTipoExtensaoImagem() == null || imagemProduto.getTipoExtensaoImagem().isEmpty()) {
            throw new BadRequestException("Tipo da extensão da imagem não encontrado!");
        }
        if (imagemProduto.getArquivoBase64() == null || imagemProduto.getArquivoBase64().isEmpty()) {
            throw new BadRequestException("Arquivo da imagem não encontrado!");
        }
    }

    public ImagemProduto salvarImagemProduto(ImagemProduto imagemProduto) throws InternalServerErrorException {
        try {
            imagemProduto.setDataUltimaAlteracao(LocalDateTime.now());
            return imagemProdutoRepository.save(imagemProduto);
        } catch (Exception e) {
            throw new InternalServerErrorException(STR."ERRO: Erro ao salvar a imagem do produto! - MENSAGEM DO ERRO: \{e.getMessage()}");
        }
    }

    public void definirStatusFalhaImagemProduto(ImagemProduto imagemProduto, String mensagemErroCadastro, String mensagemErroLog, String nomeMetodo, Exception exception) {
        imagemProduto.setStatusCadastro(Status.FALHA);
        imagemProduto.setMensagemErroCadastro(mensagemErroCadastro);
        if (mensagemErroLog != null && !mensagemErroLog.isEmpty()) {
            loggerUtil.error(ImagemProdutoServiceImpl.class, mensagemErroLog, nomeMetodo, exception);
        }
    }

    public ImagemProduto atualizarNomeImagemServidor(ImagemProduto imagemProduto, String nomeImagemServidor) throws InternalServerErrorException {
        try {
            imagemProduto.setNomeImagemServidor(nomeImagemServidor);
            return salvarImagemProduto(imagemProduto);
        } catch (Exception e) {
            throw new InternalServerErrorException(STR."ERRO: Erro ao atualizar o nome da imagem do servidor: \{nomeImagemServidor}! - MENSAGEM DO ERRO: \{e.getMessage()}");
        }
    }

}
