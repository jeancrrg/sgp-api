package com.nextgen.sgp.repository;

import com.nextgen.sgp.domain.dto.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    @Query("SELECT mrc " +
            " FROM Marca mrc " +
            "WHERE 1=1 " +
            "  AND (:codigoMarca IS NULL OR mrc.codigo = :codigoMarca) " +
            "  AND (:nomeMarca IS NULL OR mrc.nome LIKE :nomeMarca) " +
            "  AND (:indicadorAtivo IS NULL OR mrc.indicadorAtivo = :indicadorAtivo) ")
    List<Marca> buscar(Long codigoMarca, String nomeMarca, Boolean indicadorAtivo);

}
