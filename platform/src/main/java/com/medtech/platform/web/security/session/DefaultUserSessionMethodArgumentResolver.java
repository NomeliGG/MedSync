package com.medtech.platform.web.security.session;

import com.medtech.platform.exception.WrongDataException;
import com.medtech.platform.web.PlatformHttpHeaders;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DefaultUserSessionMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(DefaultUserSession.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken authenticationToken) {
            return UserSessionMapper.defaultSessionFrom(
                    authenticationToken,
                    () -> Optional
                            .ofNullable(webRequest.getHeader(PlatformHttpHeaders.ORIGINAL_SESSION_ID))
                            .map(UUID::fromString)
            ).orElseThrow(DefaultUserSessionMethodArgumentResolver::sessionResolveError);
        } else {
            throw sessionResolveError();
        }
    }

    private static WrongDataException sessionResolveError() {
        return new WrongDataException("Unable to resolve user session");
    }

}
