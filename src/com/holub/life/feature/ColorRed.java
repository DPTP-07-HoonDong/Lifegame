package com.holub.life.feature;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorRed implements ColorBehavior {
    private static ColorRed colorRed = new ColorRed();

    private ColorRed() {

    }

    public static ColorRed getInstance() {
        return colorRed;
    }

    @Override
    public Color getLiveColor() {
        return Colors.MEDIUM_RED;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"Color", "Red"};
    }
}
