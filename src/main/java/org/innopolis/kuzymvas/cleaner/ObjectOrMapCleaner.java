package org.innopolis.kuzymvas.cleaner;

import org.innopolis.kuzymvas.cleaner.fields.TypesAwareFieldCleaner;
import org.innopolis.kuzymvas.cleaner.maps.MapCleaner;
import org.innopolis.kuzymvas.cleaner.maps.StringMapCleaner;

import java.util.Collection;
import java.util.Map;

/**
 * Реализация - декоратор для очистителя объектов.
 * Определяет является ли объект таблицей и передает его либо очистителю таблиц,
 * либо другому очистителю объектов
 */
public class ObjectOrMapCleaner implements  Cleaner{

    private final MapCleaner<String> mapCleaner;
    private final Cleaner objectCleaner;

    /**
     * Создает очиститель в соответствии с постановкой задачи.
     */
    public ObjectOrMapCleaner() {
        this.mapCleaner = new StringMapCleaner();
        this.objectCleaner = new ObjectFieldsCleaner(new TypesAwareFieldCleaner());
    }

    /**
     *  Создает очиститель с произвольными реализациями алгоритмов очистики для таблиц и объектов-не-таблиц
     * @param mapCleaner - реализация очистителя таблиц
     * @param objectCleaner - реализация очистителя объектов-не-таблицы
     */
    public ObjectOrMapCleaner(MapCleaner<String> mapCleaner, Cleaner objectCleaner) {
        this.mapCleaner = mapCleaner;
        this.objectCleaner = objectCleaner;
    }

    @Override
    public void clean(
            Object target, Collection<String> namesToClean, Collection<String> namesToOutput) {
        if (target instanceof Map) {
            mapCleaner.clean((Map<String, ?>)target,namesToClean,namesToOutput);
        } else {
            objectCleaner.clean(target, namesToClean, namesToOutput);
        }

    }

    @Override
    public boolean verify(Object target, Collection<String> namesToVerify) {
        if (target instanceof Map) {
           return mapCleaner.verify((Map<String, ?>) target, namesToVerify);
        } else {
            return objectCleaner.verify(target, namesToVerify);
        }
    }
}
