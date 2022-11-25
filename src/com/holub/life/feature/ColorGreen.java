package com.holub.life.feature;

import com.holub.ui.Colors;

import java.awt.*;

public class ColorGreen implements ColorBehavior {
    @Override
    public Color getLiveColor() {
        return Colors.GREEN;
    }

    @Override
    public String[] getMenuName() {
        return new String[0];
    }
}
