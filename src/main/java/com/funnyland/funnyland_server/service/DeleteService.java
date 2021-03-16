package com.funnyland.funnyland_server.service;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.banner.repository.BannerPureRepository;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.counseling.repository.CounselingPureRepository;
import com.funnyland.funnyland_server.model.cs.dto.CsGetDto;
import com.funnyland.funnyland_server.model.cs.repository.CsPureRepository;
import com.funnyland.funnyland_server.model.popup.dto.PopupGetDto;
import com.funnyland.funnyland_server.model.popup.repository.PopupPureRepository;
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

    @Autowired
    VideoPureRepository videoPureRepository;

    @Autowired
    StoreAreaPureRepository storeAreaPureRepository;

    @Autowired
    StorePureRepository storePureRepository;

    @Autowired
    CsPureRepository csPureRepository;

    @Autowired
    PopupPureRepository popupPureRepository;

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
	public void deleteVideoOneService(VideoGetDto dto) {
        videoPureRepository.deleteById(dto.getVideoId());
	}
	public void deleteStoreAreaOneService(StoreAreaGetDto dto) {
        storeAreaPureRepository.findById(dto.getAreaId()).ifPresent(r->{
            r.setStoreAreaDeleted(EXIST_OR_NOT.IS_DELETED);
            r.setStoreAreaUpdated(dateService.getCurrentDate());
            storeAreaPureRepository.save(r);
        });
	}
	public void deleteStoreOneService(StoreGetDto dto) {
        storePureRepository.deleteById(dto.getStoreId());
	}
	public void deleteCsOneService(CsGetDto dto) {
        csPureRepository.deleteById((dto.getCsId()));
	}
    public void deletePopupOneService(PopupGetDto dto) {
        popupPureRepository.deleteById(dto.getPopupId());
    }
}
