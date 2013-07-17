package com.bthelen.mbtilesutil;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapBoxTileStoreTest extends TestCase {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() {
        //not much to test, just make sure the fileName setter is run.
        String testFileName = "testfile";
        MapBoxTileStore testStore = new MapBoxTileStore(testFileName);
        assertEquals(testFileName, testStore.getFileName());
    }

    @Test
    public void testExtractTiles() {

    }

    @Test
    public void testGetJDBCFileUrl() {
        String testFileName = "testfile";
        MapBoxTileStore testStore = new MapBoxTileStore(testFileName);
        assertEquals("jdbc:sqlite:" + testFileName, testStore.getJDBCFileUrl());
    }

    @Test
    public void testGetFileNameWithoutExtension() {
        //test the happy path
        String testFileName = "testfile.mbtiles";
        MapBoxTileStore testStore = new MapBoxTileStore(testFileName);
        assertEquals("testfile", testStore.getFileNameWithoutExtension());
    }

    @Test
    public void testGetFileNameWithoutExtensionNoMbtilesExtension() {
        //make sure it doesn't crash if no .mbtiles extension is present.
        String testFileName = "testfile";
        MapBoxTileStore testStore = new MapBoxTileStore(testFileName);
        assertEquals("testfile", testStore.getFileNameWithoutExtension());
    }

    @Test
    public void testCountTilesInFile() {
        //i did select count(*) from tiles on the mbtiles file to know the answer should be 2616
        String testFileName = "AustinOpenStreetMap10-14.mbtiles";
        MapBoxTileStore testStore = new MapBoxTileStore(testFileName);
        assertEquals(2616, testStore.countTilesInFile());
    }
}
