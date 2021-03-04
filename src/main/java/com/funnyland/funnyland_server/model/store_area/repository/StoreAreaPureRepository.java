package com.funnyland.funnyland_server.model.store_area.repository;

import java.util.List;

import com.funnyland.funnyland_server.model.store_area.entity.StoreAreaPureEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreAreaPureRepository extends JpaRepository<StoreAreaPureEntity, Integer>{
    @Query("SELECT sa FROM StoreAreaPureEntity sa WHERE sa.storeAreaDeleted=0")
    List<StoreAreaPureEntity> selectAllByExist();
}
