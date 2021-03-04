package com.funnyland.funnyland_server.service;

import java.util.ArrayList;
import java.util.List;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.banner.repository.BannerPureRepository;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.counseling.entity.CounselingPureEntity;
import com.funnyland.funnyland_server.model.counseling.repository.CounselingPureRepository;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.product.repository.ProductPureRepository;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.product_category.repository.ProductCategoryPureRepository;
import com.funnyland.funnyland_server.model.store.dto.StoreGetDto;
import com.funnyland.funnyland_server.model.store.repository.StorePureRepository;
import com.funnyland.funnyland_server.model.store_area.dto.StoreAreaGetDto;
import com.funnyland.funnyland_server.model.store_area.repository.StoreAreaPureRepository;
import com.funnyland.funnyland_server.model.video.dto.VideoGetDto;
import com.funnyland.funnyland_server.model.video.repository.VideoPureRepository;
import com.funnyland.funnyland_server.service.handler.DateService;
import com.funnyland.funnyland_server.service.handler.RSAHandlerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
    @Autowired
    RSAHandlerService rsaService;

    @Autowired
    DateService dateService;
    
    @Autowired
    BannerPureRepository bannerPureRepository;

    @Autowired
    CounselingPureRepository counselingPureRepository;

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

    public void updateBannersAllService(List<BannerGetDto> dtos){
        
        for(BannerGetDto dto : dtos){
            // bannerPureRepository.findById(dto.getId()).ifPresent(r->{
            //     r.setBannerOrder(dto.getOrder());
            //     bannerPureRepository.save(r);
            // });
            bannerPureRepository.updateBannerOrder(dto.getOrder(), dto.getId());
        }
    }

    public void updateCounselingOneService(CounselingGetDto dto){
        counselingPureRepository.findById(dto.getId()).ifPresent(r->{
            r.setCounselingType(dto.getCounselingType());
            r.setCounselingApplier(dto.getApplierName()==null?"":rsaService.getEncryptRsaStr(dto.getApplierName()));
            r.setCounselingPhone(dto.getApplierPhone()==null?"":rsaService.getEncryptRsaStr(dto.getApplierPhone()));
            r.setCounselingArea(dto.getApplierArea()==null?"":dto.getApplierArea());
            r.setCounselingAddress(dto.getAddress()==null?"":dto.getAddress());
            r.setCounselingFloor(dto.getFloor()==null?"":dto.getFloor());
            r.setCounselingDesc(dto.getDesc()==null?"":dto.getDesc());
            r.setCounselingOpenDate(dto.getOpenDate()==null?null:dto.getOpenDate());
            r.setCounselingAdminChecked(dto.getAdminChecked());
            r.setCounselingUpdated(dateService.getCurrentDate());
            counselingPureRepository.save(r);
        });
    }

	public void updateProductOneService(ProductGetDto dto) {
        productPureRepository.findById(dto.getId()).ifPresent(r->{
            r.setProductCategoryId(dto.getCategoryId());
            r.setProductName(dto.getName());
            r.setProductPriority(dto.getPriority());
            r.setProductIntroduce(dto.getIntroduce());
            r.setProductSummary(dto.getSummary());
            r.setProductDesc(dto.getDesc());
            r.setProductImageUrl(dto.getImageUrl());
            r.setProductNewChecked(dto.isNewChecked()? 1 : 0);
            r.setProductHitChecked(dto.isHitChecked()? 1 : 0);
            r.setProductEventChecked(dto.isEventChecked() ? 1 : 0);
            r.setProductHide(dto.getHide());
            r.setProductUpdated(dateService.getCurrentDate());
            productPureRepository.save(r);
        });
	}

	public void updateCategoryOneService(ProductCategoryGetDto dto) {
        productCategoryPureRepository.findById(dto.getId()).ifPresent(r->{
            r.setProductCategoryName(dto.getCategoryName());
            r.setProductCategoryPriority(dto.getPriority());
            productCategoryPureRepository.save(r);
        });
	}

	public void updateVideoOneDisplayService(VideoGetDto dto) {
        videoPureRepository.updateDisplaySetClear();
        videoPureRepository.findById(dto.getVideoId()).ifPresent(r->{
            r.setVideoDisplay(dto.getVideoDisplay());
            videoPureRepository.save(r);
        });
	}

	public void updateStoreAreaOneService(StoreAreaGetDto dto) {
        storeAreaPureRepository.findById(dto.getAreaId()).ifPresent(r->{
            r.setStoreAreaName(dto.getAreaName());
            r.setStoreAreaUpdated(dateService.getCurrentDate());
            storeAreaPureRepository.save(r);
        });
	}

	public void updateStoreOneService(StoreGetDto dto) {
        storePureRepository.findById(dto.getStoreId()).ifPresent(r->{
            r.setStoreName(dto.getStoreName());
            r.setStoreArea(dto.getStoreArea());
            r.setStoreAddress(dto.getStoreAddress());
            r.setStorePhone(dto.getStorePhone());
            r.setStoreDesc(dto.getStoreDesc());
            r.setStoreImageUrl(dto.getStoreImageUrl());
            r.setStoreLat(dto.getStoreLat());
            r.setStoreLng(dto.getStoreLng());
            r.setStoreUpdated(dateService.getCurrentDate());
            storePureRepository.save(r);
        });
	}
}
