package com.nextgen.sgp.repository;

import com.nextgen.sgp.domain.StatusProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusProdutoRepository extends JpaRepository<StatusProduto, Integer> {

    @Query("SELECT sta " +
            " FROM StatusProduto sta " +
            "WHERE 1=1 " +
            "  AND (:codigo IS NULL OR sta.codigo = :codigo) " +
            "  AND (:descricao IS NULL OR sta.descricao LIKE :descricao) ")
    List<StatusProduto> buscar(Long codigo, String descricao);

}
