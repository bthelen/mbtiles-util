package com.bthelen.mbtilesutil;

import org.apache.commons.cli.*;

/**
 * A java command line application for extracting tiles from an mbtiles file.
 *
 * -------------------------------------------------------------------------------------------------
 * Arguments    Required    Description
 *
 * fileName     Yes         The name of the .mbtiles file.
 *
 * -------------------------------------------------------------------------------------------------
 *
 */
public class App {

    private Options options = new Options();
    private String fileName;
    private String tileScheme = "TMS";
    private String FILE_NAME_ARGUMENT = "fileName";
    private String FILE_NAME_ARGUMENT_DESCRIPTION = "(Required)The name of the .mbtiles file.  Relative or full path.";
    private String TILE_SCHEME_ARGUMENT = "tileScheme";
    private String getTILE_SCHEME_ARGUMENT_DESCRIPTION = "(Optional)The name of the tile scheme.  TMS(default) or Google.";

    public static void main(String[] args)
    {
        App thisApp = new App();
        try {
            thisApp.parseArgs(args);
            MapBoxTileStore mapBoxTiles = new MapBoxTileStore(thisApp.getFileName());
            mapBoxTiles.extractTiles(thisApp.getTileScheme());
            //put us on a new line so prompt isn't next to progress bar.
            System.out.print("\r\n");
        } catch (ParseException pe) {
            thisApp.printUsage();
            return;
        }
    }

    protected void parseArgs(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        Option fileNameOption = new Option(FILE_NAME_ARGUMENT, true, FILE_NAME_ARGUMENT_DESCRIPTION);
        fileNameOption.setRequired(true);
        options.addOption(fileNameOption);
        Option tileSchemeOption = new Option(TILE_SCHEME_ARGUMENT, true, getTILE_SCHEME_ARGUMENT_DESCRIPTION);
        options.addOption(tileSchemeOption);
        CommandLine line = parser.parse(options, args);
        setFileName(line.getOptionValue(FILE_NAME_ARGUMENT));
        setTileScheme(line.getOptionValue(TILE_SCHEME_ARGUMENT));
    }

    private void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "mbtiles-util", options );
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTileScheme() {
        return tileScheme;
    }

    public void setTileScheme(String tileScheme) {
        this.tileScheme = tileScheme;
    }
}
