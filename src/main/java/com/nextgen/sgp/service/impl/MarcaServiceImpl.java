package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.Marca;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.MarcaRepository;
import com.nextgen.sgp.service.MarcaService;
import com.nextgen.sgp.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private FormatterUtil formatterUtil;

    public List<Marca> buscar(Long codigo, String nome, Boolean indicadorAtivo) throws InternalServerErrorException {
        try {
            if (nome != null) {
                String nomeFormatado = formatterUtil.removerAcentos(nome);
                nome = "%" + nomeFormatado.toUpperCase().trim() + "%";
            }
            return marcaRepository.buscar(codigo, nome, indicadorAtivo);
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar a marca! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public Marca salvar(Marca marca) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosMarca(marca);
            String nomeMarcaFormatado = formatterUtil.removerAcentos(marca.getNome());
            marca.setNome(nomeMarcaFormatado.toUpperCase().trim());
            if (marcaRepository.existsByNome(marca.getNome())) {
                throw new BadRequestException("Já possui essa marca: " + marca.getNome() + " cadastrada!");
            }
            marca.setDataUltimaAlteracao(new Date());
            return marcaRepository.save(marca);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao salvar a marca! - MENSAGEM DO ERRO: " + e.getMessage());
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

    public Marca atualizar(Marca marca) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosMarca(marca);
            if (!marcaRepository.existsByCodigo(marca.getCodigo())) {
                throw new BadRequestException("Marca: " + marca.getNome() + " não encontrada para atualizar!");
            }
            String nomeMarcaFormatado = formatterUtil.removerAcentos(marca.getNome());
            marca.setNome(nomeMarcaFormatado.toUpperCase().trim());
            marca.setDataUltimaAlteracao(new Date());
            return marcaRepository.save(marca);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao atualizar a marca! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void inativar(Long codigo) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigo == null) {
                throw new BadRequestException("Código da marca não encontrado para inativar!");
            }
            Marca marcaEncontrada = marcaRepository.findByCodigo(codigo);
            if (marcaEncontrada == null) {
                throw new BadRequestException("Marca: " + codigo + " não encontrada para inativar!");
            }
            marcaEncontrada.setIndicadorAtivo(Boolean.FALSE);
            marcaEncontrada.setDataUltimaAlteracao(new Date());
            marcaRepository.save(marcaEncontrada);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao inativar a marca! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
