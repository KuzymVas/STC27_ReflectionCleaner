package org.innopolis.kuzymvas.cleaner;

import org.innopolis.kuzymvas.cleaner.fields.FieldCleaner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейс очистки объекта путем очистки\вывода его полей по именам
 */
public class ObjectFieldsCleaner implements Cleaner {

    private final FieldCleaner fieldCleaner;

    /**
     * Создает очиститель объектов, использующий заданный очиститель полей
     *
     * @param fieldCleaner - очиститель полей
     */
    public ObjectFieldsCleaner(FieldCleaner fieldCleaner) {
        this.fieldCleaner = fieldCleaner;
    }

    @Override
    public void clean(
            Object target, Collection<String> namesToClean, Collection<String> namesToOutput) {
        if (!verify(target, namesToClean)) {
            throw new IllegalArgumentException("Object doesn't contain field, which is listed to be cleaned");
        }
        if (!verify(target, namesToOutput)) {
            throw new IllegalArgumentException("Map doesn't contain field, which is listed to be output");
        }
        Class<?> targetClass = target.getClass();
        for (String name : namesToClean) {
            try {
                fieldCleaner.clean(target, targetClass.getDeclaredField(name));
            } catch (NoSuchFieldException e) {
                System.out.println("Error. Field was not found even after verification have approved it.");
                throw new IllegalArgumentException("Object doesn't contain field, which is listed to be cleaned");
            }
        }
        for (String name : namesToOutput) {
            try {
                fieldCleaner.output(target, targetClass.getDeclaredField(name));
            } catch (NoSuchFieldException e) {
                System.out.println("Error. Field was not found even after verification have approved it.");
                throw new IllegalArgumentException("Map doesn't contain field, which is listed to be output");
            }
        }
    }

    /**
     * Выполняет верификацию путем проверки наличия данных имен среди объявленных полей объекта
     *
     * @param target        - объект для потенциальной очистки/вывода
     * @param namesToVerify - имена для потенциальной очистки/вывода
     * @return- true, если все имена являются полями объекта
     */
    @Override
    public boolean verify(Object target, Collection<String> namesToVerify) {
        Class<?> targetClass = target.getClass();
        List<Field> fields = Arrays.asList(targetClass.getDeclaredFields());
        List<String> fieldNames = new ArrayList<>();
        fields.stream().map(Field::getName).collect(Collectors.toCollection(() -> fieldNames));
        for (String name : namesToVerify) {
            if (!fieldNames.contains(name)) {
                return false;
            }
        }
        return true;
    }
}
