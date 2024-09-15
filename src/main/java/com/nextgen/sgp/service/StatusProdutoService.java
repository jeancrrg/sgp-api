package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.cadastro.StatusProduto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface StatusProdutoService {

    List<StatusProduto> buscar(Long codigo, String descricao) throws InternalServerErrorException;

    StatusProduto findByDescricao(String descricao) throws BadRequestException, InternalServerErrorException;

}
