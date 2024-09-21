package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.cadastro.Categoria;
import com.nextgen.sgp.domain.cadastro.Marca;
import com.nextgen.sgp.domain.cadastro.Produto;
import com.nextgen.sgp.domain.cadastro.StatusProduto;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.InternalServerErrorException;
import com.nextgen.sgp.repository.ProdutoRepository;
import com.nextgen.sgp.service.*;
import com.nextgen.sgp.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private StatusProdutoService statusProdutoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FormatterUtil formatterUtil;

    public List<Produto> buscar(Long codigo, String nome, Integer codigoTipoProduto, Integer codigoStatusProduto, Boolean indicadorSemEstoque, Long codigoMarca, Long codigoDepartamento, Long codigoCategoria) throws InternalServerErrorException {
        try {
            if (nome != null && !nome.isEmpty()) {
                String nomeFormatado = formatterUtil.removerAcentos(nome);
                nome = "%" + nomeFormatado.toUpperCase().trim() + "%";
            }
            return produtoRepository.buscar(codigo, nome, codigoTipoProduto, codigoStatusProduto, (Boolean.TRUE.equals(indicadorSemEstoque) ? 0 : null), codigoMarca, codigoDepartamento, codigoCategoria);
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao buscar os produtos! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public Produto cadastrar(Produto produto) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosProduto(produto);
            formatarCamposProduto(produto);
            if (produtoRepository.existsByCodigoBarrasEAN(produto.getCodigoBarrasEAN())) {
                throw new BadRequestException("Já possui um produto cadastrado com esse código de barras EAN!");
            }
            produto.setDataCadastro(LocalDateTime.now());
            produto.setDataUltimaAlteracao(LocalDateTime.now());
            return produtoRepository.save(produto);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao salvar a produto! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void validarDadosProduto(Produto produto) throws BadRequestException, InternalServerErrorException {
        validarCamposObrigatorios(produto);
        validarAtributosProduto(produto);
    }

    public void validarCamposObrigatorios(Produto produto) throws BadRequestException {
        if (produto == null) {
            throw new BadRequestException("Produto não encontrado!");
        }
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new BadRequestException("Nome do produto não encontrado!");
        }
        if (produto.getCodigoBarrasEAN() == null || produto.getCodigoBarrasEAN().isEmpty()) {
            throw new BadRequestException("Código de barras EAN do produto não encontrado!");
        }
        if (produto.getCodigoBarrasEAN().length() < 13) {
            throw new BadRequestException("Código de barras EAN do produto não possui 13 dígitos!");
        }
        if (produto.getStatusProduto() == null || produto.getStatusProduto().getCodigo() == null) {
            throw new BadRequestException("Status do produto não encontrado!");
        }
        if (produto.getTipoProduto() == null || produto.getTipoProduto().getCodigo() == null) {
            throw new BadRequestException("Tipo do produto não encontrado!");
        }
        if (produto.getPreco() == null) {
            throw new BadRequestException("Preço do produto não encontrado!");
        }
        if (produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Preço do produto não deve ser menor ou igual a zero!");
        }
        if (produto.getQuantidadeEstoque() == null) {
            throw new BadRequestException("Quantidade de estoque do produto não encontrado!");
        }
        if (produto.getMarca() == null || produto.getMarca().getCodigo() == null) {
            throw new BadRequestException("Marca do produto não encontrada!");
        }
        if (produto.getDepartamento() == null || produto.getDepartamento().getCodigo() == null) {
            throw new BadRequestException("Departamento do produto não encontrado!");
        }
        if (produto.getCategoria() == null || produto.getCategoria().getCodigo() == null) {
            throw new BadRequestException("Categoria do produto não encontrada!");
        }
    }

    public void validarAtributosProduto(Produto produto) throws BadRequestException, InternalServerErrorException {
        try {
            if (produto == null) {
                throw new BadRequestException("Produto não encontrado!");
            }
            Marca marcaEncontrada = marcaService.buscarAtiva(produto.getCategoria() != null ? produto.getCategoria().getCodigo() : null);
            if (marcaEncontrada == null) {
                throw new BadRequestException("Marca do produto não possui cadastro ou está inativa!");
            }
            Categoria categoriaEncontrada = categoriaService.buscarAtiva(produto.getDepartamento() != null ? produto.getDepartamento().getCodigo() : null);
            if (categoriaEncontrada == null) {
                throw new BadRequestException("Categoria do produto não possui cadastro ou está inativa!");
            }
            if (categoriaEncontrada.getDepartamento() == null) {
                throw new BadRequestException("Nenhum departamento encontrado associação a essa categoria!");
            }
            if (!Objects.equals(categoriaEncontrada.getDepartamento().getCodigo(), produto.getDepartamento() != null ? produto.getDepartamento().getCodigo() : null)) {
                throw new BadRequestException("Esse departamento não está associado a essa categoria!");
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao validar se os atributos do produto possui cadastro! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void formatarCamposProduto(Produto produto) {
        String nomeProdutoFormatado = formatterUtil.removerAcentos(produto.getNome());
        produto.setNome(nomeProdutoFormatado.toUpperCase().trim());
        String codigoBarrasEAN = produto.getCodigoBarrasEAN().trim().replaceAll("[.,]", "");
        produto.setCodigoBarrasEAN(codigoBarrasEAN);
    }

    public Produto atualizar(Produto produto) throws BadRequestException, InternalServerErrorException {
        try {
            validarDadosProduto(produto);
            Produto produtoEncontrado = produtoRepository.findByCodigo(produto.getCodigo());
            if (produtoEncontrado == null) {
                throw new BadRequestException("Produto não encontrado cadastro para atualizar!");
            }
            produtoEncontrado.setDataUltimaAlteracao(LocalDateTime.now());
            return produtoRepository.save(produtoEncontrado);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao atualizar o produto! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void inativar(Long codigo) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigo == null) {
                throw new BadRequestException("Código do produto não encontrado para inativar!");
            }
            Produto produtoEncontrado = produtoRepository.findByCodigo(codigo);
            if (produtoEncontrado == null) {
                throw new BadRequestException("Produto não encontrado para inativar!");
            }
            StatusProduto statusProduto = statusProdutoService.findByDescricao("INATIVO");
            if (statusProduto == null) {
                throw new BadRequestException("Nenhum status de inativo encontrado para inativar o produto!");
            }

            produtoEncontrado.setStatusProduto(statusProduto);
            produtoEncontrado.setDataUltimaAlteracao(LocalDateTime.now());
            produtoRepository.save(produtoEncontrado);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("ERRO: Erro ao inativar o produto! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
