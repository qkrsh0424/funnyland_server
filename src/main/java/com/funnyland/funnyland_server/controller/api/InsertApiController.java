package com.funnyland.funnyland_server.controller.api;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;

import com.funnyland.funnyland_server.model.banner.dto.BannerReqDto;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingReqDto;
import com.funnyland.funnyland_server.model.cs.dto.CsGetDto;
import com.funnyland.funnyland_server.model.message.Message;
import com.funnyland.funnyland_server.model.message.StatusEnum;
import com.funnyland.funnyland_server.model.popup.dto.PopupGetDto;
import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;
import com.funnyland.funnyland_server.model.store.dto.StoreGetDto;
import com.funnyland.funnyland_server.model.store_area.dto.StoreAreaGetDto;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.user.vo.UserInfoVO;
import com.funnyland.funnyland_server.model.video.dto.VideoGetDto;
import com.funnyland.funnyland_server.service.InsertService;
import com.funnyland.funnyland_server.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/insert")
public class InsertApiController {
    @Autowired
    UserService userService;
    @Autowired
    InsertService insertService;

    // /api/insert/counseling/one
    @PostMapping("/counseling/one")
    public String InsertCounselingRequest(HttpServletRequest request, @RequestBody CounselingReqDto dto) {
        // System.out.println(dto);
        insertService.insertCounselingOneService(dto);
        return "{\"message\":\"success\"}";
    }

    // /api/insert/banner/multiple
    @PostMapping("/banner/multiple")
    public String InsertBannerMultipleRequest(HttpServletRequest request, @RequestBody List<BannerReqDto> dtos){
        insertService.insertBannersService(dtos);
        return "{\"message\":\"success\"}";
    }

    // /api/insert/product_category/one
    @PostMapping("/product_category/one")
    public ResponseEntity<Message> InsertProductCategoryOneRequest(HttpServletRequest request, @RequestBody ProductCategoryGetDto dto){
        // System.out.println(dto);
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
            insertService.insertProductCategoryOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/insert/product/one
    @PostMapping("/product/one")
    public ResponseEntity<Message> InsertProductOneRequest(HttpServletRequest request, @RequestBody ProductGetDto dto){
        // System.out.println(dto);
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
            insertService.insertProductOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/insert/video/one
    @PostMapping("/video/one")
    public ResponseEntity<Message> InsertVideoOneRequestApi(HttpServletRequest request, @RequestBody VideoGetDto dto){
        // System.out.println(dto);
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
            insertService.insertVideoOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/insert/store_area/one
    @PostMapping("/store_area/one")
    public ResponseEntity<Message> InsertStoreAreaOneRequestApi(HttpServletRequest request, @RequestBody StoreAreaGetDto dto){
        // System.out.println(dto);
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
            insertService.insertStoreAreaOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/insert/store/one
    @PostMapping("/store/one")
    public ResponseEntity<Message> InsertStoreOneRequestApi(HttpServletRequest request, @RequestBody StoreGetDto dto){
        // System.out.println(dto);
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
            insertService.insertStoreOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/insert/cs/one
    @PostMapping("/cs/one")
    public ResponseEntity<Message> InsertCsOneRequestApi(HttpServletRequest request, @RequestBody CsGetDto dto){
        // System.out.println(dto);
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
            insertService.insertCsOneService(dto);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message,headers,HttpStatus.OK);
    }

    // /api/insert/popup/one
    @PostMapping("/popup/one")
    public ResponseEntity<Message> InsertPopupOneRequestApi(HttpServletRequest request, @RequestBody PopupGetDto dto){
        // System.out.println(dto);
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
            insertService.insertPopupOneService(dto);
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
