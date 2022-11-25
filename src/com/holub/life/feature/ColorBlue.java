package com.holub.life.feature;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorBlue implements ColorBehavior {
    private Color LIVE_COLOR;

    @Override
    public Color getLiveColor() {
        return LIVE_COLOR;
    }

    @Override
    public void setLiveColor() {
        LIVE_COLOR = Colors.MEDIUM_BLUE;
    }
}