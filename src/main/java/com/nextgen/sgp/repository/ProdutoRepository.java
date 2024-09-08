package com.nextgen.sgp.repository;

import com.nextgen.sgp.domain.Marca;
import com.nextgen.sgp.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT pro " +
            " FROM Produto pro " +
            "WHERE 1=1 " +
            "  AND (:codigo IS NULL OR pro.codigo = :codigo) " +
            "  AND (:nome IS NULL OR pro.nome LIKE :nome) " +
            "  AND (:codigoTipoProduto IS NULL OR pro.statusProduto.codigo = :codigoTipoProduto) " +
            "  AND (:codigoStatusProduto IS NULL OR pro.tipoProduto.codigo = :codigoStatusProduto) " +
            "  AND (:quantidadeEstoque IS NULL OR pro.quantidadeEstoque <= :quantidadeEstoque) ")
    List<Produto> buscar(Long codigo, String nome, Integer codigoTipoProduto, Integer codigoStatusProduto, Integer quantidadeEstoque);

}
