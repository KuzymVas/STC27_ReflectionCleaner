package org.innopolis.kuzymvas.cleaner.fields;

import java.lang.reflect.Field;

/**
 * Интерфейс очистителя полей. Решает подзадачи очистки и вывода в консоль для отдельного поля
 */
public interface FieldCleaner {

    /**
     * Очищает заданное поле в заданном объекте (сбрасывает ссылочные поля в null,
     * а примитивные в значение по умолчанию)
     *
     * @param target - объект для изменения
     * @param field  - поле для изменения
     */
    void clean(Object target, Field field);

    /**
     * Выводит имя и значение заданного поля в заданном объекте в консоль
     *
     * @param target - объект
     * @param field  - поле
     */
    void output(Object target, Field field);
}
