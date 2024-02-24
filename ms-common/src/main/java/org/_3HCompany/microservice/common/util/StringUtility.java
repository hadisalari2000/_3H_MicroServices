package org._3HCompany.microservice.common.util;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StringUtility extends StringUtils {

    public static String toPersian(String input)
    {
        return input.trim().replace('ك','ک').replace('ي','ی');
    }

    public static Boolean stringIsNullOrEmpty(String str){
        if(str==null)
            return true;
        return str.trim().isEmpty();
    }
}