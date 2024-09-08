package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.Produto;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface ProdutoService {

    List<Produto> buscar(Long codigo, String nome, Integer codigoTipoProduto, Integer codigoStatusProduto, Boolean indicadorSemEstoque) throws InternalServerErrorException;

}
