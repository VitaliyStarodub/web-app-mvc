package com.starodub.dao;

import com.starodub.model.ColumnName;
import com.starodub.model.SeparateModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PrepareStatementsBuilder<T, ID> {

    private Class<?> clazz;

    public PrepareStatementsBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    public PrepareStatementsBuilder() {
    }

    public T getObjectFromResultSet(ResultSet resultSet, Class<?> clazz) {
        Object newObj = null;
        Field[] fields;
        Map<String, String> resultSetMap = new LinkedHashMap<>();

        try {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                resultSetMap.put(resultSet.getMetaData().getColumnName(i), resultSet.getMetaData().getColumnClassName(i));
            }
        } catch (SQLException e) {
            System.out.println("Cant't put data from resultSet to Map");
        }

        try {
            newObj = clazz.getDeclaredConstructor().newInstance();
            fields = newObj.getClass().getDeclaredFields();

            for(int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                for (Map.Entry<String, String> pair: resultSetMap.entrySet()) {
                    if((pair.getKey().equals(getColumnName(fields[i]))) && !(fields[i].isAnnotationPresent(SeparateModel.class))) {
                        if(pair.getValue().equals(String.class.getTypeName())) {
                            fields[i].set(newObj, resultSet.getString(i + 1));
                        }
                        if(pair.getValue().equals(Long.class.getTypeName())) {
                            fields[i].set(newObj, resultSet.getLong(i + 1));
                        }
                        if(pair.getValue().equals(Integer.class.getTypeName())) {
                            fields[i].set(newObj, resultSet.getInt(i + 1));
                        }
                        if(pair.getValue().equals(BigDecimal.class.getTypeName())) {
                            fields[i].set(newObj, resultSet.getDouble(i + 1));
                        }else {
                            fields[i].set(newObj, resultSet.getObject(i + 1));
                        }
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (T)newObj;
    }

    public void prepareStatementForInsert(PreparedStatement statement, T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();

        for(Field field: fields) {
            field.setAccessible(true);
            if((field.isAnnotationPresent(ColumnName.class)) && !(getColumnName(field).equals("ID"))){
                fieldList.add(field);
            }
        }

        setFieldsForInsert(statement, object, fieldList);
    }

    public void prepareStatementForUpdate(PreparedStatement statement, T object, ID id) {
        Field[] fields = object.getClass().getDeclaredFields();
        Field fieldID = null;
        List<Field> fieldList = new ArrayList<>();

        for(Field field: fields) {
            field.setAccessible(true);
            if(getColumnName(field).equals("ID")) {
                fieldID = field;
            } else if(field.isAnnotationPresent(ColumnName.class)){
                fieldList.add(field);
            }
        }
        fieldList.add(fieldID);

        setFieldsForUpdate(statement, object, fieldList, id);
    }

    private String getColumnName(Field field) {
        if(field.isAnnotationPresent(ColumnName.class)) {
            ColumnName columnName = field.getAnnotation(ColumnName.class);
            return columnName.name();
        }else {
            return "Column NOT FOUND";
        }
    }

    private void setFieldsForUpdate(PreparedStatement statement, T object, List<Field> fieldList, ID id) {
        try{
            for (int i = 0; i < fieldList.size(); i++) {
                if(i == fieldList.size() - 1) {
                    statement.setObject(i + 1, id);
                } else {
                    statement.setObject(i + 1, fieldList.get(i).get(object));
                }
            }
        } catch (SQLException e) {
            System.out.println("Can't set for update object");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setFieldsForInsert(PreparedStatement statement, T object, List<Field> fieldList) {
        try{
            for (int i = 0; i < fieldList.size(); i++) {
                if((fieldList.get(i).getType().getName()).equals(String.class.getName())) {
                    statement.setString(i + 1, (String) fieldList.get(i).get(object));
                }
                if((fieldList.get(i).getType().getName()).equals(Long.class.getName())) {
                    statement.setLong(i + 1, (Long) fieldList.get(i).get(object));
                }
                if((fieldList.get(i).getType().getName()).equals(Double.class.getName())) {
                    statement.setDouble(i + 1, (Double) fieldList.get(i).get(object));
                } else {
                    statement.setObject(i + 1, fieldList.get(i).get(object));
                }

            }
        } catch (SQLException e) {
            System.out.println("Can't set for update object");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
