package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.Departamento;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;

import java.util.List;

public interface DepartamentoService {

    List<Departamento> buscar(Long codigo, String nome, Boolean indicadorAtivo) throws InternalServerErrorException;

    Departamento salvar(Departamento departamento) throws BadRequestException, InternalServerErrorException;

    Departamento atualizar(Departamento departamento) throws BadRequestException, InternalServerErrorException;

    void inativar(Long codigo) throws BadRequestException, InternalServerErrorException;

}
