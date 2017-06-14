
package kinectapp.modules;

import java.awt.Color;
import kinectapp.interfaces.LayoutManager;


public class BeatLayoutManager implements LayoutManager {

    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;
    
    private final String FONT_FAMILY = "Arial";
    
    private final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private final Color BLINKING_BACKGROUND_COLOR = Color.BLUE;
    private final Color DEFAULT_JOINT_COLOR = Color.ORANGE;
    
    @Override
    public int getWindowWidth() {
        return this.WINDOW_WIDTH;
    }

    @Override
    public int getWindowHeight() {
        return this.WINDOW_HEIGHT;
    }

    @Override
    public String getDefaultFontFamily() {
        return this.FONT_FAMILY;
    }

    @Override
    public Color getDefaultBackgroundColor() {
        return this.DEFAULT_BACKGROUND_COLOR;
    }

    @Override
    public Color getBlinkingBackgroundColor() {
        return this.BLINKING_BACKGROUND_COLOR;
    }

    @Override
    public Color getDefaultJointColor() {
        return this.DEFAULT_JOINT_COLOR;
    }
    
    
    
    
    
}
