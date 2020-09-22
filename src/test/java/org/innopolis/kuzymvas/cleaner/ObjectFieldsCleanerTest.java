package org.innopolis.kuzymvas.cleaner;

import org.innopolis.kuzymvas.cleaner.fields.FieldCleaner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public class ObjectFieldsCleanerTest {

    private static  class Struct {
        private int A = 42;
        public double B = 42.0;
        String S = "Test";
    }

    private FieldCleaner mockFieldCleaner;
    private ObjectFieldsCleaner cleaner;
    private Collection<String> goodListOf2;
    private Collection<String> goodListOf3;
    private Collection<String> badListMixed;
    private Collection<String> badListAll;
    private Struct testStruct;

    @Before
    public void setUp() {
        mockFieldCleaner = Mockito.mock(FieldCleaner.class);
        cleaner = new ObjectFieldsCleaner(mockFieldCleaner);
        goodListOf2 = Arrays.asList("A","S");
        goodListOf3 = Arrays.asList("A","B","S");
        badListMixed = Arrays.asList("A","D","S");
        badListAll = Arrays.asList("D","E");
        testStruct = new Struct();
    }

    @Test
    public void testVerification() {
        Assert.assertTrue("Cleaner failed to verify list of present fields", cleaner.verify(testStruct, goodListOf2));
        Assert.assertTrue("Cleaner failed to verify list of present fields", cleaner.verify(testStruct, goodListOf3));
        Assert.assertFalse("Cleaner verified list containing missing fields", cleaner.verify(testStruct, badListMixed));
        Assert.assertFalse("Cleaner verified list of missing fields", cleaner.verify(testStruct, badListAll));
    }

    @Test
    public void testNormalCleanup() {
        cleaner.clean(testStruct, goodListOf2, goodListOf3);
        Mockito.verify(mockFieldCleaner, Mockito.times(2)).clean(Mockito.eq(testStruct), Mockito.any(Field.class));
        Mockito.verify(mockFieldCleaner, Mockito.times(3)).output(Mockito.eq(testStruct), Mockito.any(Field.class));
        Mockito.clearInvocations(mockFieldCleaner);
        cleaner.clean(testStruct, goodListOf3, goodListOf2);
        Mockito.verify(mockFieldCleaner, Mockito.times(3)).clean(Mockito.eq(testStruct), Mockito.any(Field.class));
        Mockito.verify(mockFieldCleaner, Mockito.times(2)).output(Mockito.eq(testStruct), Mockito.any(Field.class));
    }

    @Test
    public void testNegativeCleanup() {
        try {
            cleaner.clean(testStruct, badListAll, badListAll);
            Assert.fail("Cleaner didn't throw exception, when presented with two unverifiable collections");
        } catch(IllegalArgumentException e) {
            Mockito.verify(mockFieldCleaner, Mockito.times(0)).clean(Mockito.eq(testStruct), Mockito.any(Field.class));
            Mockito.verify(mockFieldCleaner, Mockito.times(0)).output(Mockito.eq(testStruct), Mockito.any(Field.class));
        }
        try {
            cleaner.clean(testStruct, badListMixed, goodListOf3);
            Assert.fail("Cleaner didn't throw exception, when presented with  unverifiable collections as first collection argument");
        } catch(IllegalArgumentException e) {
            Mockito.verify(mockFieldCleaner, Mockito.times(0)).clean(Mockito.eq(testStruct), Mockito.any(Field.class));
            Mockito.verify(mockFieldCleaner, Mockito.times(0)).output(Mockito.eq(testStruct), Mockito.any(Field.class));
        }
        try {
            cleaner.clean(testStruct, goodListOf2, badListMixed);
            Assert.fail("Cleaner didn't throw exception, when presented with  unverifiable collection as second collection argument");
        } catch(IllegalArgumentException e) {
            Mockito.verify(mockFieldCleaner, Mockito.times(0)).clean(Mockito.eq(testStruct), Mockito.any(Field.class));
            Mockito.verify(mockFieldCleaner, Mockito.times(0)).output(Mockito.eq(testStruct), Mockito.any(Field.class));
        }
    }


}