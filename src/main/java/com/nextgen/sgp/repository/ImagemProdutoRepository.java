package com.nextgen.sgp.repository;

import com.nextgen.sgp.domain.cadastro.ImagemProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {

    @Query("SELECT img " +
            " FROM ImagemProduto img " +
            "WHERE 1=1 " +
            "  AND (:codigo IS NULL OR img.codigo = :codigoProduto) " +
            "  AND (:nome IS NULL OR img.nome LIKE :nome) " +
            "  AND (:codigoProduto IS NULL OR img.codigoProduto = :codigoProduto) ")
    List<ImagemProduto> buscar(Long codigo, String nome, Long codigoProduto);

    @Transactional
    void deleteByCodigo(Long codigo);
}
