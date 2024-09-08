package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.Produto;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.ProdutoRepository;
import com.nextgen.sgp.service.ProdutoService;
import com.nextgen.sgp.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FormatterUtil formatterUtil;

    public List<Produto> buscar(Long codigo, String nome, Integer codigoTipoProduto, Integer codigoStatusProduto, Boolean indicadorSemEstoque) throws InternalServerErrorException {
        try {
            if (nome != null) {
                String nomeFormatado = formatterUtil.removerAcentos(nome);
                nome = "%" + nomeFormatado.toUpperCase().trim() + "%";
            }
            return produtoRepository.buscar(codigo, nome, codigoTipoProduto, codigoStatusProduto, (Boolean.TRUE.equals(indicadorSemEstoque) ? 0 : null));
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar os produtos! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
