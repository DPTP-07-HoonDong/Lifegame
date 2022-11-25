package com.holub.life.feature;

public class TTLDefault implements TTLBehavior {
    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "Default"};
    }

    @Override
    public int getTimeToLive() {
        return 1;
    }
}
