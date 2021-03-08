package com.funnyland.funnyland_server.controller.api;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.cs.dto.CsGetDto;
import com.funnyland.funnyland_server.model.message.Message;
import com.funnyland.funnyland_server.model.message.StatusEnum;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.store.dto.StoreGetDto;
import com.funnyland.funnyland_server.model.store_area.dto.StoreAreaGetDto;
import com.funnyland.funnyland_server.model.user.vo.UserInfoVO;
import com.funnyland.funnyland_server.model.video.dto.VideoGetDto;
import com.funnyland.funnyland_server.service.UpdateService;
import com.funnyland.funnyland_server.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/update")
public class UpdateApiController {
    @Autowired
    UpdateService updateService;

    @Autowired
    UserService userService;

    // /api/update/banner/all
    @PostMapping("/banner/all")
    public String UpdateBannerAllApi(@RequestBody List<BannerGetDto> dtos){
        updateService.updateBannersAllService(dtos);
        return "{\"message\":\"success\"}";
    }

    // /api/update/counseling/one
    @PostMapping("/counseling/one")
    public String UpdateCounselingOneApi(@RequestBody CounselingGetDto dto){
        updateService.updateCounselingOneService(dto);
        return "{\"message\":\"success\"}";
    }

    // /api/update/product/one
    @PostMapping("/product/one")
    public ResponseEntity<Message> UpdateProductOneRequest(HttpServletRequest request, @RequestBody ProductGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();

        try{
            UserInfoVO user = userService.getUserInfo(request);
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            updateService.updateProductOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/update/category/one
    @PostMapping("/category/one")
    public ResponseEntity<Message> UpdateCategoryOneRequestApi(HttpServletRequest request, @RequestBody ProductCategoryGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();

        try{
            UserInfoVO user = userService.getUserInfo(request);
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            updateService.updateCategoryOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/update/video/one/display
    @PostMapping("/video/one/display")
    public ResponseEntity<Message> UpdateVideoOneDisplayRequestApi(HttpServletRequest request, @RequestBody VideoGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();

        try{
            UserInfoVO user = userService.getUserInfo(request);
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            updateService.updateVideoOneDisplayService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/update/store_area/one
    @PostMapping("/store_area/one")
    public ResponseEntity<Message> UpdateStoreAreaOneRequestApi(HttpServletRequest request, @RequestBody StoreAreaGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();

        try{
            UserInfoVO user = userService.getUserInfo(request);
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            updateService.updateStoreAreaOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/update/store/one
    @PostMapping("/store/one")
    public ResponseEntity<Message> UpdateStoreOneRequestApi(HttpServletRequest request, @RequestBody StoreGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();

        try{
            UserInfoVO user = userService.getUserInfo(request);
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            updateService.updateStoreOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/update/cs/one
    @PostMapping("/cs/one")
    public ResponseEntity<Message> UpdateCsOneRequestApi(HttpServletRequest request, @RequestBody CsGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();

        try{
            UserInfoVO user = userService.getUserInfo(request);
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            updateService.updateCsOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }
}
