package org.innopolis.kuzymvas.cleaner.maps;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StringMapCleanerTest {

    private StringMapCleaner cleaner;
    private Collection<String> goodListOf2;
    private Collection<String> goodListOf3;
    private Collection<String> badListMixed;
    private Collection<String> badListAll;
    private Map<String, String> map;

    @Before
    public void setUp() {
        cleaner = new StringMapCleaner();
        map = new HashMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("S", "3");
        goodListOf2 = Arrays.asList("A", "S");
        goodListOf3 = Arrays.asList("A", "B", "S");
        badListMixed = Arrays.asList("A", "D", "S");
        badListAll = Arrays.asList("D", "E");
    }

    @Test
    public void testVerification() {
        Assert.assertTrue("Cleaner failed to verify list of present keys", cleaner.verify(map, goodListOf2));
        Assert.assertTrue("Cleaner failed to verify list of present keys", cleaner.verify(map, goodListOf3));
        Assert.assertFalse("Cleaner verified list containing missing keys", cleaner.verify(map, badListMixed));
        Assert.assertFalse("Cleaner verified list of missing keys", cleaner.verify(map, badListAll));
    }

    @Test
    public void testNormalCleanup() {
        cleaner.clean(map, goodListOf2, goodListOf3);
        Assert.assertNull("Listed map value wasn't cleaned", map.get("A"));
        Assert.assertEquals("Not listed map value was changed", "2", map.get("B"));
        Assert.assertNull("Listed map value wasn't cleaned", map.get("S"));
        cleaner.clean(map, goodListOf3, goodListOf2);
        Assert.assertNull("Listed map value wasn't cleaned", map.get("A"));
        Assert.assertNull("Listed map value wasn't cleaned", map.get("B"));
        Assert.assertNull("Listed map value wasn't cleaned", map.get("S"));
        }

    @Test
    public void testNegativeCleanup() {
        try {
            cleaner.clean(map, badListAll, badListAll);
            Assert.fail("Cleaner didn't throw exception, when presented with two unverifiable collections");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("One of the map values was changed after exception was thrown", "1", map.get("A"));
            Assert.assertEquals("One of the map values was changed after exception was thrown", "2", map.get("B"));
            Assert.assertEquals("One of the map values was changed after exception was thrown", "3", map.get("S"));
        }
        try {
            cleaner.clean(map, badListMixed, goodListOf3);
            Assert.fail(
                    "Cleaner didn't throw exception, when presented with  unverifiable collections as first collection argument");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("One of the map values was changed after exception was thrown", "1", map.get("A"));
            Assert.assertEquals("One of the map values was changed after exception was thrown", "2", map.get("B"));
            Assert.assertEquals("One of the map values was changed after exception was thrown", "3", map.get("S"));
        }
        try {
            cleaner.clean(map, goodListOf2, badListMixed);
            Assert.fail(
                    "Cleaner didn't throw exception, when presented with  unverifiable collection as second collection argument");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("One of the map values was changed after exception was thrown", "1", map.get("A"));
            Assert.assertEquals("One of the map values was changed after exception was thrown", "2", map.get("B"));
            Assert.assertEquals("One of the map values was changed after exception was thrown", "3", map.get("S"));
        }
    }
}