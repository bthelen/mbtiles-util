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

    private String fileName;
    private String FILE_NAME_ARGUMENT = "fileName";
    private String FILE_NAME_ARGUMENT_DESCRIPTION = "The name of the .mbtiles file.  Relative or full path.";

    public static void main(String[] args)
    {
        App thisApp = new App();
        try {
            thisApp.parseArgs(args);
            MapBoxTileStore mapBoxTiles = new MapBoxTileStore(thisApp.getFileName());
            mapBoxTiles.extractTiles();
        } catch (ParseException pe) {
            System.out.println(thisApp.getUsageMessage());
            return;
        }
    }

    protected void parseArgs(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        Option fileNameOption = new Option(FILE_NAME_ARGUMENT, true, FILE_NAME_ARGUMENT_DESCRIPTION);
        fileNameOption.setRequired(true);
        options.addOption(fileNameOption);
        CommandLine line = parser.parse(options, args);
        setFileName(line.getOptionValue(FILE_NAME_ARGUMENT));
    }

    private String getUsageMessage() {
        String usage = "usage: java -jar <insert name of jar here>.jar [options...]";
        usage += "\nOptions:(R) means required arguments, (O) means optional arguments\n";
        usage += "\n-fileName       (R)    The name of the .mbtiles file.";
        return usage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
