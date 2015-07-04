/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.tile;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import javax.imageio.ImageIO;
import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_NEAREST;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Mike
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "image")
@XmlType(propOrder={"width", "height", "format", "tileData"})
public class TileImage {

    public TileImage() {
        super();
    }

    public TileImage(BufferedImage bufferedImage) {
        this();

        this.bufferedImage = bufferedImage;
    }

    private BufferedImage bufferedImage;
    private Texture linearTexture, nearestTexture;

    @XmlAttribute(name="width")
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @XmlAttribute(name="height")
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @XmlAttribute(name="format")
    public String getFormat() {
        return "png";
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @XmlElement(name="data")
    public TileData getTileData() {
        String base64bytes = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, getFormat(), out);
            byte[] bytes = out.toByteArray();
            base64bytes = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException ex) {
            Logger.getLogger(TileImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new TileData(base64bytes);
    }

    public void saveImage(String source) throws IOException {
        File file = new File(source + "." + getFormat());
        file.mkdirs();
        ImageIO.write(bufferedImage, getFormat(), file);
        System.out.printf("Saved Tile %s\n", file.getAbsolutePath());
    }

    public Texture getTexture(GL2 gl, boolean filtered) {
        if (filtered) {
            // Linear filter is more compute-intensive
            if (linearTexture == null) {
                linearTexture = AWTTextureIO.newTexture(GLProfile.getDefault(), bufferedImage, false);
                // Use linear filter if image is smaller than the original texture
                gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                // Use linear filter if image is larger than the original texture
                gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            }
            return linearTexture;
        } else {
            // Nearest filter is least compute-intensive
            if (nearestTexture == null) {
                nearestTexture = AWTTextureIO.newTexture(GLProfile.getDefault(), bufferedImage, false);
                // Use nearer filter if image is smaller than the original texture
                gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                // Use nearer filter if image is larger than the original texture
                gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            }
            return nearestTexture;
        }
        
    }
}
