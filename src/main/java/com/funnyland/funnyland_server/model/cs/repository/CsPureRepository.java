package com.funnyland.funnyland_server.model.cs.repository;

import java.util.List;
import java.util.Optional;

import com.funnyland.funnyland_server.model.cs.entity.CsPureEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CsPureRepository extends JpaRepository<CsPureEntity, Integer>{
    @Query("SELECT cs FROM CsPureEntity cs WHERE cs.csDeleted=0 AND cs.csType LIKE %:csType% ORDER BY cs.csImportantChecked DESC, cs.csCreated DESC")
    List<CsPureEntity> selectAllByExist(String csType, Pageable pageable);
    
    @Query("SELECT count(cs) FROM CsPureEntity cs WHERE cs.csType LIKE %:csType%")
    int countExistAllByCsType(String csType);

    @Query("SELECT cs FROM CsPureEntity cs WHERE cs.csId=:csId AND cs.csDeleted=0")
    Optional<CsPureEntity> selectOneByCsId(int csId);
}
