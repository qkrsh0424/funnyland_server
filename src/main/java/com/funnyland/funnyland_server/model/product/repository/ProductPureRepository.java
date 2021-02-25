package com.funnyland.funnyland_server.model.product.repository;

import java.util.List;
import java.util.Optional;

import com.funnyland.funnyland_server.model.product.entity.ProductPureEntity;
import com.funnyland.funnyland_server.model.product.projection.ProductJProductCategoryProj;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductPureRepository extends JpaRepository<ProductPureEntity, Integer> {
        @Query("SELECT p AS product, pc AS category FROM ProductPureEntity p\n"
                        + "LEFT OUTER JOIN ProductCategoryPureEntity pc ON p.productCategoryId = pc.productCategoryId\n"
                        + "WHERE p.productDeleted=0")
        public List<ProductJProductCategoryProj> selectAllConProductCategory(Pageable pageable);

        @Query("SELECT p AS product, pc AS category FROM ProductPureEntity p\n"
                        + "LEFT OUTER JOIN ProductCategoryPureEntity pc ON p.productCategoryId = pc.productCategoryId\n"
                        + "WHERE p.productDeleted=0 AND p.productCategoryId=:categoryId")
        public List<ProductJProductCategoryProj> selectSomeConProductCategoryByCategoryIdAndPageIndex(int categoryId,
                        Pageable pageable);

        @Query("SELECT count(p) FROM ProductPureEntity p WHERE p.productDeleted=0")
        public int countExistAll();

        @Query("SELECT count(p) FROM ProductPureEntity p WHERE p.productDeleted=0 AND p.productCategoryId=:categoryId")
        public int countExistAllByCategoryId(int categoryId);

        @Query("SELECT p AS product, pc AS category FROM ProductPureEntity p\n"
                        + "LEFT OUTER JOIN ProductCategoryPureEntity pc ON p.productCategoryId = pc.productCategoryId\n"
                        + "WHERE p.productDeleted=0 AND p.productId=:productId")
        public Optional<ProductJProductCategoryProj> selectOneByProductIdExist(int productId);
}
