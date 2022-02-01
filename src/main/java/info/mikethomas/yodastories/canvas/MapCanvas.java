package info.mikethomas.yodastories.canvas;

import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2.*;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import info.mikethomas.yodastories.parser.DataParser;
import info.mikethomas.yodastories.zone.Zone;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;

/**
 * Yodaplay.
 *
 * @author Mike Thomas
 */
public class MapCanvas extends GLCanvas implements GLEventListener, KeyListener {

    // Data file parser
    private final DataParser data = new DataParser();
    

    // Setup OpenGL Graphics Renderer
    private final GLU glu = new GLU(); // get GL Utilities

    // Filtering
    private int filter = GL_NEAREST;

    // Lighting
    private boolean isLightOn;

    // Current Zone
    private int currZone = 287;

    /**
     * Constructor to setup the GUI for this Component
     */
    public MapCanvas() {
        this.addGLEventListener(this);
        // For handling KeyEvents
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    // ------ Implement methods declared in GLEventListener ------
    /**
     * Called back immediately after the OpenGL context is initialized. Can be
     * used to perform one-time initialization. Run only once.
     *
     * @param drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
        gl.glClearDepth(1.0f);      // set clear depth value to farthest
        gl.glEnable(GL_MULTISAMPLE); // enables multisample anti-aliasing
        gl.glEnable(GL_DEPTH_TEST); // enables depth testing
        gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
        gl.glHint(GL_TEXTURE_COMPRESSION_HINT, GL_NICEST); // best texture compression
        gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting

        // Nearest filter is least compute-intensive
        // Use nearer filter if image is larger than the original texture
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        // Use nearer filter if image is smaller than the original texture
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        // Generate mipmap
        gl.glGenerateMipmap(GL_TEXTURE_2D);
        
        // Set up the lighting for Light-1
        // Ambient light does not come from a particular direction. Need some ambient
        // light to light up the scene. Ambient's value in RGBA
        float[] lightAmbientValue = {0.5f, 0.5f, 0.5f, 1.0f};
        // Diffuse light comes from a particular location. Diffuse's value in RGBA
        float[] lightDiffuseValue = {1.0f, 1.0f, 1.0f, 1.0f};
        // Diffuse light location xyz (in front of the screen).
        float lightDiffusePosition[] = {0, 0, 2, 1};

        gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbientValue, 0);
        gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuseValue, 0);
        gl.glLightfv(GL_LIGHT1, GL_POSITION, lightDiffusePosition, 0);
        gl.glEnable(GL_LIGHT1);    // Enable Light-1
        gl.glDisable(GL_LIGHTING); // But disable lighting
        isLightOn = false;

        // Blending control
        // Used blending function based On source alpha value
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL_BLEND);
    }

    /**
     * Call-back handler for window re-size event. Also called when the drawable
     * is first set to visible.
     *
     * @param drawable
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context

        if (height == 0) {
            height = 1;   // prevent divide by zero
        }
        float aspect = (float) width / height;

        // Set the view port (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix
        gl.glLoadIdentity();             // reset projection matrix
        glu.gluPerspective(45.0, aspect, 0.1, 750); // fovy, aspect, zNear, zFar

        // Enable the model-view transform
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity(); // reset
    }

    /**
     * Called back by the animator to perform rendering.
     *
     * @param drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
        gl.glLoadIdentity();  // reset the model-view matrix

        // Lighting
        if (isLightOn) {
            gl.glEnable(GL_LIGHTING);
        } else {
            gl.glDisable(GL_LIGHTING);
        }

        Zone zone = data.getZones().get(currZone);
        final int numTilesWide = zone.getWidth();
        final int numTilesHigh = zone.getHeight();
        final int tileWidth = 32;
        final int tileHeight = 32;
        final int mapWidth = numTilesWide * tileWidth;
        final int mapHeight = numTilesHigh * tileHeight;
        // ------ Set the map into view ------
        gl.glTranslated(-mapWidth/2, mapHeight/2, mapWidth*-1.2);
        //gl.glRotated(-35, 1, 0, 0); // rotate about the x-axis

        // ------ Render the TileMap with textures ------
        for (int y = 0; y < numTilesHigh; y++) {
            for (int x = 0; x < numTilesWide; x++) {

                TileLayer bottomTiles = (TileLayer) zone.getMap().getLayer(0);
                TileLayer middleTiles = (TileLayer) zone.getMap().getLayer(1);
                TileLayer topTiles = (TileLayer) zone.getMap().getLayer(2);
                renderTile(gl, bottomTiles.getTileAt(x, y));
                renderTile(gl, middleTiles.getTileAt(x, y));
                renderTile(gl, topTiles.getTileAt(x, y));

                gl.glTranslated(tileWidth, 0, 0);
            }
            gl.glTranslated(-mapWidth, -tileHeight, 0);
        }
    }

    private void renderTile(GL2 gl, Tile tile) {
        if (tile != null) {
            BufferedImage image = tile.getImage();
            Texture texture = AWTTextureIO.newTexture(GLProfile.getDefault(), image, false);
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

            // Enables this texture's target in the current GL context's state.
            texture.enable(gl);
            // Bind the texture to the current OpenGL graphics context.
            texture.bind(gl);
        }

        // draw using quads
        gl.glBegin(GL_QUADS);
        // top-left of the texture and quad
        gl.glTexCoord2i(0, 0);
        gl.glVertex3i(0, 0, 0);
        // top-right of the texture and quad
        gl.glTexCoord2i(1, 0);
        gl.glVertex3i(32, 0, 0);
        // bottom-right of the texture and quad
        gl.glTexCoord2i(1, 1);
        gl.glVertex3i(32, -32, 0);
        // bottom-left of the texture and quad
        gl.glTexCoord2i(0, 1);
        gl.glVertex3i(0, -32, 0);
        gl.glEnd();
    }

    /**
     * Called back before the OpenGL context is destroyed. Release resource such
     * as buffers.
     *
     * @param drawable
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    // ------ Implement methods declared in KeyListener ------
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case VK_L: // toggle light on/off
                isLightOn = !isLightOn;
                break;
            case VK_F: // toggle filter (NEAREST, LINEAR)
                if (filter == GL_NEAREST) {
                    // Linear filter is more compute-intensive
                    filter = GL_LINEAR;
                    System.out.println("Filtering on");
                } else {
                    // Nearest filter is least compute-intensive
                    filter = GL_NEAREST;
                    System.out.println("Filtering off");
                }
                break;
            case VK_LEFT: // switch to the previous zone
                if (currZone > 0) {
                    System.out.println("Changed to previous zone: " + --currZone);
                }
                break;
            case VK_RIGHT: // switch to the next zone
                if (currZone < data.getZones().size() -1) {
                    System.out.println("Changed to next zone: " + ++currZone);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
