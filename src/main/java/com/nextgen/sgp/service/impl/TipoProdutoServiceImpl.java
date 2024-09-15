package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.cadastro.TipoProduto;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.TipoProdutoRepository;
import com.nextgen.sgp.service.TipoProdutoService;
import com.nextgen.sgp.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProdutoServiceImpl implements TipoProdutoService {

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    @Autowired
    private FormatterUtil formatterUtil;

    public List<TipoProduto> buscar(Long codigo, String descricao) throws InternalServerErrorException {
        try {
            if (descricao != null) {
                String descricaoFormatada = formatterUtil.removerAcentos(descricao);
                descricao = "%" + descricaoFormatada.toUpperCase().trim() + "%";
            }
            return tipoProdutoRepository.buscar(codigo, descricao);
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar os tipos de produto! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
