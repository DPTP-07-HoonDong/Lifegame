package com.holub.life.feature.color;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorBlack extends ColorBehavior {
    private static ColorBlack colorBlack = new ColorBlack();

    private ColorBlack() {

    }

    public static ColorBlack getInstance() {
        return colorBlack;
    }

    @Override
    public Color getLiveColor() {
        return Colors.BLACK;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"Color", "Black"};
    }
}
