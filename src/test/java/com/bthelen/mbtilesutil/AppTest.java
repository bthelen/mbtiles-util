package com.bthelen.mbtilesutil;

import junit.framework.TestCase;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest extends TestCase {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSuccessfulParseArgs() {
        //test the happy path
        App myApp = new App();
        String[] goodArgs = {"-fileName",  "TestFile.mbtiles"};
        try {
            myApp.parseArgs(goodArgs);
            assertEquals(myApp.getFileName(), "TestFile.mbtiles");
        } catch (ParseException pe) {
            fail("Caught ParseException.  Test must fail.");
        }
    }

    @Test
    public void testCaseSensitivityParseArgs() {
        //the command line is case sensitive!
        App myApp = new App();
        String[] goodArgs = {"-FileName",  "TestFile.mbtiles"};
        try {
            myApp.parseArgs(goodArgs);
            fail("ParseException not thrown.  Test must fail.");
        } catch (ParseException pe) {
            //test passes only if pe is thrown
            System.out.println("Caught ParseException as expected.");
        }
    }

    @Test
    public void testCaseNullParseArgs() {
        //put no args at all and should throw exception
        App myApp = new App();
        String[] goodArgs = {};
        try {
            myApp.parseArgs(goodArgs);
            fail("ParseException not thrown.  Test must fail.");
        } catch (ParseException pe) {
            //test passes only if pe is thrown
            System.out.println("Caught ParseException as expected.");
        }
    }

    @Test
    public void testCaseNullValueParseArgs() {
        //must include both key and value or expect ParseException
        App myApp = new App();
        String[] goodArgs = {"-fileName"};
        try {
            myApp.parseArgs(goodArgs);
            fail("ParseException not thrown.  Test must fail.");
        } catch (ParseException pe) {
            //test passes only if pe is thrown
            System.out.println("Caught ParseException as expected.");
        }
    }

}