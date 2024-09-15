package com.nextgen.sgp.repository;

import com.nextgen.sgp.domain.cadastro.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT ctg " +
            " FROM Categoria ctg " +
            "WHERE 1=1 " +
            "  AND (:codigo IS NULL OR ctg.codigo = :codigo) " +
            "  AND (:nome IS NULL OR ctg.nome LIKE :nome) " +
            "  AND (:indicadorAtivo IS NULL OR ctg.indicadorAtivo = :indicadorAtivo)" +
            "  AND (:codigoDepartamento IS NULL OR ctg.departamento.codigo = :codigoDepartamento) ")
    List<Categoria> buscar(Long codigo, String nome, Boolean indicadorAtivo, Long codigoDepartamento);

    Categoria findByCodigo(Long codigo);

    Boolean existsByCodigo(Long codigo);

    Boolean existsByNome(String nome);

    @Query("SELECT ctg " +
            " FROM Categoria ctg " +
            "WHERE 1=1 " +
            "  AND ctg.codigo = :codigo " +
            "  AND ctg.indicadorAtivo = true ")
    Categoria buscarAtiva(Long codigo);

}
