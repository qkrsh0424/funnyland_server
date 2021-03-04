package com.funnyland.funnyland_server.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.funnyland.funnyland_server.crypto_handler.RSAUtil;
import com.funnyland.funnyland_server.model.banner.dto.BannerReqDto;
import com.funnyland.funnyland_server.model.banner.entity.BannerPureEntity;
import com.funnyland.funnyland_server.model.banner.repository.BannerPureRepository;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingReqDto;
import com.funnyland.funnyland_server.model.counseling.entity.CounselingPureEntity;
import com.funnyland.funnyland_server.model.counseling.repository.CounselingPureRepository;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.product_category.entity.ProductCategoryPureEntity;
import com.funnyland.funnyland_server.model.product.entity.ProductPureEntity;
import com.funnyland.funnyland_server.model.product_category.repository.ProductCategoryPureRepository;
import com.funnyland.funnyland_server.model.store.dto.StoreGetDto;
import com.funnyland.funnyland_server.model.store.entity.StorePureEntity;
import com.funnyland.funnyland_server.model.store.repository.StorePureRepository;
import com.funnyland.funnyland_server.model.store_area.dto.StoreAreaGetDto;
import com.funnyland.funnyland_server.model.store_area.entity.StoreAreaPureEntity;
import com.funnyland.funnyland_server.model.store_area.repository.StoreAreaPureRepository;
import com.funnyland.funnyland_server.model.video.dto.VideoGetDto;
import com.funnyland.funnyland_server.model.video.entity.VideoPureEntity;
import com.funnyland.funnyland_server.model.video.repository.VideoPureRepository;
import com.funnyland.funnyland_server.model.product.repository.ProductPureRepository;
import com.funnyland.funnyland_server.service.handler.DateService;
import com.funnyland.funnyland_server.service.handler.RSAHandlerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InsertService {
    @Autowired
    DateService dateService;

    @Autowired
    RSAHandlerService rsaService;

    @Autowired
    CounselingPureRepository counselingPureRepository;

    @Autowired
    BannerPureRepository bannerPureRepository;

    @Autowired
    ProductCategoryPureRepository productCategoryPureRepository;

    @Autowired
    ProductPureRepository productPureRepository;

    @Autowired
    VideoPureRepository videoPureRepository;

    @Autowired
    StoreAreaPureRepository storeAreaPureRepository;

    @Autowired
    StorePureRepository storePureRepository;

    public void insertCounselingOneService(CounselingReqDto dto) {
        counselingPureRepository.save(getCounselingEntityByDto(dto));
    }

    private CounselingPureEntity getCounselingEntityByDto(CounselingReqDto dto){
        CounselingPureEntity entity = new CounselingPureEntity();
        entity.setCounselingType(dto.getCounselingType());
        entity.setCounselingApplier(dto.getApplierName()==null?"":rsaService.getEncryptRsaStr(dto.getApplierName()));
        entity.setCounselingPhone(dto.getApplierPhone()==null?"":rsaService.getEncryptRsaStr(dto.getApplierPhone()));
        entity.setCounselingArea(dto.getApplierArea()==null?"":dto.getApplierArea());
        entity.setCounselingAddress(dto.getAddress()==null?"":dto.getAddress());
        entity.setCounselingFloor(dto.getFloor()==null?"":dto.getFloor());
        entity.setCounselingDesc(dto.getDesc()==null?"":dto.getDesc());
        entity.setCounselingPrivacyAgreement(dto.getPrivacyAgreement());
        entity.setCounselingOpenDate(dto.getOpenDate()==null?null:dto.getOpenDate());
        entity.setCounselingCreated(dateService.getCurrentDate());
        entity.setCounselingUpdated(dateService.getCurrentDate());
        return entity;
    }

    private CounselingReqDto getCounselingDtoByEntity(CounselingPureEntity entity){
        CounselingReqDto dto = new CounselingReqDto();
        dto.setCounselingType(entity.getCounselingType());
        dto.setAddress(rsaService.getDecryptRsaStr(entity.getCounselingAddress()));
        dto.setApplierArea(entity.getCounselingArea());
        dto.setApplierName(rsaService.getDecryptRsaStr(entity.getCounselingApplier()));
        dto.setApplierPhone(rsaService.getDecryptRsaStr(entity.getCounselingPhone()));
        dto.setDesc(rsaService.getDecryptRsaStr(entity.getCounselingDesc()));
        dto.setFloor(entity.getCounselingFloor());
        dto.setPrivacyAgreement(entity.getCounselingPrivacyAgreement());
        dto.setOpenDate(entity.getCounselingOpenDate());
        return dto;
    }

    public void insertBannersService(List<BannerReqDto> dtos){
        
        bannerPureRepository.saveAll(getBannerEntitiesByDtos(dtos));
    }

    private List<BannerPureEntity> getBannerEntitiesByDtos(List<BannerReqDto> dtos){
        List<BannerPureEntity> entities = new ArrayList<>();
        for(BannerReqDto dto:dtos){
            BannerPureEntity entity = new BannerPureEntity();
            entity.setBannerImageUrl(dto.getImageUrl());
            entity.setBannerCreated(dateService.getCurrentDate());
            entity.setBannerUpdated(dateService.getCurrentDate());
            entity.setBannerOrder(-1);
            entities.add(entity);
        }

        return entities;
    }

	public void insertProductCategoryOneService(ProductCategoryGetDto dto) {
        productCategoryPureRepository.save(getProductCategoryEntityByDto(dto));
	}

    private ProductCategoryPureEntity getProductCategoryEntityByDto(ProductCategoryGetDto dto){
        ProductCategoryPureEntity entity = new ProductCategoryPureEntity();
        entity.setProductCategoryName(dto.getCategoryName());
        entity.setProductCategoryPriority(dto.getPriority());
        return entity;
    }

	public void insertProductOneService(ProductGetDto dto) {
        productPureRepository.save(getProductPureEntityByDto(dto));
	}

    private ProductPureEntity getProductPureEntityByDto(ProductGetDto dto){
        ProductPureEntity entity = new ProductPureEntity();
        entity.setProductCategoryId(dto.getCategoryId());
        entity.setProductName(dto.getName());
        entity.setProductPriority(dto.getPriority());
        entity.setProductIntroduce(dto.getIntroduce());
        entity.setProductSummary(dto.getSummary());
        entity.setProductDesc(dto.getDesc());
        entity.setProductImageUrl(dto.getImageUrl());
        entity.setProductNewChecked(dto.isNewChecked()?1:0);
        entity.setProductHitChecked(dto.isHitChecked()?1:0);
        entity.setProductEventChecked(dto.isEventChecked()?1:0);
        entity.setProductHide(dto.getHide());
        entity.setProductCreated(dateService.getCurrentDate());
        entity.setProductUpdated(dateService.getCurrentDate());
        return entity;
    }

	public void insertVideoOneService(VideoGetDto dto) {
        VideoPureEntity entity = getVideoPureEntityByDto(dto);
        videoPureRepository.save(entity);
	}

    private VideoPureEntity getVideoPureEntityByDto(VideoGetDto dto){
        VideoPureEntity entity = new VideoPureEntity();
        entity.setVideoName(dto.getVideoName());
        entity.setVideoType(dto.getVideoType());
        entity.setVideoUrl(dto.getVideoUrl());
        entity.setVideoKey(dto.getVideoKey());
        entity.setVideoCreated(dateService.getCurrentDate());
        entity.setVideoUpdated(dateService.getCurrentDate());
        return entity;
    }

	public void insertStoreAreaOneService(StoreAreaGetDto dto) {
        storeAreaPureRepository.save(getStoreAreaEntityByDto(dto));
	}

    private StoreAreaPureEntity getStoreAreaEntityByDto(StoreAreaGetDto dto){
        StoreAreaPureEntity entity = new StoreAreaPureEntity();
        entity.setStoreAreaName(dto.getAreaName());
        entity.setStoreAreaCreated(dateService.getCurrentDate());
        entity.setStoreAreaUpdated(dateService.getCurrentDate());
        return entity;
    }

	public void insertStoreOneService(StoreGetDto dto) {
        storePureRepository.save(getStoreEntityByDto(dto));
	}

    private StorePureEntity getStoreEntityByDto(StoreGetDto dto){
        StorePureEntity entity = new StorePureEntity();
        entity.setStoreArea(dto.getStoreArea());
        entity.setStoreName(dto.getStoreName());
        entity.setStoreAddress(dto.getStoreAddress());
        entity.setStorePhone(dto.getStorePhone());
        entity.setStoreDesc(dto.getStoreDesc());
        entity.setStoreImageUrl(dto.getStoreImageUrl());
        entity.setStoreLat(dto.getStoreLat());
        entity.setStoreLng(dto.getStoreLng());
        entity.setStoreCreated(dateService.getCurrentDate());
        entity.setStoreUpdated(dateService.getCurrentDate());
        return entity;
    }
}
