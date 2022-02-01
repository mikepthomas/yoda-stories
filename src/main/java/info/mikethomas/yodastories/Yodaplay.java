package info.mikethomas.yodastories;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import info.mikethomas.yodastories.canvas.MapCanvas;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Yodaplay.
 *
 * @author Mike Thomas
 */
public class Yodaplay {

    // Define constants for the top-level container
    private static final String TITLE = "Yoda Stories";
    private static final int CANVAS_WIDTH = 576;  // width of the drawable
    private static final int CANVAS_HEIGHT = 576; // height of the drawable
    private static final int FPS = 5; // animator's target frames per second

    /**
     * The entry main() method to setup the top-level container and animator
     *
     * @param args
     */
    public static void main(String[] args) {
        // Run the GUI codes in the event-dispatching thread for thread safety
        SwingUtilities.invokeLater(() -> {
            // Create the OpenGL rendering canvas
            GLCanvas canvas = new MapCanvas();
            canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
            
            // Create a animator that drives canvas' display() at the specified FPS.
            final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
            
            // Create the top-level container
            final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
            frame.getContentPane().add(canvas);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Use a dedicate thread to run the stop() to ensure that the
                    // animator stops before program exits.
                    new Thread() {
                        @Override
                        public void run() {
                            if (animator.isStarted()) {
                                animator.stop();
                            }
                            System.exit(0);
                        }
                    }.start();
                }
            });
            frame.setTitle(TITLE);
            frame.pack();
            frame.setVisible(true);
            animator.start(); // start the animation loop
        });
    }
}
