
package kinectapp.modules;

import java.awt.Color;
import kinectapp.interfaces.LayoutManager;


public class BeatLayoutManager implements LayoutManager {

    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;
    
    private final String FONT_FAMILY = "HP Simplified";
    
    private Color defaultBackgroundColor = Color.WHITE;
    private Color blinkingBackgroundColor = Color.BLUE;
    private Color defaultJointColor = Color.ORANGE;
    
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
        return this.defaultBackgroundColor;
    }

    @Override
    public Color getBlinkingBackgroundColor() {
        return this.blinkingBackgroundColor;
    }

    @Override
    public Color getDefaultJointColor() {
        return this.defaultJointColor;
    }
    
    
    
    
    
}
