package com.nextgen.sgp.repository;

import com.nextgen.sgp.domain.cadastro.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    @Query("SELECT mrc " +
            " FROM Marca mrc " +
            "WHERE 1=1 " +
            "  AND (:codigo IS NULL OR mrc.codigo = :codigo) " +
            "  AND (:nome IS NULL OR mrc.nome LIKE :nome) " +
            "  AND (:indicadorAtivo IS NULL OR mrc.indicadorAtivo = :indicadorAtivo) ")
    List<Marca> buscar(Long codigo, String nome, Boolean indicadorAtivo);

    Marca findByCodigo(Long codigo);

    Boolean existsByNome(String nome);

    @Query("SELECT mrc " +
            " FROM Marca mrc " +
            "WHERE 1=1 " +
            "  AND mrc.codigo = :codigo " +
            "  AND mrc.indicadorAtivo = true ")
    Marca buscarAtiva(Long codigo);

}
