package info.mikethomas.yodastories.zone;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import lombok.Data;

import org.mapeditor.core.Map;
import org.mapeditor.core.TileLayer;

@Data
public class Zone {

    private int id;
    private int width, height;
    private Map map;

    private Graphics2D getGraphics(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(
            RenderingHints.KEY_DITHERING,
            RenderingHints.VALUE_DITHER_ENABLE);
        graphics.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        return graphics;
    }

    private BufferedImage renderImage(TileLayer tileLayer) {
        BufferedImage image = new BufferedImage(width * 32, height * 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = getGraphics(image);

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (tileLayer.getTileAt(x, y) != null) {
                    BufferedImage tileImage = tileLayer.getTileAt(x, y).getImage();
                    graphics.drawImage(tileImage, x * 32, y * 32, null);
                }
            }
        }

        return image;
    }

    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(width * 32, height * 32, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = getGraphics(image);

        graphics.drawImage(renderImage((TileLayer) map.getLayer(0)), 0, 0, null);
        graphics.drawImage(renderImage((TileLayer) map.getLayer(1)), 0, 0, null);
        graphics.drawImage(renderImage((TileLayer) map.getLayer(2)), 0, 0, null);
        System.out.printf("Rendered Map %d\n", id);
        return image;
    }

    public void saveImage(String dir) throws IOException {
        saveImage(dir, false);
    }

    public void saveImage(String dir, boolean allLayers) throws IOException {
        File directory = new File(dir + '/' + id);
        directory.mkdirs();
        
        if (allLayers) {
            File bottomFile = new File(dir + '/' + id, "bottom.png");
            ImageIO.write(renderImage((TileLayer) map.getLayer(0)), "png", bottomFile);
            System.out.printf("Saved Map %s\n", bottomFile.getAbsolutePath());

            File middleFile = new File(dir + '/' + id, "middle.png");
            ImageIO.write(renderImage((TileLayer) map.getLayer(1)), "png", middleFile);
            System.out.printf("Saved Map %s\n", middleFile.getAbsolutePath());

            File topFile = new File(dir + '/' + id, "top.png");
            ImageIO.write(renderImage((TileLayer) map.getLayer(2)), "png", topFile);
            System.out.printf("Saved Map %s\n", topFile.getAbsolutePath());
            
        }

        File combinedFile = new File(dir + '/' + id, "combined.png");
        ImageIO.write(getImage(), "png", combinedFile);
        System.out.printf("Saved Map %s\n", combinedFile.getAbsolutePath());
    }
}
