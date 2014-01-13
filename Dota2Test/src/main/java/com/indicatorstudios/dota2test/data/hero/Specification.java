package com.indicatorstudios.dota2test.data.hero;

public enum Specification {
    STRENGTH, AGILITY, INTELLIGENCE;

    public static Specification fromString(String key) {
        if (key.equals("int")) {
            return INTELLIGENCE;
        } else if (key.equals("agi")) {
            return AGILITY;
        } else if (key.equals("str")) {
            return STRENGTH;
        }

        return null;
    }
}
