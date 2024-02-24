package org._3HCompany.microservice.common.handler.exception;/*
package com.example.eshop.handler.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.eshop.util.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@ControllerAdvice
public class ServiceExceptionHandler {

    private final AuditCallHandler auditHandler;

    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    public ServiceExceptionHandler(AuditCallHandler auditHandler) {
        this.auditHandler = auditHandler;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Set<MetaMapDTO> errors = null;
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String errorMessage = ApplicationProperties.getProperty(error.getDefaultMessage());
            if (errorMessage == null)
                errorMessage = ApplicationProperties.getProperty("invalid_parameter");
            String key = "invalid_parameter";
            String validationErrorType = (error.getCodes() != null && error.getCodes().length == 4) ? error.getCodes()[3] : null;
            String sizeError = "";
            if (validationErrorType != null) switch (validationErrorType) {
                case "NotBlank":
                case "NotNull":
                    key = "missing_parameter";
                    errorMessage = ApplicationProperties.getProperty("missing_parameter");
                    break;
                case "Min":
                    key = "invalid_min_length";
                    errorMessage = ApplicationProperties.getProperty("invalid_min_length");
                    sizeError = (error.getArguments() != null && error.getArguments().length == 2) ? error.getArguments()[1].toString() : "";
                    break;
                case "Max":
                    key = "invalid_min_length";
                    errorMessage = ApplicationProperties.getProperty("invalid_max_length");
                    sizeError = (error.getArguments() != null && error.getArguments().length == 2) ? error.getArguments()[1].toString() : "";
                    break;
                case "Size":
                    key = "invalid_length";
                    errorMessage = ApplicationProperties.getProperty("invalid_length");
                    if (error.getArguments() != null && error.getArguments().length == 3) {
                        Integer maxValue = TypesHelper.tryParseInt(error.getArguments()[1]);
                        Integer minValue = TypesHelper.tryParseInt(error.getArguments()[2]);
                        if (minValue != null && minValue != 0) {
                            sizeError = ApplicationProperties.getProperty("less") + " " + minValue.toString() + " ";
                        }
                        if (maxValue != null && maxValue != Integer.MAX_VALUE) {
                            if (!sizeError.equals(""))
                                sizeError = String.format("%s %s ", sizeError, ApplicationProperties.getProperty("and"));
                            sizeError += ApplicationProperties.getProperty("more") + " " + maxValue.toString() + " ";
                        }
                    }
                    break;
                case "Pattern":
                    key = "invalid_parameter";
                    errorMessage = String.format("%s", ApplicationProperties.getProperty("invalid_parameter"));
                    break;
                default:
                    sizeError = "";
                    break;
            }
            errors = BaseDTOMapper.getInstance().setMetaDTOCollection(errors,
                    MetaMapDTO.builder()
                            .key(key)
                            .message(String.format(errorMessage, error.getField(), sizeError)
                            ).build()
            );
        }
        BaseDTO baseDTO = BaseDTO.builder().meta(MetaDTO.builder().errors(errors).warnings(null).build()).build();
        logException(baseDTO, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<MetaMapDTO> errors = null;
        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
            String errorMessage = ApplicationProperties.getProperty(error.getMessage());
            if (errorMessage == null)
                errorMessage = ApplicationProperties.getProperty("invalid_parameter");
            String key = "invalid_parameter";
            String field = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
            String fieldName = (ApplicationProperties.getProperty(field) != null) ? ApplicationProperties.getProperty(field) : field;
            String sizeError = "";
            if (error.getConstraintDescriptor().getAnnotation().annotationType().equals(javax.validation.constraints.NotBlank.class)) {
                key = "missing_parameter";
                errorMessage = ApplicationProperties.getProperty("missing_parameter");
            }
            if (error.getConstraintDescriptor().getAnnotation().annotationType().equals(javax.validation.constraints.Min.class)) {
                key = "invalid_min_length";
                errorMessage = ApplicationProperties.getProperty("invalid_min_length");
                sizeError = (error.getConstraintDescriptor().getAttributes().get("value") != null) ? error.getConstraintDescriptor().getAttributes().get("value").toString() : "";
            }
            if (error.getConstraintDescriptor().getAnnotation().annotationType().equals(javax.validation.constraints.Max.class)) {
                key = "invalid_max_length";
                errorMessage = ApplicationProperties.getProperty("invalid_max_length");
                sizeError = (error.getConstraintDescriptor().getAttributes().get("value") != null) ? error.getConstraintDescriptor().getAttributes().get("value").toString() : "";
            }
            if (error.getConstraintDescriptor().getAnnotation().annotationType().equals(javax.validation.constraints.Size.class)) {
                errorMessage = ApplicationProperties.getProperty("invalid_length");
                Integer maxValue = TypesHelper.tryParseInt(error.getConstraintDescriptor().getAttributes().get("max"));
                Integer minValue = TypesHelper.tryParseInt(error.getConstraintDescriptor().getAttributes().get("min"));
                if (minValue != null && minValue != 0) {
                    sizeError = ApplicationProperties.getProperty("less") + " " + minValue.toString() + " ";
                }
                if (maxValue != null && maxValue != Integer.MAX_VALUE) {
                    if (!sizeError.equals(""))
                        sizeError = String.format("%s %s ", sizeError, ApplicationProperties.getProperty("and"));
                    sizeError += ApplicationProperties.getProperty("more") + " " + maxValue.toString() + " ";
                }
            }
            errors = BaseDTOMapper.getInstance().setMetaDTOCollection(errors,
                    MetaMapDTO.builder()
                            .key(key)
                            .message(String.format(errorMessage, fieldName, sizeError)
                            ).build()
            );
        }
        BaseDTO baseDTO = BaseDTO.builder().meta(MetaDTO.builder().errors(errors).warnings(null).build()).build();
        logException(baseDTO, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(BaseDTO.builder().meta(MetaDTO.builder().errors(errors).warnings(null).build()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<?> handleServiceException(ServiceException ex) {

        BaseDTO baseDTO = BaseDTO.builder()
                .meta(MetaDTO.builder()
                        .errors(BaseDTOMapper.getInstance()
                                .setMetaDTOCollection(
                                        null,
                                        MetaMapDTO.builder()
                                                .key(ex.getKey())
                                                .message(ApplicationProperties.getProperty(ex.getMessage()))
                                                .build()))
                        .warnings(null)
                        .build())
                .build();
        logException(baseDTO, ex.getHttpStatus());
        return new ResponseEntity<>(baseDTO, ex.getHttpStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<?> handleAllExceptions(NoHandlerFoundException ex) {
        BaseDTO baseDTO = BaseDTO.builder().meta(
                MetaDTO.builder().errors(
                        BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                MetaMapDTO.builder().key("not_found_url").message(ApplicationProperties.getProperty("not_found_url")).build()))
                        .warnings(null).build())
                .build();
        logException(baseDTO, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(baseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<?> handleMissingParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        BaseDTO baseDTO = BaseDTO.builder().meta(
                MetaDTO.builder()
                        .errors(BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                MetaMapDTO.builder()
                                        .key("missing_parameter")
                                        .message(String.format(ApplicationProperties.getProperty("missing_parameter"), ex.getParameterName())
                                        ).build()))
                        .warnings(null).build())
                .build();
        logException(baseDTO, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<?> handleNotReadableExceptions(HttpMessageNotReadableException ex) {
        BaseDTO baseDTO =BaseDTO.builder().build();

        if(ex.getMessage().contains("JSON parse error: Unrecognized token")){
            String variable=ex.getMessage().split("JSON parse error: Unrecognized token")[1]
                    .split("'")[1]
                    .trim();

            baseDTO = BaseDTO.builder().meta(
                    MetaDTO.builder().errors(
                            BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                    MetaMapDTO.builder()
                                            .key("invalid_parameter")
                                            .message(String.format(variable, ApplicationProperties.getProperty("invalid_parameter")))
                                            .build()))
                            .warnings(null).build())
                    .build();
        }
        else{
            baseDTO = BaseDTO.builder().meta(
                    MetaDTO.builder().errors(
                            BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                    MetaMapDTO.builder()
                                            .key("required_parameters")
                                            .message(ApplicationProperties.getProperty("required_parameters"))
                                            .build()))
                            .warnings(null).build())
                    .build();
        }
        logException(baseDTO, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<?> handleAllExceptions(RuntimeException ex, HttpServletRequest request) {
        BaseDTO baseDTO = BaseDTO.builder().meta(
                MetaDTO.builder().errors(
                        BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                MetaMapDTO.builder()
                                        .key("unexpected_error")
                                        .message(ApplicationProperties.getProperty("unexpected_error")
                                        ).build()))
                        .warnings(null).build())
                .build();
        logException(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<?> methodArgumentTypeMismatchExceptionExceptions(MethodArgumentTypeMismatchException ex) {
        BaseDTO baseDTO = BaseDTO.builder().meta(
                MetaDTO.builder()
                        .errors(BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                MetaMapDTO.builder()
                                        .key("invalid_parameter")
                                        .message(String.format(ApplicationProperties.getProperty("invalid_parameter"), ex.getName())
                                        ).build()))
                        .warnings(null).build())
                .build();
        logException(baseDTO, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<?> handleIllegalArgumentExceptions(IllegalArgumentException ex, HttpServletRequest request) {
        BaseDTO baseDTO = BaseDTO.builder().meta(
                MetaDTO.builder().errors(
                        BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                MetaMapDTO.builder()
                                        .key("unexpected_error")
                                        .message(ApplicationProperties.getProperty("unexpected_error"))
                                        .build()))
                        .warnings(null).build())
                .build();
        logException(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public final ResponseEntity<?> handleIllegalStateExceptions(IllegalStateException ex) {
        BaseDTO baseDTO = BaseDTO.builder().meta(
                MetaDTO.builder().errors(
                        BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                MetaMapDTO.builder()
                                        .key("request_timeout")
                                        .message(ex.getMessage())
                                        .build()))
                        .warnings(null).build())
                .build();
        logException(baseDTO, HttpStatus.REQUEST_TIMEOUT);
        return new ResponseEntity<>(baseDTO, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<?> handleAccessExceptions(AccessDeniedException ex, HttpServletRequest request) {
        BaseDTO baseDTO = BaseDTO.builder().meta(
                MetaDTO.builder().errors(
                        BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                MetaMapDTO.builder()
                                        .key("access_denied")
                                        .message(ApplicationProperties.getProperty("access_denied"))
                                        .build()))
                        .warnings(null).build())
                .build();
        logException(baseDTO, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(baseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Error.class)
    public final ResponseEntity<?> handleAllExceptions(Error ex) {
        BaseDTO baseDTO = BaseDTO.builder().meta(
                MetaDTO.builder().errors(
                        BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                MetaMapDTO.builder()
                                        .key("unexpected_error")
                                        .message(ApplicationProperties.getProperty("unexpected_error"))
                                        .build()))
                        .warnings(null).build())
                .build();
        logException(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        BaseDTO baseDTO = BaseDTO.builder().build();
        if (ex.getMostSpecificCause().getMessage().contains("not-null")) {
            baseDTO = BaseDTO.builder().meta(
                    MetaDTO.builder()
                            .errors(BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                    MetaMapDTO.builder()
                                            .key("missing_parameter")
                                            .message(String.format(
                                                    ApplicationProperties.getProperty("missing_parameter"),
                                                    (ex.getMostSpecificCause().getMessage().split("violates not-null")[0].split("\"")[1]))
                                            ).build()))
                            .warnings(null).build())
                    .build();
        } else {
            baseDTO = BaseDTO.builder().meta(
                    MetaDTO.builder()
                            .errors(BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                    MetaMapDTO.builder()
                                            .key("duplicate_unique_key")
                                            .message(String.format(
                                                    ApplicationProperties.getProperty("duplicate_unique_key"),
                                                    (ex.getMostSpecificCause().getMessage().split("Key")[1].split("=")[0]).replace("(", "").replace(")", ""))
                                            ).build()))
                            .warnings(null).build())
                    .build();
        }
        logException(baseDTO, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex) throws Exception {
        BaseDTO baseDTO = new ObjectMapper().readValue(ex.getStatusText(), BaseDTO.class);
        logException(baseDTO, ex.getStatusCode());
        return new ResponseEntity<>(baseDTO, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex) throws Exception {
        BaseDTO baseDTO=BaseDTO.builder().build();

        if(ex.getClass().getName().equalsIgnoreCase("org.springframework.web.HttpRequestMethodNotSupportedException"))
            baseDTO = BaseDTO.builder()
                    .meta(MetaDTO.builder()
                            .errors(BaseDTOMapper.getInstance().setMetaDTOCollection(null,
                                    MetaMapDTO.builder().key("405").message("Http POST Request Not Supported").build()))
                            .build())
                    .data(ex.getMessage())
                    .build();
        else
            baseDTO = new ObjectMapper().readValue((((HttpServerErrorException) ex).getResponseBodyAsString()), BaseDTO.class);

        logException(baseDTO, ((HttpServerErrorException) ex).getStatusCode());
        return new ResponseEntity<>(baseDTO, ((HttpServerErrorException) ex).getStatusCode());
    }

    private HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    private void logException(BaseDTO baseDTO, HttpStatus httpStatus) {

        BaseAuditItemRequest auditItemRequest = new BaseAuditItemRequest(
                Objects.requireNonNull(getCurrentHttpRequest()).getHeader("rrn"),
                AuditItemRequest
                        .builder()
                        .datetime(new Date().getTime())
                        .type(AuditType.EXCEPTION)
                        .result(baseDTO)
                        .microServiceName(ApplicationProperties.getProperty("spring.application.name"))
                        .path(Objects.requireNonNull(getCurrentHttpRequest()).getRequestURI())
                        .build());

        auditHandler.addAuditLog(auditItemRequest,Level.ERROR,httpStatus.value());
        LogBackUtility.getInstance(logger).log(auditItemRequest, Level.ERROR);
    }

}
*/
