package org._3HCompany.microservice.common.models.enums;

public enum Operators {
    EQUAL, NOTEQUAL, ASCENDING, DESCENDING, GREATER_THAN, GREATER_THAN_EQUAL, LIKE,
    LESS_THAN, LESS_THAN_EQUAL, BETWEEN,IN;
    public static final String[] OPERATORS = {"like","eq", "neq", "asc", "desc", "gr", "gre", "ls", "lse","in"};

    public static Operators getOperator(String input) {
        return switch (input) {
            case "like" -> LIKE;
            case "eq" -> EQUAL;
            case "neq" -> NOTEQUAL;
            case "gr" -> GREATER_THAN;
            case "gre" -> GREATER_THAN_EQUAL;
            case "ls" -> LESS_THAN;
            case "lse" -> LESS_THAN_EQUAL;
            case "bet" -> BETWEEN;
            case "asc" -> ASCENDING;
            case "desc" -> DESCENDING;
            case "in" -> IN;
            default -> null;
        };
    }
}
