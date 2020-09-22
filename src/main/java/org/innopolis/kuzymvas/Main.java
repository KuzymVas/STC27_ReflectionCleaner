package org.innopolis.kuzymvas;

import org.innopolis.kuzymvas.cleaner.Cleaner;
import org.innopolis.kuzymvas.cleaner.ObjectOrMapCleaner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Cleaner cleaner = new ObjectOrMapCleaner();

        Object objectTest = new ObjectTest();
        System.out.println("Cleaning an object:");
        cleaner.clean(objectTest, Arrays.asList("value", "A", "aDouble"), Arrays.asList("number", "value", "aDouble"));
        Map<String, String> map = new HashMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");
        map.put("D", "4");
        map.put("E", "5");
        Object mapObject = map;
        System.out.println("Cleaning a map:");
        cleaner.clean(mapObject, Arrays.asList("A", "B", "C"), Arrays.asList("A", "C", "E"));
    }

    private static class ObjectTest {

        private boolean value = true;
        private String A = "A";
        private String B = "B";
        public int number = 43;
        protected final Double aDouble = 4.2;
    }
}
