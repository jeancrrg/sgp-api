package com.nextgen.sgp.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
@Entity
@Table(name = "TDEPARTAMENTO")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODDPT")
    private Long codigo;

    @Column(name = "NOMDPT")
    private String nome;

    @Column(name = "INDATV")
    private Boolean indicadorAtivo;

    public Departamento() {

    }

    public Departamento(Long codigo, String nome, Boolean indicadorAtivo) {
        this.codigo = codigo;
        this.nome = nome;
        this.indicadorAtivo = indicadorAtivo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getIndicadorAtivo() {
        return indicadorAtivo;
    }

    public void setIndicadorAtivo(Boolean indicadorAtivo) {
        this.indicadorAtivo = indicadorAtivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departamento that = (Departamento) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

}
