package com.bthelen.mbtilesutil;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;


/* FYI here is the DDL that creates the tiles view in the .mbtiles file so we know what field names are, etc.
 *
 * sqlite> .dump tiles
 * PRAGMA foreign_keys=OFF;
 * BEGIN TRANSACTION;
 * CREATE VIEW tiles AS
 * SELECT
 * map.zoom_level AS zoom_level,
 * map.tile_column AS tile_column,
 * map.tile_row AS tile_row,
 * images.tile_data AS tile_data
 * FROM map
 * JOIN images ON images.tile_id = map.tile_id;
 * COMMIT;
 */
public class MapBoxTileStore {

    private static String JDBC_CLASS = "org.sqlite.JDBC";
    private static String JDBC_SQLITE_URL_PREFIX = "jdbc:sqlite:";

    private String fileName;

    public MapBoxTileStore(String fileName) {
        setFileName(fileName);
    }

    public int countTilesInFile() {
        int retVal = 0;
        Connection connection = null;

        System.out.print("Calculating Number of Tiles to Extract...\r");

        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName(JDBC_CLASS);

            // create a database connection
            connection = DriverManager.getConnection(this.getJDBCFileUrl());
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from tiles");
            //this section and this whole method in general are only necessary
            //because the JDBC driver we are using supports forward only cursors
            //so we can't do rs.last(), rs.getRow() to get the record count.
            while (rs.next()) {
                retVal++;
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }

        return retVal;
    }

    public void extractTiles() {

        Connection connection = null;

        int numTiles = countTilesInFile();

        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName(JDBC_CLASS);

            // create a database connection
            connection = DriverManager.getConnection(this.getJDBCFileUrl());
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from tiles");
            int i = 1;
            while (rs.next()) {
                i++;
                //if we have a high number of tiles, only do this every 500 requests
                if ((numTiles < 10000) || (i % 500 == 0)) {
                    int percentComplete = Math.round((i/(float)numTiles) * 100f);
                    printProgressBar(percentComplete);
                }
                File tileDir = new File("./" + getFileNameWithoutExtension() + "/" + rs.getInt(1) + "/" + rs.getInt(2));
                tileDir.mkdirs();
                File fullPathToTile = new File(tileDir.getAbsolutePath() + "/" + rs.getInt(3) + ".png");
                FileOutputStream output = new FileOutputStream(fullPathToTile);
                IOUtils.write(rs.getBytes(4), output);
                output.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getJDBCFileUrl() {
        return JDBC_SQLITE_URL_PREFIX + getFileName();
    }

    public String getFileNameWithoutExtension() {
        return this.getFileName().replaceAll(".mbtiles", "");
    }

    public static void printProgressBar(int percent){
        StringBuilder bar = new StringBuilder("[");

        for(int i = 0; i < 50; i++){
            if( i < (percent/2)){
                bar.append("=");
            }else if( i == (percent/2)){
                bar.append(">");
            }else{
                bar.append(" ");
            }
        }

        bar.append("]   " + percent + "%     ");
        System.out.print("\r" + bar.toString());
    }

    protected void closeConnection(Connection connection) {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            // connection close failed.
            System.out.println(e);
        }
    }
}
