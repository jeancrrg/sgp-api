package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.cadastro.StatusProduto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.StatusProdutoRepository;
import com.nextgen.sgp.service.StatusProdutoService;
import com.nextgen.sgp.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusProdutoServiceImpl implements StatusProdutoService {

    @Autowired
    private StatusProdutoRepository statusProdutoRepository;

    @Autowired
    private FormatterUtil formatterUtil;

    public List<StatusProduto> buscar(Long codigo, String descricao) throws InternalServerErrorException {
        try {
            if (descricao != null) {
                String descricaoFormatada = formatterUtil.removerAcentos(descricao);
                descricao = "%" + descricaoFormatada.toUpperCase().trim() + "%";
            }
            return statusProdutoRepository.buscar(codigo, descricao);
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar os status de produto! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public StatusProduto findByDescricao(String descricao) throws BadRequestException, InternalServerErrorException {
        try {
            if (descricao == null) {
                throw new BadRequestException("Descrição do status do produto não encontrada!");
            }
            return statusProdutoRepository.findByDescricao(descricao.toUpperCase().trim());
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar os status de produto! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
