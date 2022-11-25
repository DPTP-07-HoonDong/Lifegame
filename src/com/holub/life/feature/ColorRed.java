package com.holub.life.feature;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorRed implements ColorBehavior {
    @Override
    public Color getLiveColor() {
        return Colors.MEDIUM_RED;
    }

    @Override
    public String[] getMenuName() {
        return new String[0];
    }
}
