package com.funnyland.funnyland_server.model.counseling.repository;

import java.util.List;

import com.funnyland.funnyland_server.model.counseling.entity.CounselingPureEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CounselingPureRepository extends JpaRepository<CounselingPureEntity, Long>{
    @Query("SELECT c FROM CounselingPureEntity c WHERE c.counselingDeleted=0 ORDER BY c.counselingCreated DESC")
    List<CounselingPureEntity> selectCounselingAllByExist(Pageable pageable);
}
