package org.innopolis.kuzymvas.cleaner.fields;

import java.lang.reflect.Field;

/**
 * Реализация очистителя полей, способная работать, как с ссылочными, так и с примитивными типами полей.
 */
public class TypesAwareFieldCleaner implements FieldCleaner {
    @Override
    public void clean(Object target, Field field) {
        Class<?> fieldClass = field.getType();
        try {
            field.setAccessible(true);
            if (fieldClass.equals(Integer.TYPE)) {
                field.setInt(target, 0);
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Long.TYPE)) {
                field.setLong(target, 0);
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Byte.TYPE)) {
                field.setByte(target, (byte) 0);
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Short.TYPE)) {
                field.setShort(target, (short) 0);
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Character.TYPE)) {
                field.setChar(target, (char) 0);
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Float.TYPE)) {
                field.setFloat(target, 0.0f);
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Double.TYPE)
            ) {
                field.setDouble(target, 0.0);
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Boolean.TYPE)) {
                field.setBoolean(target, false);
                field.setAccessible(false);
                return;
            }
            field.set(target, null);
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            System.out.println("Не сработал вызов setAccessible для получения доступа к полю: " + field.getName()
                                       + ". Очистка поля невозможна.");
        }
    }

    @Override
    public void output(Object target, Field field) {
        Class<?> fieldClass = field.getType();
        try {
            field.setAccessible(true);
            if (fieldClass.equals(Integer.TYPE)) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + field.getInt(target));
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Long.TYPE)) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + field.getLong(target));
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Byte.TYPE)) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + String.valueOf(field.getByte(target)));
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Short.TYPE)) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + String.valueOf(field.getShort(target)));
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Character.TYPE)) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + field.getChar(target));
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Double.TYPE)) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + field.getDouble(target));
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Float.TYPE)) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + field.getFloat(target));
                field.setAccessible(false);
                return;
            }
            if (fieldClass.equals(Boolean.TYPE)) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + field.getBoolean(target));
                field.setAccessible(false);
                return;
            }
            Object value = field.get(target);
            if (value != null) {
                System.out.println("Name: " + field.getName()
                                           + ", Value: " + value.toString());
            } else {
                System.out.println("Name: " + field.getName()
                                           + ", Value is null");
            }
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            System.out.println("Не сработал вызов setAccessible для получения доступа к полю: " + field.getName()
                                       + ". Вывод поля невозможна.");
        }
    }
}
