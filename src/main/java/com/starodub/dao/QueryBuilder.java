package com.starodub.dao;

import com.starodub.model.ColumnName;
import com.starodub.model.TableName;

import java.lang.reflect.Field;

public class QueryBuilder<T> {

    private Class<?> clazz;
    private int valuesCounter;

    public QueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    public QueryBuilder() {
    }

    public String findByIdQuery(Class<?> clazz) {
        return "SELECT * FROM " + getTableName(clazz) + " WHERE ID = ?;";
    }

    public String findAllQuery(Class<?> clazz) {
        return "SELECT * FROM " + getTableName(clazz);
    }

    public String insertQuery(Class<?> clazz) {
        return "INSERT INTO " + getTableName(clazz) + " (" + getClassFieldsForInsert() + ")" +
                " VALUES (" + getQuestionMarks() + ");";
    }
    public String updateQuery(Class<?> clazz) {
        return "UPDATE " + getTableName(clazz) + " SET " + getClassFieldsForUpdate() + " WHERE ID = ?;";
    }

    public String deleteQuery(Class<?> clazz) {
        return "DELETE FROM " + getTableName(clazz) + " WHERE ID = ?;";
    }

    private String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(TableName.class)) {
            TableName table = clazz.getAnnotation(TableName.class);
            return table.name().toUpperCase();
        } else {
            return "Table name NOT FOUND";
        }
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(ColumnName.class)) {
            ColumnName columnName = field.getAnnotation(ColumnName.class);
            return columnName.name();
        } else {
            return "Column NOT FOUND";
        }
    }

    private String getClassFieldsForInsert() {
        Field[] fields;
        fields = clazz.getDeclaredFields();
        StringBuilder fieldsBuilder = new StringBuilder();

        for (int i = 0; i < fields.length; ++i) {
            if (fields[i].isAnnotationPresent(ColumnName.class) && !(getColumnName(fields[i]).equals("ID"))) {
                fieldsBuilder.append(getColumnName(fields[i]));
                valuesCounter++;
                fieldsBuilder.append(", ");
            }
        }
        fieldsBuilder.setLength(fieldsBuilder.length() - 2);

        return fieldsBuilder.toString().toUpperCase();

    }

    private String getClassFieldsForUpdate() {
        Field[] fields;
        fields = clazz.getDeclaredFields();
        StringBuilder fieldsBuilder = new StringBuilder();

        for (int i = 0; i < fields.length; ++i) {
            if ((fields[i].isAnnotationPresent(ColumnName.class)) && !(getColumnName(fields[i]).equals("ID"))) {
                fieldsBuilder.append(getColumnName(fields[i]));
                fieldsBuilder.append(" = ?, ");
            }
        }
        fieldsBuilder.setLength(fieldsBuilder.length() - 2);

        return fieldsBuilder.toString().toUpperCase();

    }

    private String getQuestionMarks() {
        StringBuilder questionMarks = new StringBuilder();
        for (int i = 0; i < valuesCounter; i++) {
            questionMarks.append("?, ");
        }
        questionMarks.setLength(questionMarks.length() - 2);
        valuesCounter = 0;

        return questionMarks.toString();
    }
}
