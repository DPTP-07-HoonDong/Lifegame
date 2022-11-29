package com.holub.life.feature.color;

import com.holub.life.Resident;
import com.holub.life.feature.Feature;

import java.awt.*;

public abstract class ColorBehavior implements Feature {
    public abstract Color getLiveColor();

    @Override
    public void setFeature(Resident cell) {
        cell.setColorBehavior(this);
    }
}
