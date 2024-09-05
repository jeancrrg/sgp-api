package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.StatusProduto;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface StatusProdutoService {

    List<StatusProduto> buscar(Long codigo, String descricao) throws InternalServerErrorException;

}
