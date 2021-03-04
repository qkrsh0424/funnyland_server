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
                        + "WHERE p.productDeleted=0 AND pc.productCategoryDeleted=0 AND p.productNewChecked IN :newChecked AND p.productHitChecked IN :hitChecked AND p.productEventChecked IN :eventChecked\n"
                        + "ORDER BY p.productUpdated DESC")
        public List<ProductJProductCategoryProj> selectAllConProductCategory(Pageable pageable, int[] newChecked, int[] hitChecked, int[] eventChecked);

        @Query("SELECT p AS product, pc AS category FROM ProductPureEntity p\n"
                        + "LEFT OUTER JOIN ProductCategoryPureEntity pc ON p.productCategoryId = pc.productCategoryId\n"
                        + "WHERE p.productDeleted=0 AND p.productCategoryId=:categoryId AND pc.productCategoryDeleted=0 AND p.productNewChecked IN :newChecked AND p.productHitChecked IN :hitChecked AND p.productEventChecked IN :eventChecked\n"
                        + "ORDER BY p.productUpdated DESC")
        public List<ProductJProductCategoryProj> selectSomeConProductCategoryByCategoryIdAndPageIndex(int categoryId, Pageable pageable, int[] newChecked, int[] hitChecked, int[] eventChecked);

        @Query("SELECT count(p) FROM ProductPureEntity p\n"
                        + "LEFT OUTER JOIN ProductCategoryPureEntity pc ON p.productCategoryId = pc.productCategoryId\n"
                        + "WHERE p.productDeleted=0 AND pc.productCategoryDeleted=0 AND p.productNewChecked IN :newChecked AND p.productHitChecked IN :hitChecked AND p.productEventChecked IN :eventChecked")
        public int countExistAll(int[] newChecked, int[] hitChecked, int[] eventChecked);

        @Query("SELECT count(p) FROM ProductPureEntity p\n"
                        + "LEFT OUTER JOIN ProductCategoryPureEntity pc ON p.productCategoryId = pc.productCategoryId\n"
                        + "WHERE p.productDeleted=0 AND pc.productCategoryDeleted=0 AND p.productCategoryId=:categoryId AND p.productNewChecked IN :newChecked AND p.productHitChecked IN :hitChecked AND p.productEventChecked IN :eventChecked")
        public int countExistAllByCategoryId(int categoryId, int[] newChecked, int[] hitChecked, int[] eventChecked);

        @Query("SELECT p AS product, pc AS category FROM ProductPureEntity p\n"
                        + "LEFT OUTER JOIN ProductCategoryPureEntity pc ON p.productCategoryId = pc.productCategoryId\n"
                        + "WHERE p.productDeleted=0 AND p.productId=:productId")
        public Optional<ProductJProductCategoryProj> selectOneByProductIdExist(int productId);
}
