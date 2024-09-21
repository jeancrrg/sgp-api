package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.cadastro.Produto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface ProdutoService {

    List<Produto> buscar(Long codigo, String nome, Integer codigoTipoProduto, Integer codigoStatusProduto, Boolean indicadorSemEstoque, Long codigoMarca, Long codigoDepartamento, Long codigoCategoria) throws InternalServerErrorException;

    Produto cadastrar(Produto produto) throws BadRequestException, InternalServerErrorException;

    Produto atualizar(Produto produto) throws BadRequestException, InternalServerErrorException;

    void inativar(Long codigo) throws BadRequestException, InternalServerErrorException;

}
