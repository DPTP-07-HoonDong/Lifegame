package com.holub.life.feature;

public class TTLDefault implements TTLBehavior {
    @Override
    public String[] getMenuName() {
        return new String[0];
    }

    @Override
    public int getTimeToLive() {
        return 1;
    }
}
