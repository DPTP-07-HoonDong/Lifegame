package com.holub.life.feature.ttl;

import com.holub.life.Resident;

public class TTL2 extends TTLBehavior {
    private static TTL2 ttl2 = new TTL2();

    private TTL2() {

    }

    public static TTL2 getInstance() {
        return ttl2;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"TTL", "2"};
    }

    @Override
    public int getTimeToLive() {
        return 2;
    }

}
