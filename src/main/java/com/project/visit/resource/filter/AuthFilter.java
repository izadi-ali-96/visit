package com.project.visit.resource.filter;

import com.project.visit.exception.AuthException;
import com.project.visit.exception.ResponseResult;
import com.project.visit.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService authService;

    private static final List<Pattern> PUBLIC_URLS = List.of(
            Pattern.compile("/swagger-ui.*"),
            Pattern.compile("/api-docs.*"),
            Pattern.compile("/authentication/login"),
            Pattern.compile("/user/patient"),
            Pattern.compile("/user/doctor"),
            Pattern.compile("^/doctor$"),
            Pattern.compile("^/doctor/expertise$"),
            Pattern.compile("^/visit/list$"),
            Pattern.compile("^/visit/doctor/light$"),
            Pattern.compile("^/doctor/\\d$"),
            Pattern.compile("^/comment/.*\\d$"),
            Pattern.compile("^/doctor/image/\\d$"),
            Pattern.compile("^/location/.*\\d$"),
            Pattern.compile("^/visit/doctor$")
    );

    private Map<Pattern, String> PERMIT_URLS() {
        var map = new HashMap<Pattern, String>();
        map.put(Pattern.compile("^/doctor/add/expertise$"), "DOCTOR");
        map.put(Pattern.compile("^/doctor/info$"), "DOCTOR");
        map.put(Pattern.compile("^/doctor/image$"), "DOCTOR");
        map.put(Pattern.compile("^/doctor/address/.*\\d$"), "DOCTOR");
        map.put(Pattern.compile("^/doctor/address$"), "DOCTOR");
        map.put(Pattern.compile("^/doctor/profile$"), "DOCTOR");
        map.put(Pattern.compile("^/visit/generate$"), "DOCTOR");
        map.put(Pattern.compile("^/visit/assign$"), "USER");
        map.put(Pattern.compile("^/visit/user$"), "USER");
        map.put(Pattern.compile("^/comment/add$"), "USER");
        map.put(Pattern.compile("^/visit/doctor/light$"), "USER");
        map.put(Pattern.compile("^/visit/unassign/.*\\d$"), "USER");
        map.put(Pattern.compile("^/doctor/.*\\d/delete$"), "DOCTOR");
        return map;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (checkPublicUrls(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            var authModel = authService.checkToken(request.getHeader("Authorization"));
            checkApiPermission(request.getRequestURI(), authModel.roles());
            var mutableRequest = new MutableHttpServletRequest(request);
            mutableRequest.putHeader(Constants.REQUEST_HEADER_USER_ID, authModel.username());
            filterChain.doFilter(mutableRequest, response);
        } catch (AuthException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    void checkApiPermission(String url, List<String> roles) {
        var permit = false;
        for (var pattern : PERMIT_URLS().entrySet()) {
            if (pattern.getKey().matcher(url).find()) {
                permit = roles.contains(pattern.getValue());
                break;
            }
        }
        if (!permit) {
            throw new AuthException(ResponseResult.NOT_PERMIT_EXCEPTION);
        }
    }

    private boolean checkPublicUrls(String url) {
        var isPublic = false;
        for (Pattern publicUrl : PUBLIC_URLS) {
            if (publicUrl.matcher(url).find()) {
                isPublic = true;
                break;
            }
        }
        return isPublic;
    }
}
