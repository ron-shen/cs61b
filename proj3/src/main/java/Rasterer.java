
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private final double ROOT_ULLAT = MapServer.ROOT_ULLAT;
    private final double ROOT_ULLON = MapServer.ROOT_ULLON;
    private final double ROOT_LRLAT = MapServer.ROOT_LRLAT;
    private final double ROOT_LRLON = MapServer.ROOT_LRLON;
    private final double lonDistance = ROOT_LRLON - ROOT_ULLON;
    private final double latDistance = ROOT_ULLAT - ROOT_LRLAT;
    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        double queryUlLon = params.get("ullon");
        double queryUllat = params.get("ullat");
        double queryLrLon = params.get("lrlon");
        double queryLrlat = params.get("lrlat");
        double queryWidth = params.get("w");
        double queryHeight = params.get("h");
        boolean check = checkParams(queryLrLon, queryUlLon, queryUllat, queryLrlat);

        int depth = findDepth(queryLrLon, queryUlLon, queryWidth);

        double scaleLon = lonDistance / Math.pow(2, depth);
        int[] lonMultiple = findMultipleLon(queryUlLon, queryLrLon, scaleLon);
        double raster_ul_lon = ROOT_ULLON + scaleLon * lonMultiple[0];
        double raster_lr_lon = ROOT_ULLON + scaleLon * lonMultiple[1];

        double scaleLat = latDistance / Math.pow(2, depth);
        int[] latMultiple = findMultipleLat(queryUllat, queryLrlat, scaleLat);
        double raster_ul_lat = ROOT_ULLAT - scaleLat * latMultiple[0];
        double raster_lr_lat = ROOT_ULLAT - scaleLat * latMultiple[1];

        String[][] renderGrid = findRenderGrid(depth, lonMultiple, latMultiple);

        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("render_grid", renderGrid);
        results.put("query_success", check);
        return results;
    }
    private double findLonDPP(double lrLon, double ulLon, double width){
        return (lrLon - ulLon) / width;
    }
    private int[] findMultipleLon(double ullon, double lrlon, double scale){
        ullon = Math.max(ullon, ROOT_ULLON);
        lrlon = Math.min(lrlon, ROOT_LRLON);
        int [] res = new int[2];
        int ulMultiple = (int) Math.floor((ullon - ROOT_ULLON) / scale);
        int lrMultiple = (int) Math.ceil((lrlon - ROOT_ULLON) / scale);
        res[0] = ulMultiple;
        res[1] = lrMultiple;
        return res;
    }
    private int[] findMultipleLat(double ullat, double lrlat, double scale) {
        ullat = Math.min(ullat, ROOT_ULLAT);
        lrlat = Math.max(lrlat, ROOT_LRLAT);
        int [] res = new int[2];
        int ulMultiple = (int) Math.floor((ROOT_ULLAT - ullat) / scale);
        int lrMultiple = (int) Math.ceil((ROOT_ULLAT - lrlat) / scale);
        res[0] = ulMultiple;
        res[1] = lrMultiple;
        return res;
    }
    private int findDepth(double lrLon, double ulLon, double width){
        double interestedLonDPP = findLonDPP(lrLon, ulLon, width);
        double suitableLonDPP = -1;
        int depth = 0;
        while(depth < 7){
            double newULLon = ROOT_LRLON - lonDistance / Math.pow(2, depth);
            suitableLonDPP = findLonDPP(ROOT_LRLON, newULLon, 256);
            if(suitableLonDPP < interestedLonDPP){
                break;
            }
            depth++;
        }
        return depth;
    }
    private String[][] findRenderGrid(int depth, int[] lonMultiple, int[] latMultiple){
        int cols = lonMultiple[1] - lonMultiple[0];
        int rows = latMultiple[1] - latMultiple[0];
        int startRow = latMultiple[0];
        int endRow = latMultiple[1];
        int startCol = lonMultiple[0];
        int endCol = lonMultiple[1];

        String[][] renderGrid = new String[rows][cols];
        for(int i = startRow; i < endRow; i++){
            for(int j = startCol; j < endCol; j++){
                String filename = String.format("d%d_x%d_y%d.png", depth, j, i);
                renderGrid[i - startRow][j - startCol] = filename;
            }
        }
        return renderGrid;
    }
    private boolean checkParams(double queryLrlon, double queryUllon, double queryUllat, double queryLrlat) {
        if (queryUllon > MapServer.ROOT_LRLON || queryLrlon < MapServer.ROOT_ULLON
                || queryUllat < MapServer.ROOT_LRLAT || queryLrlat > MapServer.ROOT_ULLAT
        ) {
            return false;
        }
        return true;
    }

}
