package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.cadastro.Categoria;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.CategoriaRepository;
import com.nextgen.sgp.service.CategoriaService;
import com.nextgen.sgp.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FormatterUtil formatterUtil;

    public List<Categoria> buscar(Long codigo, String nome, Boolean indicadorAtivo, Long codigocategoria) throws InternalServerErrorException {
        try {
            if (nome != null) {
                String nomeFormatado = formatterUtil.removerAcentos(nome);
                nome = "%" + nomeFormatado.toUpperCase().trim() + "%";
            }
            return categoriaRepository.buscar(codigo, nome, indicadorAtivo, codigocategoria);
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar as categorias! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public Categoria cadastrar(Categoria categoria) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosCategoria(categoria);
            String nomeCategoriaFormatado = formatterUtil.removerAcentos(categoria.getNome());
            categoria.setNome(nomeCategoriaFormatado.toUpperCase().trim());
            if (categoriaRepository.existsByNome(categoria.getNome())) {
                throw new BadRequestException("Já possui essa categoria cadastrada!");
            }
            categoria.setDataCadastro(LocalDateTime.now());
            categoria.setDataUltimaAlteracao(LocalDateTime.now());
            return categoriaRepository.save(categoria);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao cadastrar a categoria! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void validarDadosCategoria(Categoria categoria) throws BadRequestException {
        if (categoria == null) {
            throw new BadRequestException("Categoria não encontrada!");
        }
        if (categoria.getNome() == null || categoria.getNome().isEmpty()) {
            throw new BadRequestException("Nome da categoria não encontrado!");
        }
        if (categoria.getIndicadorAtivo() == null) {
            throw new BadRequestException("Indicador de ativo da categoria não encontrado!");
        }
        if (categoria.getDepartamento() == null) {
            throw new BadRequestException("Departamento da categoria não encontrado!");
        }
    }

    public Categoria atualizar(Categoria categoria) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosCategoria(categoria);
            if (!categoriaRepository.existsByCodigo(categoria.getCodigo())) {
                throw new BadRequestException("Categoria não encontrada para atualizar!");
            }
            String nomeCategoriaFormatado = formatterUtil.removerAcentos(categoria.getNome());
            categoria.setNome(nomeCategoriaFormatado.toUpperCase().trim());
            categoria.setDataUltimaAlteracao(LocalDateTime.now());
            return categoriaRepository.save(categoria);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao atualizar a categoria! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void inativar(Long codigo) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigo == null) {
                throw new BadRequestException("Código da categoria não encontrado para inativar!");
            }
            Categoria categoriaEncontrada = categoriaRepository.findByCodigo(codigo);
            if (categoriaEncontrada == null) {
                throw new BadRequestException("Categoria não encontrada para inativar!");
            }
            categoriaEncontrada.setIndicadorAtivo(Boolean.FALSE);
            categoriaEncontrada.setDataUltimaAlteracao(LocalDateTime.now());
            categoriaRepository.save(categoriaEncontrada);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao inativar a categoria! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public Categoria buscarAtiva(Long codigo) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigo == null) {
                throw new BadRequestException("Código da categoria não encontrado!");
            }
            return categoriaRepository.buscarAtiva(codigo);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar a categoria ativa! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
