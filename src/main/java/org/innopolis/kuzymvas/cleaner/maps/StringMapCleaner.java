package org.innopolis.kuzymvas.cleaner.maps;

import java.util.Collection;
import java.util.Map;

/**
 * Реализация интерфейса очистики таблицы для случая строкового ключа
 */
public class StringMapCleaner implements MapCleaner<String> {

    @Override
    public void clean(
            Map<String, ?> target, Collection<String> keysToClean, Collection<String> keysToOutput) {
        if (!verify(target, keysToClean)) {
            throw new IllegalArgumentException("Map doesn't contain key, which is listed to be cleaned");
        }
        if (!verify(target, keysToOutput)) {
            throw new IllegalArgumentException("Map doesn't contain key, which is listed to be output");
        }
        cleanKeys(target, keysToClean);
        outputKeys(target, keysToOutput);
    }

    @Override
    public boolean verify(Map<String, ?> target, Collection<String> keysToVerify) {
        for (String key : keysToVerify) {
            if (!target.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Заменяет значения в данной таблице соответствующие каждому ключу из данной коллекции
     * на null. (Таблица не может содержать значения примитивных типов)
     *
     * @param target      - таблица
     * @param keysToClean - коллекция ключей для очистки
     */
    private void cleanKeys(Map<String, ?> target, Collection<String> keysToClean) {

        for (String key : keysToClean) {
            target.replace(key, null);
        }
    }

    /**
     * Выводит в консоль значения в данной таблице соответствующие каждому ключу из данной коллекции
     *
     * @param target       - таблица
     * @param keysToOutput - коллекция ключей для вывода на консоль
     */
    private void outputKeys(Map<String, ?> target, Collection<String> keysToOutput) {
        for (String key : keysToOutput) {
            Object value = target.get(key);
            if (value != null) {
                System.out.println(
                        "Key: " + key + ", Value: " + target.get(key).toString()
                );
            } else {
                System.out.println(
                        "Key: " + key + ", Value is null"
                );
            }
        }
    }
}
