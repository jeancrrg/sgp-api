package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.cadastro.Categoria;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface CategoriaService {

    List<Categoria> buscar(Long codigo, String nome, Boolean indicadorAtivo, Long codigoDepartamento) throws InternalServerErrorException;

    Categoria cadastrar(Categoria categoria) throws BadRequestException, InternalServerErrorException;

    Categoria atualizar(Categoria categoria) throws BadRequestException, InternalServerErrorException;

    void inativar(Long codigo) throws BadRequestException, InternalServerErrorException;

    Categoria buscarAtiva(Long codigoMarca) throws BadRequestException, InternalServerErrorException;

}
