package com.funnyland.funnyland_server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.banner.entity.BannerPureEntity;
import com.funnyland.funnyland_server.model.banner.repository.BannerPureRepository;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.counseling.entity.CounselingPureEntity;
import com.funnyland.funnyland_server.model.counseling.repository.CounselingPureRepository;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.product.dto.ProductJCategoryGetDto;
import com.funnyland.funnyland_server.model.product.projection.ProductJProductCategoryProj;
import com.funnyland.funnyland_server.model.product.repository.ProductPureRepository;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.product_category.entity.ProductCategoryPureEntity;
import com.funnyland.funnyland_server.model.product_category.repository.ProductCategoryPureRepository;
import com.funnyland.funnyland_server.service.handler.RSAHandlerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    RSAHandlerService rsaService;

    @Autowired
    BannerPureRepository bannerPureRepository;

    @Autowired
    CounselingPureRepository counselingPureRepository;

    @Autowired
    ProductCategoryPureRepository productCategoryPureRepository;

    @Autowired
    ProductPureRepository productPureRepository;

    public List<BannerGetDto> searchBannerAllByorderService() {
        List<BannerPureEntity> entities = bannerPureRepository.selectBannerAllByorder();
        return getBannerDtosByEntitiesConv(entities);
    }

    private List<BannerGetDto> getBannerDtosByEntitiesConv(List<BannerPureEntity> entities) {
        List<BannerGetDto> dtos = new ArrayList<>();
        // for(BannerPureEntity entity:entities){
        // BannerGetDto dto = new BannerGetDto();
        // dto.setId(entity.getBannerId());
        // dto.setImageUrl(entity.getBannerImageUrl());
        // dto.setHide(entity.getBannerHide());
        // dto.setOrder(entity.getBannerOrder());

        // dtos.add(dto);
        // }
        for (int i = 0; i < entities.size(); i++) {
            BannerGetDto dto = new BannerGetDto();
            dto.setId(entities.get(i).getBannerId());
            dto.setImageUrl(entities.get(i).getBannerImageUrl());
            dto.setHide(entities.get(i).getBannerHide());
            dto.setOrder(entities.get(i).getBannerOrder());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<CounselingGetDto> searchCounselingAllService(int pageIndex, int displaySize) {
        Pageable pageable = PageRequest.of(pageIndex, displaySize);
        List<CounselingPureEntity> entities = counselingPureRepository.selectCounselingAllByExist(pageable);
        return getCounselingDtosByEntitiesConv(entities);
    }

    private List<CounselingGetDto> getCounselingDtosByEntitiesConv(List<CounselingPureEntity> entities) {
        List<CounselingGetDto> dtos = new ArrayList<>();
        for (CounselingPureEntity entity : entities) {
            CounselingGetDto dto = new CounselingGetDto();
            dto.setId(entity.getCounselingId());
            dto.setCounselingType(entity.getCounselingType());
            dto.setApplierName(rsaService.getDecryptRsaStr(entity.getCounselingApplier()));
            dto.setApplierPhone(rsaService.getDecryptRsaStr(entity.getCounselingPhone()));
            dto.setApplierArea(entity.getCounselingArea());
            dto.setAddress(entity.getCounselingAddress());
            dto.setFloor(entity.getCounselingFloor());
            dto.setOpenDate(entity.getCounselingOpenDate());
            dto.setDesc(entity.getCounselingDesc());
            dto.setPrivacyAgreement(entity.getCounselingPrivacyAgreement());
            dto.setAdminChecked(entity.getCounselingAdminChecked());
            dto.setCreatedAt(entity.getCounselingCreated());
            dto.setUpdatedAt(entity.getCounselingUpdated());
            dtos.add(dto);
        }
        return dtos;
    }

    public int searchCounselingAllCountService() {
        return counselingPureRepository.findAll().size();
    }

    public List<ProductCategoryGetDto> searchProductCategoryAllService() {
        List<ProductCategoryPureEntity> entities = productCategoryPureRepository.selectAllByExist();
        List<ProductCategoryGetDto> dtos = getProductCategoryDtosByEntites(entities);
        return dtos;
    }

    private List<ProductCategoryGetDto> getProductCategoryDtosByEntites(List<ProductCategoryPureEntity> entities) {
        List<ProductCategoryGetDto> dtos = new ArrayList<>();
        for (ProductCategoryPureEntity entity : entities) {
            ProductCategoryGetDto dto = new ProductCategoryGetDto();
            dto.setId(entity.getProductCategoryId());
            dto.setCategoryName(entity.getProductCategoryName());
            dto.setPriority(entity.getProductCategoryPriority());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProductJCategoryGetDto> searchProductAllService(String categoryIdStr, int pageIndex, int displaySize) {
        List<ProductJProductCategoryProj> projs = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageIndex, displaySize);

        if (categoryIdStr == null) {
            projs = productPureRepository.selectAllConProductCategory(pageable);
        } else {
            int categoryId = -1;
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (NumberFormatException e) {
                // System.out.println(e);
            }
            if (categoryId == -1) {
                projs = productPureRepository.selectAllConProductCategory(pageable);
            } else {
                projs = productPureRepository.selectSomeConProductCategoryByCategoryIdAndPageIndex(categoryId,
                        pageable);
            }

        }
        List<ProductJCategoryGetDto> dtos = getProductJCategoryDtosByProjs(projs);

        return dtos;
    }

    public int searchCountForProductExistService(String categoryIdStr) {
        int categoryId = -1;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {

        }
        if (categoryId == -1) {
            return productPureRepository.countExistAll();
        }
        return productPureRepository.countExistAllByCategoryId(categoryId);
    }

    private List<ProductJCategoryGetDto> getProductJCategoryDtosByProjs(List<ProductJProductCategoryProj> projs) {
        List<ProductJCategoryGetDto> pjcDtos = new ArrayList<>();
        for (ProductJProductCategoryProj proj : projs) {
            ProductJCategoryGetDto pjcDto = new ProductJCategoryGetDto();
            ProductGetDto pDto = new ProductGetDto();
            ProductCategoryGetDto pcDto = new ProductCategoryGetDto();

            pDto.setCategoryId(proj.getProduct().getProductCategoryId());
            pDto.setDesc(proj.getProduct().getProductDesc());
            pDto.setHide(proj.getProduct().getProductHide());
            pDto.setId(proj.getProduct().getProductId());
            pDto.setImageUrl(proj.getProduct().getProductImageUrl());
            pDto.setIntroduce(proj.getProduct().getProductIntroduce());
            pDto.setName(proj.getProduct().getProductName());
            pDto.setPriority(proj.getProduct().getProductPriority());
            pDto.setSummary(proj.getProduct().getProductSummary());

            pcDto.setId(proj.getCategory().getProductCategoryId());
            pcDto.setCategoryName(proj.getCategory().getProductCategoryName());
            pcDto.setPriority(proj.getCategory().getProductCategoryPriority());

            pjcDto.setProduct(pDto);
            pjcDto.setCategory(pcDto);
            pjcDtos.add(pjcDto);
        }
        return pjcDtos;
    }

    public ProductJCategoryGetDto searchProductOneService(int productId) {
        // System.out.println(productPureRepository.selectOneByProductIdExist(productId));
        Optional<ProductJProductCategoryProj> proj = productPureRepository.selectOneByProductIdExist(productId);
        
        if (proj.isPresent()) {
            ProductJCategoryGetDto dto = getProductJCategoryDtoByProj(proj.get());
            return dto;
        }else{
            return null;
        }
    }

    private ProductJCategoryGetDto getProductJCategoryDtoByProj(ProductJProductCategoryProj proj) {
        ProductJCategoryGetDto dto = new ProductJCategoryGetDto();

        ProductGetDto pDto = new ProductGetDto();
        ProductCategoryGetDto pcDto = new ProductCategoryGetDto();

        pDto.setCategoryId(proj.getProduct().getProductCategoryId());
        pDto.setDesc(proj.getProduct().getProductDesc());
        pDto.setHide(proj.getProduct().getProductHide());
        pDto.setId(proj.getProduct().getProductId());
        pDto.setImageUrl(proj.getProduct().getProductImageUrl());
        pDto.setIntroduce(proj.getProduct().getProductIntroduce());
        pDto.setName(proj.getProduct().getProductName());
        pDto.setPriority(proj.getProduct().getProductPriority());
        pDto.setSummary(proj.getProduct().getProductSummary());

        pcDto.setId(proj.getCategory().getProductCategoryId());
        pcDto.setCategoryName(proj.getCategory().getProductCategoryName());
        pcDto.setPriority(proj.getCategory().getProductCategoryPriority());

        dto.setProduct(pDto);
        dto.setCategory(pcDto);
        return dto;
    }
}
