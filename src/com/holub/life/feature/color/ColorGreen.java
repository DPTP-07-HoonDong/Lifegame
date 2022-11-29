package com.holub.life.feature.color;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorGreen extends ColorBehavior {
    private static ColorGreen colorGreen = new ColorGreen();

    private ColorGreen() {

    }

    public static ColorGreen getInstance() {
        return colorGreen;
    }

    @Override
    public Color getLiveColor() {
        return Colors.GREEN;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"Color", "Green"};
    }
}
