package com.diogo.streaming.repository;

import com.diogo.streaming.domain.Video;
import com.diogo.streaming.repository.VideoRating;
import com.diogo.streaming.repository.VideoViews;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    List<Video> findByCategoriaIdOrderByTituloAsc(Long categoriaId);

    @Query("""
           select v as video, avg(a.nota) as avg, count(a.id) as total
           from Avaliacao a join a.video v
           group by v
           order by avg(a.nota) desc
           """)
    List<VideoRating> topMaisBemAvaliados(Pageable pageable);
    default List<VideoRating> topMaisBemAvaliados(int limit) {
        return topMaisBemAvaliados(PageRequest.of(0, limit));
    }

    @Query("""
           select v as video, count(vis.id) as total
           from Visualizacao vis join vis.video v
           group by v
           order by count(vis.id) desc
           """)
    List<VideoViews> topMaisAssistidos(Pageable pageable);
    default List<VideoViews> topMaisAssistidos(int limit) {
        return topMaisAssistidos(PageRequest.of(0, limit));
    }
}
