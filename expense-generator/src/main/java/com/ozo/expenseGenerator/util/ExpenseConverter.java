package com.ozo.expenseGenerator.util;

import com.ozo.expenseGenerator.model.Expense;

public class ExpenseConverter {

    /**
     * Converts an Expense object to a JSON string format suitable for Kafka and Spark processing.
     *
     * @param expense The Expense object to be converted.
     * @return A JSON string representation of the Expense object.
     */
    public static String toJson(String id, Expense expense) {
        return String.format("{\"id\":\"%s\",\"userId\":%d,\"dateTime\":\"%s\",\"description\":\"%s\",\"type\":\"%s\",\"count\":%d,\"payment\":%f}",
                id,
                expense.getUserId(),
                expense.getDateTime(),
                expense.getDescription(),
                expense.getType(),
                expense.getCount(),
                expense.getPayment()
        );
    }

    /**
     * Converts an Expense object to a string format suitable for Kafka and Spark processing.
     *
     * @param expense The Expense object to be converted.
     * @return A delimited string representation of the Expense object.
     */
    public static String toDelimitedString(Expense expense) {
        return String.join(",",
                String.valueOf(expense.getUserId()),
                expense.getDateTime(),
                escapeComma(expense.getDescription()),
                expense.getType(),
                String.valueOf(expense.getCount()),
                String.valueOf(expense.getPayment())
        );
    }

    /**
     * Escapes commas in text fields to ensure the integrity of the delimited format.
     *
     * @param input The string to escape.
     * @return The escaped string.
     */
    private static String escapeComma(String input) {
        return input.replace(",", "\\,");
    }
}
