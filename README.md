MBTiles Util
====================

Description
---------------------
A command line utility for extracting map tiles from [MBTiles](https://github.com/mapbox/mbtiles-spec) files.  Makes it easy for creating MbTiles archives in [TileMill](http://www.mapbox.com/tilemill/) then using the tiles as overlays in the [Google Maps API](https://developers.google.com/maps/), [Leaflet](http://leafletjs.com/), etc. 

Build Environment
---------------------
* Java 7
* Maven 3

Build Instructions
---------------------
* mvn clean install

Usage
---------------------
After building copy the target/mbtiles-util-1.0-SNAPSHOT-jar-with-dependencies.jar to your ~/bin directory, assuming ~/bin is in your path.

Create a the following shell script called mbtiles-util in your ~/bin directory:

    #!/bin/bash
    java -jar ~/bin/mbtiles-util-1.0-SNAPSHOT-jar-with-dependencies.jar $@

Run the mbtiles-util file on your .mbtiles file.  Here are the usage notes:

    usage: mbtiles-util
     -fileName <arg>     (Required)The name of the .mbtiles file.  Relative or
                         full path.
     -tileScheme <arg>   (Optional)The name of the tile scheme.  TMS(default)
                         or Google.
See [maptiler.org](http://www.maptiler.org/google-maps-coordinates-tile-bounds-projection/) for more information about map tiling.

License
---------------------
    Copyright 2013 Bruce E. Thelen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
