package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.cadastro.Marca;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface MarcaService {

    List<Marca> buscar(Long codigo, String nome, Boolean indicadorAtivo) throws InternalServerErrorException;

    Marca cadastrar(Marca marca) throws BadRequestException, InternalServerErrorException;

    Marca atualizar(Marca marca) throws BadRequestException, InternalServerErrorException;

    void inativar(Long codigo) throws BadRequestException, InternalServerErrorException;

    Marca buscarAtiva(Long codigo) throws BadRequestException, InternalServerErrorException;

}
