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
import com.funnyland.funnyland_server.model.cs.dto.CsGetDto;
import com.funnyland.funnyland_server.model.cs.entity.CsPureEntity;
import com.funnyland.funnyland_server.model.cs.repository.CsPureRepository;
import com.funnyland.funnyland_server.model.popup.dto.PopupGetDto;
import com.funnyland.funnyland_server.model.popup.entity.PopupPureEntity;
import com.funnyland.funnyland_server.model.popup.repository.PopupPureRepository;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.product.dto.ProductJCategoryGetDto;
import com.funnyland.funnyland_server.model.product.projection.ProductJProductCategoryProj;
import com.funnyland.funnyland_server.model.product.repository.ProductPureRepository;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.product_category.entity.ProductCategoryPureEntity;
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

    public List<ProductJCategoryGetDto> searchProductAllService(String categoryIdStr, int pageIndex, int displaySize, boolean newChecked, boolean hitChecked, boolean eventChecked) {
        List<ProductJProductCategoryProj> projs = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageIndex, displaySize);

        int[] newCheckedInt = newChecked ? new int[]{1} : new int[]{0,1};
        int[] hitCheckedInt = hitChecked ? new int[]{1} : new int[]{0,1};
        int[] eventCheckedInt = eventChecked ? new int[]{1} : new int[]{0,1};

        if (categoryIdStr == null) {
            projs = productPureRepository.selectAllConProductCategory(pageable, newCheckedInt, hitCheckedInt, eventCheckedInt);
        } else {
            int categoryId = -1;
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (NumberFormatException e) {
                // System.out.println(e);
            }
            if (categoryId == -1) {
                projs = productPureRepository.selectAllConProductCategory(pageable, newCheckedInt, hitCheckedInt, eventCheckedInt);
            } else {
                projs = productPureRepository.selectSomeConProductCategoryByCategoryIdAndPageIndex(categoryId,pageable, newCheckedInt, hitCheckedInt, eventCheckedInt);
            }

        }
        List<ProductJCategoryGetDto> dtos = getProductJCategoryDtosByProjs(projs);

        return dtos;
    }

    public int searchCountForProductExistService(String categoryIdStr, boolean newChecked, boolean hitChecked, boolean eventChecked) {
        int categoryId = -1;
        int[] newCheckedInt = newChecked ? new int[]{1} : new int[]{0,1};
        int[] hitCheckedInt = hitChecked ? new int[]{1} : new int[]{0,1};
        int[] eventCheckedInt = eventChecked ? new int[]{1} : new int[]{0,1};
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {

        }
        if (categoryId == -1) {
            return productPureRepository.countExistAll(newCheckedInt,hitCheckedInt,eventCheckedInt);
        }
        return productPureRepository.countExistAllByCategoryId(categoryId,newCheckedInt,hitCheckedInt,eventCheckedInt);
    }

    private List<ProductJCategoryGetDto> getProductJCategoryDtosByProjs(List<ProductJProductCategoryProj> projs) {
        List<ProductJCategoryGetDto> pjcDtos = new ArrayList<>();
        for (ProductJProductCategoryProj proj : projs) {
            ProductJCategoryGetDto pjcDto = new ProductJCategoryGetDto();
            ProductGetDto pDto = new ProductGetDto();
            ProductCategoryGetDto pcDto = new ProductCategoryGetDto();

            pDto.setId(proj.getProduct().getProductId());
            pDto.setCategoryId(proj.getProduct().getProductCategoryId());
            pDto.setName(proj.getProduct().getProductName());
            pDto.setIntroduce(proj.getProduct().getProductIntroduce());
            pDto.setPriority(proj.getProduct().getProductPriority());
            pDto.setDesc(proj.getProduct().getProductDesc());
            pDto.setImageUrl(proj.getProduct().getProductImageUrl());
            pDto.setSummary(proj.getProduct().getProductSummary());
            pDto.setNewChecked(proj.getProduct().getProductNewChecked()==1 ? true : false);
            pDto.setHitChecked(proj.getProduct().getProductHitChecked()==1 ? true : false);
            pDto.setEventChecked(proj.getProduct().getProductEventChecked()==1 ? true: false);
            pDto.setHide(proj.getProduct().getProductHide());

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

        pDto.setId(proj.getProduct().getProductId());
        pDto.setCategoryId(proj.getProduct().getProductCategoryId());
        pDto.setName(proj.getProduct().getProductName());
        pDto.setIntroduce(proj.getProduct().getProductIntroduce());
        pDto.setDesc(proj.getProduct().getProductDesc());
        pDto.setImageUrl(proj.getProduct().getProductImageUrl());
        pDto.setPriority(proj.getProduct().getProductPriority());
        pDto.setSummary(proj.getProduct().getProductSummary());
        pDto.setNewChecked(proj.getProduct().getProductNewChecked()==1 ? true : false);
        pDto.setHitChecked(proj.getProduct().getProductHitChecked()==1 ? true : false);
        pDto.setEventChecked(proj.getProduct().getProductEventChecked()==1 ? true : false);
        pDto.setHide(proj.getProduct().getProductHide());

        pcDto.setId(proj.getCategory().getProductCategoryId());
        pcDto.setCategoryName(proj.getCategory().getProductCategoryName());
        pcDto.setPriority(proj.getCategory().getProductCategoryPriority());

        dto.setProduct(pDto);
        dto.setCategory(pcDto);
        return dto;
    }

	public List<VideoGetDto> searchVideoAllService() {
        List<VideoPureEntity> entities = videoPureRepository.findAll();
		return getVideoDtosByEntities(entities);
	}

    private List<VideoGetDto> getVideoDtosByEntities(List<VideoPureEntity> entities){
        List<VideoGetDto> dtos = new ArrayList<>();
        for(VideoPureEntity entity : entities){
            VideoGetDto dto = new VideoGetDto();
            dto.setVideoId(entity.getVideoId());
            dto.setVideoName(entity.getVideoName());
            dto.setVideoType(entity.getVideoType());
            dto.setVideoKey(entity.getVideoKey());
            dto.setVideoUrl(entity.getVideoUrl());
            dto.setVideoDisplay(entity.getVideoDisplay());
            dto.setVideoCreated(entity.getVideoCreated());
            dto.setVideoUpdated(entity.getVideoUpdated());
            dtos.add(dto);
        }
        return dtos;

    }

	public List<StoreAreaGetDto> searchStoreAreaAllService() {
        List<StoreAreaPureEntity> entities = storeAreaPureRepository.selectAllByExist();
		return getStoreAreaDtosByEntities(entities);
	}

    private List<StoreAreaGetDto> getStoreAreaDtosByEntities(List<StoreAreaPureEntity> entities){
        List<StoreAreaGetDto> dtos = new ArrayList<>();
        for(StoreAreaPureEntity entity : entities){
            StoreAreaGetDto dto = new StoreAreaGetDto();
            dto.setAreaId(entity.getStoreAreaId());
            dto.setAreaName(entity.getStoreAreaName());
            dto.setAreaCreated(entity.getStoreAreaCreated());
            dto.setAreaUpdated(entity.getStoreAreaUpdated());
            dtos.add(dto);
        }
        return dtos;
    }

	public List<StoreGetDto> searchStoreAllService(String areaName, int pageIndex, int displaySize) {
        Pageable pageable = PageRequest.of(pageIndex, displaySize);
		return getStoreDtosByEntities(storePureRepository.selectAllByExist(areaName, pageable));
	}

    private List<StoreGetDto> getStoreDtosByEntities(List<StorePureEntity> entities){
        List<StoreGetDto> dtos = new ArrayList<>();
        for(StorePureEntity entity : entities){
            StoreGetDto dto = new StoreGetDto();
            dto.setStoreId(entity.getStoreId());
            dto.setStoreArea(entity.getStoreArea());
            dto.setStoreName(entity.getStoreName());
            dto.setStoreAddress(entity.getStoreAddress());
            dto.setStorePhone(entity.getStorePhone());
            dto.setStoreDesc(entity.getStoreDesc());
            dto.setStoreImageUrl(entity.getStoreImageUrl());
            dto.setStoreLat(entity.getStoreLat());
            dto.setStoreLng(entity.getStoreLng());
            dto.setStoreCreated(entity.getStoreCreated());
            dto.setStoreUpdated(entity.getStoreUpdated());
            dtos.add(dto);
        }
        return dtos;
    }

	public int searchCountForStoreExistService(String areaName) {
        return storePureRepository.countExistAllByAreaName(areaName);
	}

	public StoreGetDto searchStoreOneService(int storeId) {
        Optional<StorePureEntity> entityOpt = storePureRepository.selectOneByStoreId(storeId);
        if(entityOpt.isPresent()){
            StoreGetDto dto = getStoreDtoByEntity(entityOpt.get());
            return dto;
        }else{
            return null;
        }
	}

    private StoreGetDto getStoreDtoByEntity(StorePureEntity entity){
        StoreGetDto dto = new StoreGetDto();
        dto.setStoreId(entity.getStoreId());
        dto.setStoreArea(entity.getStoreArea());
        dto.setStoreName(entity.getStoreName());
        dto.setStoreAddress(entity.getStoreAddress());
        dto.setStorePhone(entity.getStorePhone());
        dto.setStoreDesc(entity.getStoreDesc());
        dto.setStoreImageUrl(entity.getStoreImageUrl());
        dto.setStoreLat(entity.getStoreLat());
        dto.setStoreLng(entity.getStoreLng());
        dto.setStoreCreated(entity.getStoreCreated());
        dto.setStoreUpdated(entity.getStoreUpdated());
        return dto;
    }

	public List<CsGetDto> searchCsAllService(String csType, int pageIndex, int displaySize) {
        Pageable pageable = PageRequest.of(pageIndex, displaySize);
		return getCsDtosByEntities(csPureRepository.selectAllByExist(csType, pageable));
	}

    private List<CsGetDto> getCsDtosByEntities(List<CsPureEntity> entities){
        List<CsGetDto> dtos = new ArrayList<>();
        for(CsPureEntity entity : entities){
            CsGetDto dto = new CsGetDto();
            dto.setCsId(entity.getCsId());
            dto.setCsType(entity.getCsType());
            dto.setCsTitle(entity.getCsTitle());
            dto.setCsDesc(entity.getCsDesc());
            dto.setCsAuthor(entity.getCsAuthor());
            dto.setCsImportantChecked(entity.isCsImportantChecked());
            dto.setCsCreated(entity.getCsCreated());
            dto.setCsUpdated(entity.getCsUpdated());
            dto.setCsViewCount(entity.getCsViewCount());
            dtos.add(dto);
        }
        return dtos;
    }

	public int searchCountForCsExistService(String csType) {
		return csPureRepository.countExistAllByCsType(csType);
	}

    public CsGetDto searchCsOneService(int csId) {
        Optional<CsPureEntity> entityOpt = csPureRepository.selectOneByCsId(csId);
        if(entityOpt.isPresent()){
            CsGetDto dto = getCsDtoByEntity(entityOpt.get());
            return dto;
        }else{
            return null;
        }
    }

    private CsGetDto getCsDtoByEntity(CsPureEntity entity){
        CsGetDto dto = new CsGetDto();
        dto.setCsId(entity.getCsId());
        dto.setCsType(entity.getCsType());
        dto.setCsTitle(entity.getCsTitle());
        dto.setCsAuthor(entity.getCsAuthor());
        dto.setCsDesc(entity.getCsDesc());
        dto.setCsImportantChecked(entity.isCsImportantChecked());
        dto.setCsViewCount(entity.getCsViewCount());
        dto.setCsCreated(entity.getCsCreated());
        dto.setCsUpdated(entity.getCsUpdated());
        return dto;
    }

    public List<PopupGetDto> searchPopupAllService() {
        List<PopupPureEntity> entities = popupPureRepository.findAll();

        return getPopupDtosByEntities(entities);
    }

    private List<PopupGetDto> getPopupDtosByEntities(List<PopupPureEntity> entities){
        List<PopupGetDto> dtos = new ArrayList<>();
        for(PopupPureEntity entity : entities){
            PopupGetDto dto = new PopupGetDto();
            dto.setPopupId(entity.getPopupId());
            dto.setPopupName(entity.getPopupName());
            dto.setPopupImageUrl(entity.getPopupImageUrl());
            dto.setPopupUrl(entity.getPopupUrl());
            dto.setPopupUpdated(entity.getPopupUpdated());
            dto.setPopupCreated(entity.getPopupCreated());
            dtos.add(dto);
        }
        return dtos;
    }
}
