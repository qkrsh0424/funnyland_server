package com.funnyland.funnyland_server.model.store.repository;

import java.util.List;
import java.util.Optional;

import com.funnyland.funnyland_server.model.store.entity.StorePureEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StorePureRepository extends JpaRepository<StorePureEntity, Integer>{
    @Query("SELECT s FROM StorePureEntity s WHERE s.storeDeleted=0 AND s.storeArea LIKE %:areaName% ORDER BY s.storeUpdated DESC")
    List<StorePureEntity> selectAllByExist(String areaName, Pageable pageable);

    @Query("SELECT count(s) FROM StorePureEntity s WHERE s.storeDeleted=0 AND s.storeArea LIKE %:areaName%")
	int countExistAllByAreaName(String areaName);

    @Query("SELECT s FROM StorePureEntity s WHERE s.storeDeleted=0 AND s.storeId=:storeId")
	Optional<StorePureEntity> selectOneByStoreId(int storeId);
}
