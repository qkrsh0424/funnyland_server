package com.funnyland.funnyland_server.service;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.banner.repository.BannerPureRepository;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.counseling.repository.CounselingPureRepository;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.product.repository.ProductPureRepository;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.product_category.repository.ProductCategoryPureRepository;
import com.funnyland.funnyland_server.service.handler.DateService;
import com.funnyland.funnyland_server.service.handler.EXIST_OR_NOT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {
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

    public void deleteBannerOneService(BannerGetDto dto){
        bannerPureRepository.findById(dto.getId()).ifPresent(r->{
            r.setBannerDeleted(EXIST_OR_NOT.IS_DELETED);
            bannerPureRepository.save(r);
        });

    }
	public void deleteCounselingOneService(CounselingGetDto dto) {
        counselingPureRepository.findById(dto.getId()).ifPresent(r->{
            r.setCounselingDeleted(EXIST_OR_NOT.IS_DELETED);
            r.setCounselingUpdated(dateService.getCurrentDate());
            counselingPureRepository.save(r);
        });
	}
	public void deleteProductCategoryOneService(ProductCategoryGetDto dto) {
        productCategoryPureRepository.findById(dto.getId()).ifPresent(r->{
            r.setProductCategoryDeleted(EXIST_OR_NOT.IS_DELETED);
            productCategoryPureRepository.save(r);
        });
	}
	public void deleteProductOneService(ProductGetDto dto) {
        productPureRepository.findById(dto.getId()).ifPresent(r->{
            r.setProductDeleted(EXIST_OR_NOT.IS_DELETED);
            productPureRepository.save(r);
        });
	}
}
