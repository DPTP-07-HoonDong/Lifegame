package com.holub.life.feature;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorBlack implements ColorBehavior {
    @Override
    public Color getLiveColor() {
        return Colors.BLACK;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"Color", "Black"};
    }
}
