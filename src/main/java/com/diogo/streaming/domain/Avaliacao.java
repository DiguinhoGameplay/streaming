package com.diogo.streaming.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "avaliacao")
public class Avaliacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @ManyToOne(optional = false) @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(nullable = false)
    private Integer nota; // 0..10

    @Column(columnDefinition = "text")
    private String comentario;

    public Avaliacao() {}
    public Avaliacao(Perfil perfil, Video video, Integer nota, String comentario) {
        this.perfil = perfil; this.video = video; this.nota = nota; this.comentario = comentario;
    }

    public Long getId() { return id; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    public Video getVideo() { return video; }
    public void setVideo(Video video) { this.video = video; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
