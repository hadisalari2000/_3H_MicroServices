package org._3HCompany.microservices.uaa.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
public class ApplicationUtilities {

    private static ApplicationUtilities applicationUtilities;

    public static ApplicationUtilities getInstance() {
        if (applicationUtilities == null) {
            applicationUtilities = new ApplicationUtilities();
        }
        return applicationUtilities;
    }

    public static Boolean fileExtensionIsValid(String fileExtension){

        List<String> list = List.of(ApplicationProperties.getProperty("valid.image.file.format"));
        return list.stream()
                .map(String::toLowerCase)
                .anyMatch(s -> s.contains(fileExtension.toLowerCase()));
    }

    public HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }
}
