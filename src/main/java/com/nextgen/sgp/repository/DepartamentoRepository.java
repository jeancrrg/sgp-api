package com.nextgen.sgp.repository;

import com.nextgen.sgp.domain.cadastro.Departamento;
import com.nextgen.sgp.domain.cadastro.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    @Query("SELECT dpt " +
            " FROM Departamento dpt " +
            "WHERE 1=1 " +
            "  AND (:codigo IS NULL OR dpt.codigo = :codigo) " +
            "  AND (:nome IS NULL OR dpt.nome LIKE :nome) " +
            "  AND (:indicadorAtivo IS NULL OR dpt.indicadorAtivo = :indicadorAtivo) ")
    List<Departamento> buscar(Long codigo, String nome, Boolean indicadorAtivo);

    Departamento findByCodigo(Long codigo);

    Boolean existsByNome(String nome);

    @Query("SELECT pro " +
            " FROM Produto pro " +
            "WHERE 1=1 " +
            "  AND pro.departamento.codigo = :codigoDepartamento " +
            "  AND pro.statusProduto.codigo = 1 ")
    List<Produto> buscarProdutosAssociadoDepartamento(Long codigoDepartamento);

}
