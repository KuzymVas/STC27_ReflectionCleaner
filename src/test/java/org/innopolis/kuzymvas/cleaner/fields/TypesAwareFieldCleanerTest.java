package org.innopolis.kuzymvas.cleaner.fields;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

public class TypesAwareFieldCleanerTest {

    @Test
    public void cleanFieldTest() {
        TypesAwareFieldCleaner cleaner = new TypesAwareFieldCleaner();
        Struct struct = new Struct();
        Field[] fields = Struct.class.getDeclaredFields();
        for (Field field : fields) {
            cleaner.clean(struct, field);
        }
        Assert.assertEquals("Failed to clean public integer field", 0, struct.pbInt);
        Assert.assertEquals("Failed to clean private integer field", 0, struct.prInt);
        Assert.assertEquals("Failed to clean public long field", 0, struct.pbLong);
        Assert.assertEquals("Failed to clean private long field", 0, struct.prLong);
        Assert.assertEquals("Failed to clean public byte field", 0, struct.pbByte);
        Assert.assertEquals("Failed to clean private byte field", 0, struct.prByte);
        Assert.assertEquals("Failed to clean public short field", 0, struct.pbShort);
        Assert.assertEquals("Failed to clean private short field", 0, struct.prShort);
        Assert.assertEquals("Failed to clean public character field", 0, struct.pbChar);
        Assert.assertEquals("Failed to clean private character field", 0, struct.prChar);
        Assert.assertEquals("Failed to clean public float field", 0.0, struct.pbFloat, 0.0000001);
        Assert.assertEquals("Failed to clean private float field", 0.0, struct.prFloat, 0.0000001);
        Assert.assertEquals("Failed to clean public double field", 0.0, struct.pbDouble, 0.0000001);
        Assert.assertEquals("Failed to clean private double field", 0.0, struct.prSDouble, 0.0000001);
        Assert.assertFalse("Failed to clean public boolean field", struct.pbBoolean);
        Assert.assertFalse("Failed to clean private boolean field", struct.prBoolean);
        Assert.assertNull("Failed to clean public Object field", struct.pbObject);
        Assert.assertNull("Failed to clean private Object field", struct.prObject);
        Assert.assertNull("Failed to clean private final field", struct.pfObject);
    }

    @Test
    public void outputFieldTest() {
        TypesAwareFieldCleaner cleaner = new TypesAwareFieldCleaner();
        Struct struct = new Struct();
        Field[] fields = Struct.class.getDeclaredFields();
        for (Field field : fields) {
            cleaner.output(struct, field);
        }
        NullStruct nullStruct = new NullStruct();
        Field[] nullFields = NullStruct.class.getDeclaredFields();
        for (Field field : nullFields) {
            cleaner.output(nullStruct, field);
        }
    }

    private static class Struct {
        private final Object pfObject = new Object();
        public int pbInt = 1;
        public long pbLong = 1;
        public byte pbByte = 1;
        public short pbShort = 1;
        public char pbChar = 1;
        public float pbFloat = 1.f;
        public double pbDouble = 1.;
        public boolean pbBoolean = true;
        public Object pbObject = new Object();
        private final int prInt = 2;
        private final long prLong = 2;
        private final byte prByte = 2;
        private final short prShort = 2;
        private final char prChar = 2;
        private final float prFloat = 2.f;
        private final double prSDouble = 2.;
        private final boolean prBoolean = true;
        private final Object prObject = new Object();
    }

    private static class NullStruct {
        private final Object nullField = null;
    }
}