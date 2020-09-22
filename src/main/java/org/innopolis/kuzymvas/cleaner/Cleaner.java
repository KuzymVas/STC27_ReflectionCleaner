package org.innopolis.kuzymvas.cleaner;

import java.util.Collection;

/**
 * Интерфейс для решения задачи очистки некоего объекта. Включает в себя метод verify для верификации доступности
 * для очистки всех имен в коллекции. Вызов очистки для коллекции имен, не проходящих верификацию приведет
 * к выбросу исключения IllegalArgumentException
 */
public interface Cleaner {

    /**
     * Выполняет очистку и вывод компонентов заданного объекта в соответствии с двумя наборами имен
     * @param target - заданный объект. Остается неизменным при выбросе исключения данным методом
     * @param namesToClean - набор имен для очистки
     * @param namesToOutput - набор имен для вывода
     * @throws IllegalArgumentException - если любой из наборов имен не проходит верификацию
     */
    void clean(Object target, Collection<String> namesToClean, Collection<String> namesToOutput) throws  IllegalArgumentException;

    /**
     *  Проверяет коллекцию строк на возможность очистки/вывода соответствующих имен у заданного объекта
     * @param target - объект для потенциальной очистки/вывода
     * @param namesToVerify - имена для потенциальной очистки/вывода
     * @return - true, если все имена доступны для очистки/вывода, false в противном случае
     */
    boolean verify(Object target, Collection<String> namesToVerify);
}
