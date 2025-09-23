package com.diogo.streaming.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil", uniqueConstraints = @UniqueConstraint(columnNames = "nome_perfil"))
public class Perfil {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_perfil", nullable = false, length = 255)
    private String nomePerfil;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Perfil() {}
    public Perfil(String nomePerfil, Usuario usuario) {
        this.nomePerfil = nomePerfil; this.usuario = usuario;
    }

    public Long getId() { return id; }
    public String getNomePerfil() { return nomePerfil; }
    public void setNomePerfil(String nomePerfil) { this.nomePerfil = nomePerfil; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
