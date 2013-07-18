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

    @Test
    public void testConvertTMSYtoGoogleYZoomZero() {
        //at level 0 by definition, x y and z are all 0
        String testFileName = "AustinOpenStreetMap10-14.mbtiles";
        MapBoxTileStore testStore = new MapBoxTileStore(testFileName);
        int translatedY = testStore.convertTMSYtoGoogleY(0, 0);
        assertEquals(0, translatedY);
    }

    @Test
    public void testConvertTMSYtoGoogleYZoomOne() {
        //at level 1, there are only 2 y's 0 and 1 so translating 0 would equal 1
        String testFileName = "AustinOpenStreetMap10-14.mbtiles";
        MapBoxTileStore testStore = new MapBoxTileStore(testFileName);
        int translatedYBottom = testStore.convertTMSYtoGoogleY(0, 1);
        assertEquals(1, translatedYBottom);
        //and translating 1 would be 0
        int translatedYTop = testStore.convertTMSYtoGoogleY(1, 1);
        assertEquals(0, translatedYTop);
    }

    @Test
    public void testConvertTMSYtoGoogleYZoomTwo() {
        //at level 2, TMS would have 0 at the bottom and 3 at the top
        //work our way all the way up and make sure the numbers flip.
        String testFileName = "AustinOpenStreetMap10-14.mbtiles";
        MapBoxTileStore testStore = new MapBoxTileStore(testFileName);
        //bottom
        int translatedY0 = testStore.convertTMSYtoGoogleY(0, 2);
        assertEquals(3, translatedY0);
        //up one
        int translatedY1 = testStore.convertTMSYtoGoogleY(1, 2);
        assertEquals(2, translatedY1);
        //up one more
        int translatedY2 = testStore.convertTMSYtoGoogleY(2, 2);
        assertEquals(1, translatedY2);
        //top
        int translatedY3 = testStore.convertTMSYtoGoogleY(3, 2);
        assertEquals(0, translatedY3);
    }

}
