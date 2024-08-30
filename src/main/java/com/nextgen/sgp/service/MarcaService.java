package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.dto.Marca;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface MarcaService {

    List<Marca> buscar(Long codigo, String nome, Boolean indicadorAtivo) throws InternalServerErrorException;

    Marca salvar(Marca marca) throws BadRequestException, InternalServerErrorException;

}
