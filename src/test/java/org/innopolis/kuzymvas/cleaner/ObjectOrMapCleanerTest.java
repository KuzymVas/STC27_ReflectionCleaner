package org.innopolis.kuzymvas.cleaner;

import org.innopolis.kuzymvas.cleaner.maps.MapCleaner;
import org.innopolis.kuzymvas.cleaner.maps.StringMapCleaner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

public class ObjectOrMapCleanerTest {

    private Cleaner objectCleaner;
    private ObjectOrMapCleaner cleaner;
    private MapCleaner<String> mapCleaner;
    Object notMap;
    Map<String,String> map;
    Object alsoMap;
    List<String> empty;

    @Before
    public void setUp() {
      mapCleaner = Mockito.mock(StringMapCleaner.class);
      objectCleaner = Mockito.mock(Cleaner.class);
      cleaner = new ObjectOrMapCleaner(mapCleaner, objectCleaner);
      notMap = new Object();
      map = new HashMap<>();
      alsoMap = new TreeMap<String,String>();
      empty = new ArrayList<>();
    }

    @Test
    public void testVerify() {
        cleaner.verify(notMap, empty);
        Mockito.verify(mapCleaner, Mockito.times(0)).verify(Mockito.anyMap(), Mockito.anyCollection());
        Mockito.verify(objectCleaner, Mockito.times(1)).verify(Mockito.any(), Mockito.anyCollection());
        Mockito.clearInvocations(mapCleaner,objectCleaner);
        cleaner.verify(map, empty);
        Mockito.verify(mapCleaner, Mockito.times(1)).verify(Mockito.anyMap(), Mockito.anyCollection());
        Mockito.verify(objectCleaner, Mockito.times(0)).verify(Mockito.any(), Mockito.anyCollection());
        Mockito.clearInvocations(mapCleaner,objectCleaner);
        cleaner.verify(alsoMap, empty);
        Mockito.verify(mapCleaner, Mockito.times(1)).verify(Mockito.anyMap(), Mockito.anyCollection());
        Mockito.verify(objectCleaner, Mockito.times(0)).verify(Mockito.any(), Mockito.anyCollection());
    }

    @Test
    public void testCleanup() {
        cleaner.clean(notMap, empty, empty);
        Mockito.verify(mapCleaner, Mockito.times(0))
                .clean(Mockito.anyMap(), Mockito.anyCollection(), Mockito.anyCollection());
        Mockito.verify(objectCleaner, Mockito.times(1))
                .clean(Mockito.any(), Mockito.anyCollection(), Mockito.anyCollection());
        Mockito.clearInvocations(mapCleaner,objectCleaner);
        cleaner.clean(map, empty, empty);
        Mockito.verify(mapCleaner, Mockito.times(1))
                .clean(Mockito.anyMap(), Mockito.anyCollection(), Mockito.anyCollection());
        Mockito.verify(objectCleaner, Mockito.times(0))
                .clean(Mockito.any(), Mockito.anyCollection(), Mockito.anyCollection());Mockito.clearInvocations(mapCleaner,objectCleaner);
        cleaner.clean(alsoMap, empty, empty);
        Mockito.verify(mapCleaner, Mockito.times(1))
                .clean(Mockito.anyMap(), Mockito.anyCollection(), Mockito.anyCollection());
        Mockito.verify(objectCleaner, Mockito.times(0))
                .clean(Mockito.any(), Mockito.anyCollection(), Mockito.anyCollection());
    }

}