package com.funnyland.funnyland_server.service.user;

import com.funnyland.funnyland_server.enums.DeletedFlagEnums;
import com.funnyland.funnyland_server.exception.dto.InvalidUserException;
import com.funnyland.funnyland_server.model.refresh_token.entity.RefreshTokenEntity;
import com.funnyland.funnyland_server.model.refresh_token.repository.RefreshTokenRepository;
import com.funnyland.funnyland_server.model.user.dto.UserLoginDTO;
import com.funnyland.funnyland_server.model.user.dto.UserLoginSessionDTO;
import com.funnyland.funnyland_server.model.user.dto.UserSignupDTO;
import com.funnyland.funnyland_server.model.user.entity.UserEntity;
import com.funnyland.funnyland_server.model.user.repository.UserRepository;
import com.funnyland.funnyland_server.service.handler.ConvertService;
import com.funnyland.funnyland_server.service.handler.EXIST_OR_NOT;
import com.funnyland.funnyland_server.utils.*;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

enum ROLE {
    ROLE_ADMIN, ROLE_USER, ROLE_MANAGER, ROLE_USERA, ROLE_USERB, ROLE_USERC, ROLE_USERP
}

@Service
public class UserAuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CredentialExtendService credentialExtendService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ConvertService convert;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    
    public String insertUserOne(UserSignupDTO userSignupDto) {
        UserEntity user = new UserEntity();

        // Create DB user identity UUID
        UUID uuid = UUID.randomUUID();
        // Create password salt UUID
        UUID uuidSalt = UUID.randomUUID();

        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();

        String salt = uuidSalt.toString();
        // Encode password is String (input password string data of signup user +
        // created password of salt UUID).
        String encPassword = encoder.encode(userSignupDto.getPassword() + salt);

        user.setId(uuid.toString());
        user.setUsername(userSignupDto.getUsername());
        user.setPassword(encPassword);
        user.setSalt(salt);
        user.setEmail(userSignupDto.getEmail());
        user.setName(userSignupDto.getName());
        user.setRoles(ROLE.ROLE_USER.toString());
        user.setCreatedAt(currentDate);
        user.setUpdatedAt(currentDate);
        user.setCredentialCreatedAt(currentDate);
        user.setCredentialExpireAt(currentDate);

        if (userRepository.save(user).getId() != null) {
            return "{\"message\":\"success\"}";
        } else {
            return "{\"message\":\"failure\"}";
        }
    }

    public Boolean checkUserLogin(UserLoginDTO userLoginDto, HttpServletRequest request) {
        // ** OLD TEST_TODO V1
        // UserEntity user = userRepository.findByUsername(userLoginDto.getUsername());
        // if (user == null) {
        //     return false;
        // }

        // String mergePassword = userLoginDto.getPassword() + user.getSalt();

        // if (encoder.matches(mergePassword, user.getPassword())) {
        //     UserLoginSessionDTO sessionDataSet = setUserEntityToSessionDTO(user);
        //     String dto2String = convert.objectClass2JsonStringConvert(sessionDataSet);
        //     redisTemplate.opsForValue().set("spring:session:sessions:expires:" + request.getSession().getId(),dto2String);
        //     return true;
        // }
        // return false;

        // ** NEW V1
//        Optional<UserEntity> userOpt = userRepository.findByUsername_Custom(userLoginDto.getUsername(), EXIST_OR_NOT.IS_EXIST);
//        if (userOpt.isEmpty()) {
//            return false;
//        }
//        UserEntity user = userOpt.get();
//
//        String mergePassword = userLoginDto.getPassword() + user.getSalt();
//
//        if (encoder.matches(mergePassword, user.getPassword())) {
//            UserLoginSessionDTO sessionDataSet = setUserEntityToSessionDTO(user);
//            String dto2String = convert.objectClass2JsonStringConvert(sessionDataSet);
//            redisTemplate.opsForValue().set("spring:session:sessions:expires:" + request.getSession().getId(),dto2String);
//            return true;
//        }
        return false;
    }

    public Boolean isUserExist(String username) {
        // ** OLD TEST_TODO V1
        // UserEntity user = userRepository.findByUsername(username);
        // if (user != null) {
        //     return true;
        // } else {
        //     return false;
        // }

        // ** NEW V1
        Optional<UserEntity> userOpt = userRepository.findByUsername_Custom(username,EXIST_OR_NOT.IS_EXIST);
        if (userOpt.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isUserSessionValid(HttpServletRequest request){

        // **Original
        // Object session = redisTemplate.opsForValue().get("spring:session:sessions:expires:" + request.getSession().getId());
        
        // if( session != null && session.getClass().getSimpleName().equals("UserLoginSessionDTO") ){
        //     UserLoginSessionDTO sessionData = (UserLoginSessionDTO) session;
        //     if(sessionData.getStatus().equals("loged")){
        //         return true;
        //     }
        //     return false;
        // }
        // return false;

        // **Test OK**
//        String session = (String) redisTemplate.opsForValue().get("spring:session:sessions:expires:" + request.getSession().getId());
//        // System.out.println(session.isEmpty());
//        if( session != null && !session.isEmpty() ){
//            // System.out.println("UserAuthService/isUserSessionValid : hello");
//            UserLoginSessionDTO sessionData = (UserLoginSessionDTO) convert.jsonString2ObjectClassConvert(session, UserLoginSessionDTO.class);
//            // System.out.println(sessionData.getStatus());
//            if(sessionData.getStatus().equals("loged")){
//                return true;
//            }
//            return false;
//        }
        return false;


        // TestCode
        // ObjectMapper mapper = new ObjectMapper();

        // Object session = redisTemplate.opsForValue().get("spring:session:sessions:expires:" + request.getSession().getId());
        // UserLoginSessionDTO sessionData = (UserLoginSessionDTO) session;
        // System.out.println(sessionData);

        // String str=null;
        // try {
        //     str = mapper.writeValueAsString(sessionData);
        // } catch (JsonProcessingException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        // System.out.println(str);
        
        // UserLoginSessionDTO readValue = new UserLoginSessionDTO();
        // try {
        //     readValue = mapper.readValue(str, UserLoginSessionDTO.class);
        // } catch (JsonMappingException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // } catch (JsonProcessingException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        // System.out.println(readValue);

        // TestCode2
        // Object session = redisTemplate.opsForValue().get("spring:session:sessions:expires:" + request.getSession().getId());
        // UserLoginSessionDTO sessionData = (UserLoginSessionDTO) session;

        // String str = convert.objectClass2JsonStringConvert(sessionData);
        // System.out.println(str);

        // UserLoginSessionDTO test = (UserLoginSessionDTO) convert.jsonString2ObjectClassConvert(str, UserLoginSessionDTO.class);
        // System.out.println(test);
        // return false;
    }

    // Handler
    private UserLoginSessionDTO setUserEntityToSessionDTO(UserEntity entity){
        UserLoginSessionDTO sessionData = new UserLoginSessionDTO();
        sessionData.setStatus("loged");
        sessionData.setId(entity.getId());
        sessionData.setUsername(entity.getUsername());
        sessionData.setEmail(entity.getEmail());
        sessionData.setUserUrl(entity.getUserUrl());
        sessionData.setName(entity.getName());
        sessionData.setRole(entity.getRoles());
        sessionData.setCreatedAt(entity.getCreatedAt());
        sessionData.setUpdatedAt(entity.getUpdatedAt());
        sessionData.setCredentialCreatedAt(entity.getCredentialCreatedAt());
        sessionData.setCredentialExpireAt(entity.getCredentialExpireAt());
        sessionData.setDeleted(entity.getDeleted());
        return sessionData;
    }

    @Transactional(readOnly = true)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CustomCookieUtils.clearCookieForName(response, CustomCookieUtils.COOKIE_NAME_ACCESS_TOKEN);
    }

    @Transactional(readOnly = true)
    public void checkLogin(){
        String userId = UserUtils.getUserIdElseThrow();
    }

    @Transactional
    public void login(HttpServletRequest request, HttpServletResponse response, UserLoginDTO userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(()-> new InvalidUserException("유저를 찾을 수 없음."));

        String USER_ID = userEntity.getId();
        String ENC_PASSWORD = userEntity.getPassword();
        String SALT = userEntity.getSalt();
        String FULL_PASSWORD = password + SALT;

        if (!passwordEncoder.matches(FULL_PASSWORD, ENC_PASSWORD)) {
            throw new InvalidUserException("입력한 아이디 및 패스워드를 확인해 주세요.");
        }

        /*
        acccess token, refresh token 생성
         */
        String REFRESH_TOKEN_ID = UUID.randomUUID().toString();

        String refreshToken = AuthTokenUtils.generateRefreshTokenJwt(userEntity);
        String accessToken = AuthTokenUtils.generateAccessTokenJwt(userEntity, REFRESH_TOKEN_ID);

        /*
        refreshTokenEntity 구성
         */
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .cid(null)
                .id(REFRESH_TOKEN_ID)
                .userId(USER_ID)
                .refreshToken(refreshToken)
                .createdAt(CustomDateUtils.getCurrentDateTime())
                .updatedAt(CustomDateUtils.getCurrentDateTime())
                .deletedFlag(DeletedFlagEnums.EXIST.getValue())
                .build();

        /*
        신규 refreshTokenEntity DB 저장 후, AllowedAccessCount 의 값에 따라서 oldRefreshToken을 제거해준다.
         */
        refreshTokenRepository.save(refreshTokenEntity);
        refreshTokenRepository.deleteOldRefreshTokenForUser(USER_ID, 3);

        ResponseCookie tokenCookie = ResponseCookie.from(CustomCookieUtils.COOKIE_NAME_ACCESS_TOKEN, accessToken)
                .httpOnly(true)
                .domain(CustomCookieUtils.COOKIE_DOMAIN)
                .secure(CustomCookieUtils.SECURE)
                .sameSite("Strict")
                .path("/")
                .maxAge(CustomCookieUtils.ACCESS_TOKEN_COOKIE_EXPIRATION)
                .build();

        /*
        accessToken을 쿠키에 등록해준다.
         */
        response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());
    }

    @Transactional(readOnly = true)
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie jwtTokenCookie = WebUtils.getCookie(request, CustomCookieUtils.COOKIE_NAME_ACCESS_TOKEN);

        /*
        액세스 토큰 쿠키값 유무 확인, 없다면 401 에러
         */
        if (jwtTokenCookie == null) {
            throw new InvalidUserException("접근 토큰이 만료 되었습니다. 재로그인 해주시기 바랍니다.");
        }

        String accessToken = jwtTokenCookie.getValue();
        Claims claims = null;

        /*
        액세스 토큰의 정상 유무 판단, ExpirationJwtException 에러가 난다면 재발급 가능한 토큰, 이 외의 에러가 난다면 재발급 불가능 토큰으로 401 에러
         */
        try {
            claims = CustomJwtUtils.parseJwt(AuthTokenUtils.getAccessTokenSecret(), accessToken);
        } catch (ExpiredJwtException e) {
            // 만료된 액세스토큰 claims를 이용해 리프레시 토큰 id를 추출할 수 있다.
            claims = e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidUserException("리소스에 접근이 불가능한 토큰 입니다.");
        } catch (Exception e) {
            throw new InvalidUserException("리소스에 접근이 불가능한 토큰 입니다.");
        }

        UUID refreshTokenId = null;

        /*
        액세스 토큰의 claims 에서 refreshTokenId 값을 추출
         */
        try {
            refreshTokenId = UUID.fromString(claims.get("refreshTokenId").toString());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidUserException("리소스에 접근이 불가능한 토큰 입니다.");
        }

        /*
        refreshTokenId 값으로 RefreshTokenEntity 조회
         */
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findById(refreshTokenId.toString()).orElseThrow(()-> new InvalidUserException("로그인이 만료 되었습니다."));

        /*
        RefreshTokenEntity 가 Null 이라면 401 에러
         */
        if (refreshTokenEntity == null) {
            this.logout(request, response);
            throw new InvalidUserException("로그인이 만료 되었습니다.");
        }

        /*
        RefreshTokenEntity 가 가지고 있는 jwt 값을 파싱한다. 파싱 에러가 난다면 401 에러
         */
        Claims refreshTokenClaims = null;
        try {
            refreshTokenClaims = CustomJwtUtils.parseJwt(AuthTokenUtils.getRefreshTokenSecret(), refreshTokenEntity.getRefreshToken());
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            this.logout(request, response);
            throw new InvalidUserException("로그인이 만료 되었습니다.");
        } catch (Exception e) {
            this.logout(request, response);
            throw new InvalidUserException("로그인이 만료 되었습니다.");
        }

        /*
        refreshTokenJwt 의 유저 id 와 username 값으로 새로운 accessTokenJwt를 만들어준다.
         */
        String id = UUID.fromString(refreshTokenClaims.get("id").toString()).toString();
        String username = refreshTokenClaims.get("username").toString();

        UserEntity user = UserEntity.builder()
                .id(id)
                .username(username)
                .build();

        String newAccessToken = AuthTokenUtils.generateAccessTokenJwt(user, refreshTokenEntity.getId());

        /*
        새로운 액세스 토큰을 쿠키에 저장해 준다.
         */
        ResponseCookie tokenCookie = ResponseCookie.from(CustomCookieUtils.COOKIE_NAME_ACCESS_TOKEN, newAccessToken)
                .httpOnly(true)
                .secure(CustomCookieUtils.SECURE)
                .sameSite("Strict")
                .domain(CustomCookieUtils.COOKIE_DOMAIN)
                .path("/")
                .maxAge(CustomCookieUtils.ACCESS_TOKEN_COOKIE_EXPIRATION)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());
    }
}
