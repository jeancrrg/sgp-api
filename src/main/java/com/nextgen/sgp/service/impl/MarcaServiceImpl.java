package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.dto.Marca;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.MarcaRepository;
import com.nextgen.sgp.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> buscar(Long codigo, String nome, Boolean indicadorAtivo) throws InternalServerErrorException {
        try {
            return marcaRepository.buscar(codigo, nome, indicadorAtivo);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar a marca! " + e.getMessage());
        }
    }

    public Marca salvar(Marca marca) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosMarca(marca);
            return marcaRepository.save(marca);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar a marca! " + e.getMessage());
        }
    }

    public void validarDadosMarca(Marca marca) throws BadRequestException {
        if (marca == null) {
            throw new BadRequestException("Marca não encontrada!");
        }
        if (marca.getNome() == null || marca.getNome().isEmpty()) {
            throw new BadRequestException("Nome da marca não encontrado!");
        }
        if (marca.getIndicadorAtivo() == null) {
            throw new BadRequestException("Indicador de ativo da marca não encontrado!");
        }
    }

}
