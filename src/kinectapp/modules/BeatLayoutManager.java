
package kinectapp.modules;

import kinectapp.interfaces.LayoutManager;


public class BeatLayoutManager implements LayoutManager {

    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;
    private final String FONT_FAMILY = "HP Simplified";
    
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
    
    
    
}
