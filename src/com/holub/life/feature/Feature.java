package com.holub.life.feature;

import com.holub.life.Resident;

// TODO 모든 기능 Behavior는 이 Feature 인터페이스를 implement 받습니다.
public interface Feature {
    String[] getMenuName();
    void setFeature(Resident cell);
}
