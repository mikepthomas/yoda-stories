/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.zachtronics.yodastories.tile.Tile;
import com.zachtronics.yodastories.tileset.Terrain;
import com.zachtronics.yodastories.tileset.TerrainTypes;
import com.zachtronics.yodastories.tile.TileImage;
import com.zachtronics.yodastories.tileset.TileSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mike
 */
public class TileSetTest {
    
    public TileSetTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TileSet.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        TileSet tileSet = new TileSet();
        tileSet.setName("Yoda Stories");
        tileSet.setTileWidth(32);
        tileSet.setTileHeight(32);
        
        TerrainTypes terrainTypes = new TerrainTypes();
        tileSet.setTerrainTypes(terrainTypes);
        terrainTypes.setTerrains(new ArrayList<Terrain>());
        
        Terrain terrain = new Terrain();
        terrain.setName("Desert");
        terrain.setTile(0);
        terrainTypes.getTerrains().add(terrain);
        
        tileSet.setTiles(new ArrayList<Tile>());
        Tile tile = new Tile();
        tile.setId(0);
        tile.setTerrain("0,0,0,0");
        TileImage image = new TileImage(new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB));
        tile.setImage(image);
        tileSet.getTiles().add(tile);
        
        marshaller.marshal(tileSet, new File("/Volumes/Documents", "Yoda Stories.tsx"));
     }
}
