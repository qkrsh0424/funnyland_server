package com.funnyland.funnyland_server.service.user;


import com.amazonaws.services.codecommit.model.UserInfo;
import com.funnyland.funnyland_server.exception.dto.InvalidUserException;
import com.funnyland.funnyland_server.model.user.dto.UserLoginSessionDTO;
import com.funnyland.funnyland_server.model.user.entity.UserEntity;
import com.funnyland.funnyland_server.model.user.repository.UserRepository;
import com.funnyland.funnyland_server.model.user.vo.UserInfoVO;
import com.funnyland.funnyland_server.service.handler.ConvertService;
import com.funnyland.funnyland_server.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ConvertService convert;

    public UserInfoVO getUserInfo(HttpServletRequest request) {
        String userId = UserUtils.getUserIdElseThrow();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("로그인이 필요한 서비스 입니다."));

        UserInfoVO vo = UserInfoVO.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .userUrl(userEntity.getUserUrl())
                .name(userEntity.getName())
                .role(userEntity.getRoles())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .credentialCreatedAt(userEntity.getCredentialCreatedAt())
                .credentialExpireAt(userEntity.getCredentialExpireAt())
                .build();
        return vo;
    }

    public UserInfoVO getUserSessionDtoToVo(UserLoginSessionDTO userSessionData) {
        UserInfoVO user = UserInfoVO.builder()
                .username(userSessionData.getUsername())
                .email(userSessionData.getEmail())
                .userUrl(userSessionData.getUserUrl())
                .name(userSessionData.getName())
                .role(userSessionData.getRole())
                .createdAt(userSessionData.getCreatedAt())
                .updatedAt(userSessionData.getUpdatedAt())
                .credentialCreatedAt(userSessionData.getCredentialCreatedAt())
                .credentialExpireAt(userSessionData.getCredentialExpireAt())
                .build();
        return user;
    }
}
