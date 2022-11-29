package com.holub.life.feature.rule;

import com.holub.life.feature.Feature;

public interface RuleBehavior extends Feature {
    boolean getNextState(int neighbors, int amAlive);
}
