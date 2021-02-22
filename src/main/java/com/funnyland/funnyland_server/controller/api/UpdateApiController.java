package com.funnyland.funnyland_server.controller.api;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.funnyland.funnyland_server.model.banner.dto.BannerGetDto;
import com.funnyland.funnyland_server.model.counseling.dto.CounselingGetDto;
import com.funnyland.funnyland_server.model.message.Message;
import com.funnyland.funnyland_server.model.message.StatusEnum;
import com.funnyland.funnyland_server.model.product.dto.ProductGetDto;
import com.funnyland.funnyland_server.model.user.vo.UserInfoVO;
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
    public ResponseEntity<Message> InsertProductOneRequest(HttpServletRequest request, @RequestBody ProductGetDto dto){
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
}
