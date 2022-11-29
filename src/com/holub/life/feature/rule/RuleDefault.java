package com.holub.life.feature.rule;

public class RuleDefault extends RuleBehavior {
    private static RuleDefault ruleDefault = new RuleDefault();

    private RuleDefault() {

    }

    public static RuleDefault getInstance() {
        return ruleDefault;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"Rule", "Default (3 / 2-3)"};
    }

    @Override
    public boolean getNextState(int neighbors, int amAlive) {
        return neighbors == 3 || (amAlive == 1 && neighbors == 2);
    }
}
