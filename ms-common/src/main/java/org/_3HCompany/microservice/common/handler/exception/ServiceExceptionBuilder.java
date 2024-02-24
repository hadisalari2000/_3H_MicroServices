package org._3HCompany.microservice.common.handler.exception;/*
package com.example.eshop.handler.exception;


import com.example.eshop.util.ApplicationProperties;
import lombok.Synchronized;
import org.springframework.http.HttpStatus;

public class ServiceExceptionBuilder {

    @Synchronized
    public static ServiceException serviceExceptionBuilder(String key, HttpStatus httpStatus) {
        return ServiceException.builder()
                .key(key)
                .message(ApplicationProperties.getProperty(key))
                .httpStatus(httpStatus)
                .build();
    }

    @Synchronized
    public static ServiceException duplicateExceptionBuilder(Class clazz, String fieldName,String fieldValue){
        String exceptionKey="duplicated";
        String message=generateMessage(exceptionKey,clazz,fieldName,fieldValue);
        return ServiceException.builder()
                .key(exceptionKey)
                .message(message)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @Synchronized
    public static ServiceException notFoundExceptionBuilder(Class clazz, String fieldName,String fieldValue) {
        String exceptionKey="not-found";
        String message=generateMessage(exceptionKey,clazz,fieldName,fieldValue);
        return ServiceException.builder()
                .key(exceptionKey)
                .message(message)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @Synchronized
    public static ServiceException forbiddenExceptionBuilder(Class clazz){
        String exceptionKey="forbidden";
        String message=generateMessage(exceptionKey,clazz);
        return ServiceException.builder()
                .key(exceptionKey)
                .message(message)
                .httpStatus(HttpStatus.FORBIDDEN)
                .build();
    }

    @Synchronized
    public static ServiceException authorizeExceptionBuilder(String key){
        return ServiceException.builder()
                .key(key)
                .message(ApplicationProperties.getProperty(key))
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @Synchronized
    public static ServiceException badRequestExceptionBuilder(String messageKey,String fieldValue){
        String message= String.format(ApplicationProperties.getProperty(messageKey), fieldValue);
        return ServiceException.builder()
                .key(messageKey)
                .message(message)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @Synchronized
    public static ServiceException badRequestExceptionBuilder(String messageKey){
        String message= ApplicationProperties.getProperty(messageKey);
        return ServiceException.builder()
                .key(messageKey)
                .message(message)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    private static String generateMessage(String key,Class clazz) {
        String entity=clazz.getSimpleName();
        return String.format(
                ApplicationProperties.getProperty(key),
                ApplicationProperties.getProperty(entity.toLowerCase()));
    }

    private static String generateMessage(String key,Class clazz, String fieldName,String fieldValue) {
        String entity=clazz.getSimpleName();
        return String.format(
                ApplicationProperties.getProperty(key),
                ApplicationProperties.getProperty(entity.toLowerCase()),
                ApplicationProperties.getProperty(fieldName)+ " = "+fieldValue);
    }
}
*/
