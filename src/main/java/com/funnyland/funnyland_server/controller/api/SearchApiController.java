package com.funnyland.funnyland_server.controller.api;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.banner.dto.BannerResDto;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingResDto;
import com.funnyland.funnyland_server.model.message.Message;
import com.funnyland.funnyland_server.model.message.StatusEnum;
import com.funnyland.funnyland_server.model.page.PageDto;
import com.funnyland.funnyland_server.model.product.dto.ProductJCategoryGetDto;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.user.vo.UserInfoVO;
import com.funnyland.funnyland_server.service.SearchService;
import com.funnyland.funnyland_server.service.user.UserAuthService;
import com.funnyland.funnyland_server.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public BannerResDto SearchBannerAllByorderApi(){
        BannerResDto resDto = new BannerResDto();
        List<BannerGetDto> getDtos = searchService.searchBannerAllByorderService();
        resDto.setMessage("success");
        resDto.setData(getDtos);
        resDto.setSize(getDtos.size());

        return resDto;
    }

    // /api/search/counseling/all
    @GetMapping("/counseling/all")
    public CounselingResDto SearchCounselingAllApi(HttpServletRequest request, @RequestParam("pageIndex") int pageIndex){
        CounselingResDto resDto = new CounselingResDto();
        UserInfoVO user = userService.getUserInfo(request);

        if(user == null){
            resDto.setMessage("user_invalid");
        }else if(!user.getRole().equals("ROLE_ADMIN")){
            resDto.setMessage("not_auth");
        }else{
            int displaySize = 30;
            int itemSize = searchService.searchCounselingAllCountService();
            List<CounselingGetDto> data = searchService.searchCounselingAllService(pageIndex,displaySize);

            PageDto page = new PageDto();
            page.setItemSize(itemSize);
            page.setDisplaySize(displaySize);
            page.setPageSize((int) Math.ceil((double) itemSize / (double) displaySize));
            page.setCurr(pageIndex+1);
            page.setPrev(pageIndex==0 ? 1 : pageIndex);
            page.setNext(pageIndex+1 >= page.getPageSize()?page.getPageSize():page.getCurr()+1);

            resDto.setMessage("success");
            resDto.setData(data);
            resDto.setPage(page);
        }
        return resDto;
    }

    // /api/search/product_category/all
    @GetMapping("/product_category/all")
    public ResponseEntity<Message> SearchProductCategoryAllApi(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        List<ProductCategoryGetDto> resData = new ArrayList<>();
        try{
            resData = searchService.searchProductCategoryAllService();
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/search/product/all
    @GetMapping("/product/all")
    public ResponseEntity<Message> SearchProductAllApi(HttpServletRequest request, @RequestParam(value = "categoryId", required = false) String categoryIdStr, @RequestParam(value = "pageIndex", required = false) String pageIndexStr){
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        List<ProductJCategoryGetDto> resData = new ArrayList<>();
        int displaySize = 30;
        int itemSize = 0;
        int pageIndex = 0;

        try{
            pageIndex = Integer.parseInt(pageIndexStr);
        }catch(NumberFormatException e){
            System.out.println(e);
        }

        try{
            itemSize = searchService.searchCountForProductExistService(categoryIdStr);
            resData = searchService.searchProductAllService(categoryIdStr, pageIndex, displaySize);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // System.out.println(itemSize);
        PageDto page = new PageDto();
        page.setItemSize(itemSize);
        page.setDisplaySize(displaySize);
        page.setPageSize((int) Math.ceil((double) itemSize / (double) displaySize));
        page.setCurr(pageIndex+1);
        page.setPrev(pageIndex==0 ? 1 : pageIndex);
        page.setNext(pageIndex+1 >= page.getPageSize()?page.getPageSize():page.getCurr()+1);

        headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        message.setPage(page);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/search/product/one
    @GetMapping("/product/one")
    public ResponseEntity<Message> SearchProductOneApi(HttpServletRequest request, @RequestParam(value = "productId", required = false) String productIdStr){
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        ProductJCategoryGetDto resData = new ProductJCategoryGetDto();
        
        int productId = 0;

        try{
            productId = Integer.parseInt(productIdStr);
        }catch(NumberFormatException e){
            headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage("no_data");
            return new ResponseEntity<>(message,headers,HttpStatus.OK);
        }

        try{
            resData = searchService.searchProductOneService(productId);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // System.out.println(itemSize);

        headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        message.setData(resData);
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }
}
