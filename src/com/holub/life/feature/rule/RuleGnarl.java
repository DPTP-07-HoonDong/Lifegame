package com.holub.life.feature.rule;

public class RuleGnarl implements RuleBehavior {
    private static RuleGnarl ruleGnarl = new RuleGnarl();

    private RuleGnarl() {

    }

    public static RuleGnarl getInstance() {
        return ruleGnarl;
    }

    @Override
    public String[] getMenuName() {
        return new String[]{"Rule", "Gnarl (1/1)"};
    }

    @Override
    public int[] getRule() {
        return new int[0];
    }
}
