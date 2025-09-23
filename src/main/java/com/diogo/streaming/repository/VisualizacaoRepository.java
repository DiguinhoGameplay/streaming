package com.diogo.streaming.repository;

import com.diogo.streaming.domain.Visualizacao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VisualizacaoRepository extends JpaRepository<Visualizacao, Long> {

    @Query("""
           select v.video as video, count(v) as total
           from Visualizacao v
           group by v.video
           order by total desc
           """)
    List<VideoViews> topWatched(Pageable page);

    @Query("""
           select p.usuario as usuario, count(distinct v.video) as total
           from Visualizacao v
           join v.perfil p
           group by p.usuario
           order by total desc
           """)
    List<UserViews> topUsersDistinct(Pageable page);
}
