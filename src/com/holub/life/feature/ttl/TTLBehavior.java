package com.holub.life.feature.ttl;

import com.holub.life.Resident;
import com.holub.life.feature.Feature;

public abstract class TTLBehavior implements Feature {
    public abstract int getTimeToLive();

    @Override
    public void setFeature(Resident cell) {
        cell.setTTLBehavior(this);
    }
}
