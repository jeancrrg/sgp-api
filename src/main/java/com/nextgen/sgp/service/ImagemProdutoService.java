package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.cadastro.ImagemProduto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.ConverterException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.exception.UploadArquivoException;

import java.util.List;

public interface ImagemProdutoService {

    List<ImagemProduto> buscar(Long codigo, String nome, Long codigoProduto) throws ConverterException, UploadArquivoException, InternalServerErrorException;

    List<ImagemProduto> cadastrar(List<ImagemProduto> listaImagensProduto) throws BadRequestException, InternalServerErrorException;

}
