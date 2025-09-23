package com.diogo.streaming.repository;

import com.diogo.streaming.domain.Usuario;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
           select u as usuario, count(distinct vis.video.id) as total
           from Visualizacao vis
           join vis.perfil p
           join p.usuario u
           group by u
           order by count(distinct vis.video.id) desc
           """)
    List<UserViews> usuariosQueMaisAssistiram(Pageable pageable);

    default List<UserViews> usuariosQueMaisAssistiram(int limit) {
        return usuariosQueMaisAssistiram(PageRequest.of(0, limit));
    }
}
