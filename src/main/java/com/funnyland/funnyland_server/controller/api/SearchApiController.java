package com.funnyland.funnyland_server.controller.api;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.banner.dto.BannerResDto;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingResDto;
import com.funnyland.funnyland_server.model.cs.dto.CsGetDto;
import com.funnyland.funnyland_server.model.message.Message;
import com.funnyland.funnyland_server.model.message.StatusEnum;
import com.funnyland.funnyland_server.model.page.PageDto;
import com.funnyland.funnyland_server.model.popup.dto.PopupGetDto;
import com.funnyland.funnyland_server.model.product.dto.ProductJCategoryGetDto;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.store.dto.StoreGetDto;
import com.funnyland.funnyland_server.model.store_area.dto.StoreAreaGetDto;
import com.funnyland.funnyland_server.model.user.vo.UserInfoVO;
import com.funnyland.funnyland_server.model.video.dto.VideoGetDto;
import com.funnyland.funnyland_server.service.SearchService;
import com.funnyland.funnyland_server.service.user.UserAuthService;
import com.funnyland.funnyland_server.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchApiController {
    @Autowired
    SearchService searchService;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserService userService;

    // /api/search/banner/all/byorder
    @GetMapping("/banner/all/byorder")
    public BannerResDto SearchBannerAllByorderApi() {
        BannerResDto resDto = new BannerResDto();
        List<BannerGetDto> getDtos = searchService.searchBannerAllByorderService();
        resDto.setMessage("success");
        resDto.setData(getDtos);
        resDto.setSize(getDtos.size());

        return resDto;
    }

    // /api/search/counseling/all
    @GetMapping("/counseling/all")
    public CounselingResDto SearchCounselingAllApi(HttpServletRequest request,
            @RequestParam("pageIndex") int pageIndex) {
        CounselingResDto resDto = new CounselingResDto();
        UserInfoVO user = userService.getUserInfo(request);

        if (user == null) {
            resDto.setMessage("user_invalid");
        } else if (!user.getRole().equals("ROLE_ADMIN")) {
            resDto.setMessage("not_auth");
        } else {
            int displaySize = 30;
            int itemSize = searchService.searchCounselingAllCountService();
            List<CounselingGetDto> data = searchService.searchCounselingAllService(pageIndex, displaySize);

            PageDto page = new PageDto();
            page.setItemSize(itemSize);
            page.setDisplaySize(displaySize);
            page.setPageSize((int) Math.ceil((double) itemSize / (double) displaySize));
            page.setCurr(pageIndex + 1);
            page.setPrev(pageIndex == 0 ? 1 : pageIndex);
            page.setNext(pageIndex + 1 >= page.getPageSize() ? page.getPageSize() : page.getCurr() + 1);

            resDto.setMessage("success");
            resDto.setData(data);
            resDto.setPage(page);
        }
        return resDto;
    }

    // /api/search/product_category/all
    @GetMapping("/product_category/all")
    public ResponseEntity<Message> SearchProductCategoryAllApi(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        List<ProductCategoryGetDto> resData = new ArrayList<>();
        try {
            resData = searchService.searchProductCategoryAllService();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/product/all
    @GetMapping("/product/all")
    public ResponseEntity<Message> SearchProductAllApi(HttpServletRequest request,
            @RequestParam(value = "categoryId", required = false) String categoryIdStr,
            @RequestParam(value = "pageIndex", required = false) String pageIndexStr,
            @RequestParam(value = "newChecked", required = false) boolean newChecked,
            @RequestParam(value = "hitChecked", required = false) boolean hitChecked,
            @RequestParam(value = "eventChecked", required = false) boolean eventChecked) {
        // String resourceSrc = request.getServletContext().getRealPath("/upload");
        // System.out.println(resourceSrc);
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        List<ProductJCategoryGetDto> resData = new ArrayList<>();
        int displaySize = 20;
        int itemSize = 0;
        int pageIndex = 0;

        try {
            pageIndex = Integer.parseInt(pageIndexStr);
        } catch (NumberFormatException e) {
            // System.out.println(e);
        }

        try {
            itemSize = searchService.searchCountForProductExistService(categoryIdStr, newChecked, hitChecked,
                    eventChecked);
            resData = searchService.searchProductAllService(categoryIdStr, pageIndex, displaySize, newChecked,
                    hitChecked, eventChecked);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // System.out.println(itemSize);
        PageDto page = new PageDto();
        page.setItemSize(itemSize);
        page.setDisplaySize(displaySize);
        page.setPageSize((int) Math.ceil((double) itemSize / (double) displaySize));
        page.setCurr(pageIndex + 1);
        page.setPrev(pageIndex == 0 ? 1 : pageIndex);
        page.setNext(pageIndex + 1 >= page.getPageSize() ? page.getPageSize() : page.getCurr() + 1);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        message.setPage(page);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/product/one
    @GetMapping("/product/one")
    public ResponseEntity<Message> SearchProductOneApi(HttpServletRequest request,
            @RequestParam(value = "productId", required = false) String productIdStr) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        ProductJCategoryGetDto resData = new ProductJCategoryGetDto();

        int productId = 0;

        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage("no_data");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }

        try {
            resData = searchService.searchProductOneService(productId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // System.out.println(itemSize);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/video/all
    @GetMapping("/video/all")
    public ResponseEntity<Message> SearchVideoAllApi(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        List<VideoGetDto> resData = new ArrayList<>();
        try {
            resData = searchService.searchVideoAllService();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/store_area/all
    @GetMapping("/store_area/all")
    public ResponseEntity<Message> SearchStoreAreaAllApi(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        List<StoreAreaGetDto> resData = new ArrayList<>();
        try {
            resData = searchService.searchStoreAreaAllService();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/store/all
    @GetMapping("/store/all")
    public ResponseEntity<Message> SearchStoreAllApi(HttpServletRequest request,
            @RequestParam(value = "areaName", required = false) String areaName,
            @RequestParam(value = "pageIndex", required = false) String pageIndexStr) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        List<StoreGetDto> resData = new ArrayList<>();
        int displaySize = 30;
        int itemSize = 0;
        int pageIndex = 0;

        try {
            pageIndex = Integer.parseInt(pageIndexStr);
        } catch (NumberFormatException e) {
            // System.out.println(e);
        }
        try {
            itemSize = searchService.searchCountForStoreExistService(areaName);
            resData = searchService.searchStoreAllService(areaName, pageIndex, displaySize);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PageDto page = new PageDto();
        page.setItemSize(itemSize);
        page.setDisplaySize(displaySize);
        page.setPageSize((int) Math.ceil((double) itemSize / (double) displaySize));
        page.setCurr(pageIndex + 1);
        page.setPrev(pageIndex == 0 ? 1 : pageIndex);
        page.setNext(pageIndex + 1 >= page.getPageSize() ? page.getPageSize() : page.getCurr() + 1);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        message.setPage(page);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/store/one
    @GetMapping("/store/one")
    public ResponseEntity<Message> SearchStoreOneApi(HttpServletRequest request,
            @RequestParam(value = "storeId", required = false) String storeIdStr) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        StoreGetDto resData = new StoreGetDto();

        int storeId = 0;

        try {
            storeId = Integer.parseInt(storeIdStr);
        } catch (NumberFormatException e) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage("no_data");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }

        try {
            resData = searchService.searchStoreOneService(storeId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // System.out.println(itemSize);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/cs/all
    @GetMapping("/cs/all")
    public ResponseEntity<Message> SearchCsAllApi(HttpServletRequest request,
            @RequestParam(value = "csType", required = false, defaultValue = "") String csType,
            @RequestParam(value = "pageIndex", required = false) String pageIndexStr) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        List<CsGetDto> resData = new ArrayList<>();

        int displaySize = 30;
        int itemSize = 0;
        int pageIndex = 0;

        try {
            pageIndex = Integer.parseInt(pageIndexStr);
        } catch (NumberFormatException e) {
            // System.out.println(e);
        }
        try {
            resData = searchService.searchCsAllService(csType, pageIndex, displaySize);
            itemSize = searchService.searchCountForCsExistService(csType);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PageDto page = new PageDto();
        page.setItemSize(itemSize);
        page.setDisplaySize(displaySize);
        page.setPageSize((int) Math.ceil((double) itemSize / (double) displaySize));
        page.setCurr(pageIndex + 1);
        page.setPrev(pageIndex == 0 ? 1 : pageIndex);
        page.setNext(pageIndex + 1 >= page.getPageSize() ? page.getPageSize() : page.getCurr() + 1);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        message.setPage(page);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/cs/one
    @GetMapping("/cs/one")
    public ResponseEntity<Message> SearchCsOneApi(HttpServletRequest request,
            @RequestParam(value = "csId", required = false) String csIdStr) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        CsGetDto resData = new CsGetDto();

        int csId = 0;

        try {
            csId = Integer.parseInt(csIdStr);
        } catch (NumberFormatException e) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage("no_data");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }

        try {
            resData = searchService.searchCsOneService(csId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // System.out.println(itemSize);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // /api/search/popup/all
    @GetMapping("/popup/all")
    public ResponseEntity<Message> SearchPopupAllApi(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        List<PopupGetDto> resData = new ArrayList<>();
        try {
            resData = searchService.searchPopupAllService();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
