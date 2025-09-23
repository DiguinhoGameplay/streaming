package com.diogo.streaming.repository;

import com.diogo.streaming.domain.Video;

public interface VideoViews {
    Video getVideo();
    Long getTotal();
}
