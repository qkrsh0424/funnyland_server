package com.funnyland.funnyland_server.utils;

import com.funnyland.funnyland_server.config.auth.PrincipalDetails;
import com.funnyland.funnyland_server.exception.dto.InvalidUserAuthException;
import com.funnyland.funnyland_server.exception.dto.InvalidUserException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public class UserUtils {
    public static String getUserIdElseThrow() {
        try {
            PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            return principalDetails.getUser().getId();
        } catch (Exception e) {
            throw new InvalidUserAuthException("로그인 회원이 아니거나, 잘못된 접근방식임.");
        }
    }
}
