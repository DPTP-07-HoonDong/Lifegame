package com.holub.life.feature.color;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorBlue implements ColorBehavior {
    private static ColorBlue colorBlue = new ColorBlue();

    private ColorBlue() {

    }

    public static ColorBlue getInstance() {
        return colorBlue;
    }

    @Override
    public Color getLiveColor() {
        return Colors.MEDIUM_BLUE;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"Color", "Blue"};
    }
}
