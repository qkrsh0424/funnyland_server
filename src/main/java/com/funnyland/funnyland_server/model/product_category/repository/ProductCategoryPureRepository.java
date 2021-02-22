package com.funnyland.funnyland_server.model.product_category.repository;

import java.util.List;

import com.funnyland.funnyland_server.model.product_category.entity.ProductCategoryPureEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCategoryPureRepository extends JpaRepository<ProductCategoryPureEntity, Integer>{
    @Query("SELECT pc FROM ProductCategoryPureEntity pc WHERE pc.productCategoryDeleted=0 ORDER BY pc.productCategoryPriority ASC")
	List<ProductCategoryPureEntity> selectAllByExist();
    
}
