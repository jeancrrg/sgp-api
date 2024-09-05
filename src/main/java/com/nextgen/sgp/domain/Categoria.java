package com.nextgen.sgp.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
@Entity
@Table(name = "TCATEGORIA")
public class Categoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODCTG")
    private Long codigo;

    @Column(name = "NOMCTG")
    private String nome;

    @Column(name = "INDATV")
    private Boolean indicadorAtivo;

    @ManyToOne
    @JoinColumn(name = "CODDPT", referencedColumnName = "CODDPT")
    private Departamento departamento;

    @Column(name = "DATULTALT")
    private Date dataUltimaAlteracao;

    public Categoria() {

    }

    public Categoria(Long codigo, String nome, Boolean indicadorAtivo, Departamento departamento, Date dataUltimaAlteracao) {
        this.codigo = codigo;
        this.nome = nome;
        this.indicadorAtivo = indicadorAtivo;
        this.departamento = departamento;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(codigo, categoria.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

}
