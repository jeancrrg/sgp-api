package com.nextgen.sgp.repository;

import com.nextgen.sgp.domain.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Integer> {

    @Query("SELECT tip " +
            " FROM TipoProduto tip " +
            "WHERE 1=1 " +
            "  AND (:codigo IS NULL OR tip.codigo = :codigo) " +
            "  AND (:descricao IS NULL OR tip.descricao LIKE :descricao) ")
    List<TipoProduto> buscar(Long codigo, String descricao);

}
