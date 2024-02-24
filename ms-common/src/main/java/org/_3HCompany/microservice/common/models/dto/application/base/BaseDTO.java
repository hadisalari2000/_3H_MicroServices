package org._3HCompany.microservice.common.models.dto.application.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org._3HCompany.microservice.common.models.enums.ColumnEnum;
import org._3HCompany.microservice.common.models.enums.RequestTypeEnum;
import org._3HCompany.microservice.common.models.enums.TableEnum;
import org._3HCompany.microservice.common.util.ApplicationProperties;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO<T> {

    private String[] errorMessages;
    private String[] hiddenMessages;
    private String[] informationMessages;
    private Boolean isSuccess;

    private T data;

    public static BaseDTO getSuccess(TableEnum tableEnum, RequestTypeEnum... requestType) {
        return BaseDTO.builder()
                .isSuccess(true)
                .informationMessages(new String[]{getSuccessMessage(tableEnum, requestType)})
                .build();
    }

    public static BaseDTO getError(String... errorMessage) {
        return BaseDTO.builder()
                .isSuccess(false)
                .errorMessages(errorMessage)
                .data(null)
                .build();
    }

    public static BaseDTO getNotFoundError(TableEnum tableName, String... args) {

        String[] errorMessage;
        errorMessage = new String[(args != null ? (args.length / 2) : 0) + 1];
        int counter = 0;

        if (args != null && args.length > 0)
            for (int i = 0; i < args.length / 2; i++) {
                errorMessage[counter] = String.format(
                        ApplicationProperties.getProperty("not-found-error-message-detail-3"),
                        tableName.getDescription(),
                        args[counter * 2],
                        args[(counter * 2) + 1]);
                counter++;
            }
        else
            errorMessage[0] = String.format(
                    ApplicationProperties.getProperty("not-found-error-message-detail-1"), tableName.getDescription());

        return getError(errorMessage);
    }

    public static BaseDTO getDuplicateError(TableEnum tableName, List<String> errors) {

        String[] errorMessage;
        errorMessage = new String[errors!=null?errors.size():1];
        int counter = 0;

        if (errors!=null)
            for (String arg : errors) {
                if (arg.equals(ColumnEnum.PASSWORD.getTitle()))
                    errorMessage[counter] = ApplicationProperties.getProperty("confirm-password-error-message");

                else
                    errorMessage[counter] = String.format(
                            ApplicationProperties.getProperty("duplicate-error-message-detail-1"), arg);
                counter++;
            }
        else {
            String message = String.format(
                    ApplicationProperties.getProperty("duplicate-error-message-detail-1"), tableName.getDescription());
            return getError(message);
        }
        return getError(errorMessage);
    }

    public static BaseDTO getRequiredError(String... args) {

        String[] errorMessage = new String[args.length + 1];
        int counter = 0;

        for (String arg : args) {
            errorMessage[counter] = String.format(
                    ApplicationProperties.getProperty("required-error-message-detail-1"),
                    arg);
            counter++;
        }
        return getError(errorMessage);
    }

    public static BaseDTO getException(String errorMessage, String[] technicalErrors) {
        return BaseDTO.builder()
                .isSuccess(false)
                .data(null)
                .errorMessages(new String[]{errorMessage})
                .hiddenMessages(technicalErrors)
                .build();
    }

    public static BaseDTO getException(String errorMessage, StackTraceElement[] technicalErrors) {
        String[] hiddenMessage=(String[]) Arrays.stream(technicalErrors)
                .map(StackTraceElement::toString)
                .toArray();

        return BaseDTO.builder()
                .isSuccess(false)
                .data(null)
                .errorMessages(new String[]{errorMessage})
                .hiddenMessages(hiddenMessage)
                .build();
    }



    /*-----------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------*/
    /*-----------------------------------------------------------------------*/

    private static String getSuccessMessage(TableEnum tableEnum, RequestTypeEnum... requestTypes) {
        for (RequestTypeEnum requestType : requestTypes) {
            return switch (requestType) {
                case INSERT ->
                        String.format(ApplicationProperties.getProperty("insert-successful"), tableEnum.getTitle());
                case UPDATE ->
                        String.format(ApplicationProperties.getProperty("update-successful"), tableEnum.getTitle());
                case DELETE ->
                        String.format(ApplicationProperties.getProperty("delete-successful"), tableEnum.getTitle());
                default -> String.format(ApplicationProperties.getProperty("successful"), tableEnum.getTitle());
            };
        }

        return ApplicationProperties.getProperty("successful");
    }

}
