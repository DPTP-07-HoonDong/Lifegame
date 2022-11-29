package com.holub.life.feature.rule;

import com.holub.life.Resident;
import com.holub.life.feature.Feature;

public abstract class RuleBehavior implements Feature {
    public abstract boolean getNextState(int neighbors, int amAlive);

    @Override
    public void setFeature(Resident cell) {
        cell.setRuleBehavior(this);
    }
}
