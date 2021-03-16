package com.funnyland.funnyland_server.controller.api;

import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.cs.dto.CsGetDto;
import com.funnyland.funnyland_server.model.message.Message;
import com.funnyland.funnyland_server.model.message.StatusEnum;
import com.funnyland.funnyland_server.model.popup.dto.PopupGetDto;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.store.dto.StoreGetDto;
import com.funnyland.funnyland_server.model.store_area.dto.StoreAreaGetDto;
import com.funnyland.funnyland_server.model.user.vo.UserInfoVO;
import com.funnyland.funnyland_server.model.video.dto.VideoGetDto;
import com.funnyland.funnyland_server.service.DeleteService;
import com.funnyland.funnyland_server.service.user.UserAuthService;
import com.funnyland.funnyland_server.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delete")
public class DeleteApiController {
    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserService userService;
    
    @Autowired
    DeleteService deleteService;
    // /api/delete/banner/one
    @PostMapping("/banner/one")
    public String DeleteBannerOneApi(@RequestBody BannerGetDto dto){
        deleteService.deleteBannerOneService(dto);
        return "{\"message\":\"success\"}";
    }

    // /api/delete/counseling/one
    @PostMapping("/counseling/one")
    public ResponseEntity<Message> DeleteCounselingOneApi(HttpServletRequest request ,@RequestBody CounselingGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        try{
            UserInfoVO user = userService.getUserInfo(request);
            // user.setRole("ROLE_USER");
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            deleteService.deleteCounselingOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
        
    }

    // /api/delete/product_category/one
    @PostMapping("/product_category/one")
    public ResponseEntity<Message> DeleteProductCategoryOneApi(HttpServletRequest request ,@RequestBody ProductCategoryGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        try{
            UserInfoVO user = userService.getUserInfo(request);
            // user.setRole("ROLE_USER");
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            deleteService.deleteProductCategoryOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
        
    }

    // /api/delete/product/one
    @PostMapping("/product/one")
    public ResponseEntity<Message> DeleteProductOneApi(HttpServletRequest request ,@RequestBody ProductGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        try{
            UserInfoVO user = userService.getUserInfo(request);
            // user.setRole("ROLE_USER");
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            deleteService.deleteProductOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
        
    }

    // /api/delete/video/one
    @PostMapping("/video/one")
    public ResponseEntity<Message> DeleteVideoOneApiCompletely(HttpServletRequest request ,@RequestBody VideoGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        try{
            UserInfoVO user = userService.getUserInfo(request);
            // user.setRole("ROLE_USER");
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            deleteService.deleteVideoOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
        
    }

    // /api/delete/store_area/one
    @PostMapping("/store_area/one")
    public ResponseEntity<Message> DeleteStoreAreaOneApi(HttpServletRequest request ,@RequestBody StoreAreaGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        try{
            UserInfoVO user = userService.getUserInfo(request);
            // user.setRole("ROLE_USER");
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            deleteService.deleteStoreAreaOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
        
    }

    // /api/delete/store/one
    @PostMapping("/store/one")
    public ResponseEntity<Message> DeleteStoreOneApi(HttpServletRequest request ,@RequestBody StoreGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        try{
            UserInfoVO user = userService.getUserInfo(request);
            // user.setRole("ROLE_USER");
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            deleteService.deleteStoreOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
        
    }

    @PostMapping("/cs/one")
    public ResponseEntity<Message> DeleteCsOneApi(HttpServletRequest request ,@RequestBody CsGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        try{
            UserInfoVO user = userService.getUserInfo(request);
            // user.setRole("ROLE_USER");
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            deleteService.deleteCsOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
        
    }

    // /api/delete/popup/one
    @PostMapping("/popup/one")
    public ResponseEntity<Message> DeletePopupOneApiCompletely(HttpServletRequest request ,@RequestBody PopupGetDto dto){
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        try{
            UserInfoVO user = userService.getUserInfo(request);
            // user.setRole("ROLE_USER");
            if(!user.getRole().equals("ROLE_ADMIN")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch(NullPointerException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            deleteService.deletePopupOneService(dto);
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
