package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.cadastro.TipoProduto;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface TipoProdutoService {

    List<TipoProduto> buscar(Long codigo, String descricao) throws InternalServerErrorException;

}
