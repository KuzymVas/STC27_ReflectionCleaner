package org.innopolis.kuzymvas.cleaner.maps;

import java.util.Collection;
import java.util.Map;

/**
 * Интерфейс для решения задачи очистки и вывода таблицы с заданным типом ключа
 * ключает в себя метод verify для верификации доступности для очистки всех ключей в коллекции.
 * Вызов очистки для коллекции ключей, не проходящих верификацию приведет
 * к выбросу исключения IllegalArgumentException
 *
 * @param <K>- тип ключа таблицы
 */
public interface MapCleaner<K> {

    /**
     * Выполняет очистку и вывод значений в таблице в соответствии с заданными ключами
     *
     * @param target       - таблица для очистки и вывода
     * @param keysToClean  - коллекция ключей для очистки
     * @param keysToOutput - коллекция ключей для вывода
     */
    void clean(Map<K, ?> target, Collection<K> keysToClean, Collection<K> keysToOutput);

    /**
     * Проверяет коллекцию ключей на наличие их в данной таблице
     *
     * @param target       - таблица
     * @param keysToVerify - коллекция ключей
     * @return - true, если все ключи из коллекции содержатся в таблице, false - в противном случае
     */
    boolean verify(Map<K, ?> target, Collection<K> keysToVerify);
}
