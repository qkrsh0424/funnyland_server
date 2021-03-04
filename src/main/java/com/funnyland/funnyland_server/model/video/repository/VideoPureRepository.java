package com.funnyland.funnyland_server.model.video.repository;

import javax.transaction.Transactional;

import com.funnyland.funnyland_server.model.video.entity.VideoPureEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VideoPureRepository extends JpaRepository<VideoPureEntity, Integer>{
    @Transactional
    @Modifying
    @Query("UPDATE VideoPureEntity v SET v.videoDisplay=0 WHERE v.videoDisplay=1")
    int updateDisplaySetClear();
}
