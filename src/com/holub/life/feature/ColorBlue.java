package com.holub.life.feature;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorBlue implements ColorBehavior {
    @Override
    public Color getLiveColor() {
        return Colors.MEDIUM_BLUE;
    }

    @Override
    public String[] getMenuName() {
        return new String[0];
    }
}
