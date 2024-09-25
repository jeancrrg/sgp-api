package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.cadastro.Marca;
import com.nextgen.sgp.domain.cadastro.Produto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.MarcaRepository;
import com.nextgen.sgp.service.MarcaService;
import com.nextgen.sgp.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            throw new InternalServerErrorException("ERRO: Erro ao buscar as marcas! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public Marca cadastrar(Marca marca) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosMarca(marca);
            String nomeMarcaFormatado = formatterUtil.removerAcentos(marca.getNome());
            marca.setNome(nomeMarcaFormatado.toUpperCase().trim());
            if (marcaRepository.existsByNome(marca.getNome())) {
                throw new BadRequestException("Já possui essa marca cadastrada!");
            }
            marca.setDataCadastro(LocalDateTime.now());
            marca.setDataUltimaAlteracao(LocalDateTime.now());
            return marcaRepository.save(marca);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao cadastrar a marca! - MENSAGEM DO ERRO: " + e.getMessage());
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
            Marca marcaEncontrada = marcaRepository.findByCodigo(marca.getCodigo());
            if (marcaEncontrada == null) {
                throw new BadRequestException("Marca não encontrada para atualizar!");
            }
            String nomeMarcaFormatado = formatterUtil.removerAcentos(marca.getNome());
            marcaEncontrada.setNome(nomeMarcaFormatado.toUpperCase().trim());
            marcaEncontrada.setIndicadorAtivo(marca.getIndicadorAtivo());
            marcaEncontrada.setDataUltimaAlteracao(LocalDateTime.now());
            return marcaRepository.save(marcaEncontrada);
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
                throw new BadRequestException(STR."Marca: \{codigo} não encontrada para inativar!");
            }
            validarPossuiProdutoAssociadoMarca(codigo);
            marcaEncontrada.setIndicadorAtivo(Boolean.FALSE);
            marcaEncontrada.setDataUltimaAlteracao(LocalDateTime.now());
            marcaRepository.save(marcaEncontrada);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException(STR."ERRO: Erro ao inativar a marca! - MENSAGEM DO ERRO: \{e.getMessage()}");
        }
    }

    public void validarPossuiProdutoAssociadoMarca(Long codigoMarca) throws BadRequestException, InternalServerErrorException {
        try {
            List<Produto> listaProdutosAssociadosMarca = marcaRepository.buscarProdutosAssociadoMarca(codigoMarca);
            if (listaProdutosAssociadosMarca != null && !listaProdutosAssociadosMarca.isEmpty()) {
                throw new BadRequestException("Não é possível inativar essa marca pois possui produto associado! Inative o produto para inativar essa marca.");
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException(STR."ERRO: Erro ao validar se possui produto associado a marca: \{codigoMarca}! - MENSAGEM DO ERRO: \{e.getMessage()}");
        }
    }

    public Marca buscarAtiva(Long codigo) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigo == null) {
                throw new BadRequestException("Código da marca não encontrado!");
            }
            return marcaRepository.buscarAtiva(codigo);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar a marca ativa! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
