package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.cadastro.Departamento;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.DepartamentoRepository;
import com.nextgen.sgp.service.DepartamentoService;
import com.nextgen.sgp.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private FormatterUtil formatterUtil;

    public List<Departamento> buscar(Long codigo, String nome, Boolean indicadorAtivo) throws InternalServerErrorException {
        try {
            if (nome != null) {
                String nomeFormatado = formatterUtil.removerAcentos(nome);
                nome = "%" + nomeFormatado.toUpperCase().trim() + "%";
            }
            return departamentoRepository.buscar(codigo, nome, indicadorAtivo);
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar os departamentos! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public Departamento cadastrar(Departamento departamento) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosDepartamento(departamento);
            String nomeDepartamentoFormatado = formatterUtil.removerAcentos(departamento.getNome());
            departamento.setNome(nomeDepartamentoFormatado.toUpperCase().trim());
            if (departamentoRepository.existsByNome(departamento.getNome())) {
                throw new BadRequestException("Já possui esse departamento cadastrado!");
            }
            departamento.setDataCadastro(LocalDateTime.now());
            departamento.setDataUltimaAlteracao(LocalDateTime.now());
            return departamentoRepository.save(departamento);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao cadastrar o departamento! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void validarDadosDepartamento(Departamento departamento) throws BadRequestException {
        if (departamento == null) {
            throw new BadRequestException("Departamento não encontrada!");
        }
        if (departamento.getNome() == null || departamento.getNome().isEmpty()) {
            throw new BadRequestException("Nome do departamento não encontrado!");
        }
        if (departamento.getIndicadorAtivo() == null) {
            throw new BadRequestException("Indicador de ativo do departamento não encontrado!");
        }
    }

    public Departamento atualizar(Departamento departamento) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosDepartamento(departamento);
            Departamento departamentoEncontrado = departamentoRepository.findByCodigo(departamento.getCodigo());
            if (departamentoEncontrado == null) {
                throw new BadRequestException("Departamento não encontrado para atualizar!");
            }
            String nomeDepartamentoFormatado = formatterUtil.removerAcentos(departamento.getNome());
            departamentoEncontrado.setNome(nomeDepartamentoFormatado.toUpperCase().trim());
            departamentoEncontrado.setIndicadorAtivo(departamento.getIndicadorAtivo());
            departamentoEncontrado.setDataUltimaAlteracao(LocalDateTime.now());
            return departamentoRepository.save(departamentoEncontrado);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao atualizar o departamento! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void inativar(Long codigo) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigo == null) {
                throw new BadRequestException("Código do departamento não encontrado para inativar!");
            }
            Departamento departamentoEncontrado = departamentoRepository.findByCodigo(codigo);
            if (departamentoEncontrado == null) {
                throw new BadRequestException("Departamento não encontrado para inativar!");
            }
            departamentoEncontrado.setIndicadorAtivo(Boolean.FALSE);
            departamentoEncontrado.setDataUltimaAlteracao(LocalDateTime.now());
            departamentoRepository.save(departamentoEncontrado);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao inativar o departamento! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
