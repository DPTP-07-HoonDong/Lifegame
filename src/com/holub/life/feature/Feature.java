package com.holub.life.feature;

// TODO 모든 기능 Behavior는 이 Feature 인터페이스를 implement 받습니다.
public interface Feature {
    String[] getMenuName();

    Feature DUMMY = new Feature() {
        private final String menuSpecifier = "Dummy";
        private final String name = "Default";

        @Override
        public String[] getMenuName() {
            return new String[]{this.menuSpecifier, this.name};
        }
    };

    Feature DUMMY_OTHER = new Feature() {
        private final String menuSpecifier = "Dummy";
        private final String name = "Other";

        @Override
        public String[] getMenuName() {
            return new String[]{this.menuSpecifier, this.name};
        }
    };
}
