package com.funnyland.funnyland_server.model.banner.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.funnyland.funnyland_server.model.banner.entity.BannerPureEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BannerPureRepository extends JpaRepository<BannerPureEntity, Long>{
    @Query("SELECT b FROM BannerPureEntity b WHERE b.bannerDeleted=0 ORDER BY b.bannerOrder ASC, b.bannerCreated DESC")
    List<BannerPureEntity> selectBannerAllByorder();

    @Transactional
    @Modifying
    @Query("UPDATE BannerPureEntity b SET b.bannerOrder=:order WHERE b.bannerId=:id")
    void updateBannerOrder(int order, Long id);
}
