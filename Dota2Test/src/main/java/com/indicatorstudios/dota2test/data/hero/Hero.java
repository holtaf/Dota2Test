package com.indicatorstudios.dota2test.data.hero;


import org.json.JSONException;
import org.json.JSONObject;

import static com.indicatorstudios.dota2test.data.JSONConstants.*;

public class Hero {
    public final String name;
    public final String displayName;
    public final String attackRangeType;
    public final String roles;
    public final double minDamage;
    public final double maxDamage;
    public final double movespeed;
    public final double armor;
    public final Specification specification;
    public final double baseStrength;
    public final double baseAgility;
    public final double baseIntelligence;
    public final double strengthGain;
    public final double agilityGain;
    public final double intelligenceGain;

    private Hero(String name, String displayName, String attackRangeType, String roles,
                 double minDamage, double maxDamage, double movespeed, double armor,
                 Specification specification, double baseStrength, double baseAgility, double baseIntelligence,
                 double strengthGain, double agilityGain, double intelligenceGain) {
        this.name = name;
        this.displayName = displayName;
        this.attackRangeType = attackRangeType;
        this.roles = roles;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.movespeed = movespeed;
        this.armor = armor;
        this.specification = specification;
        this.baseStrength = baseStrength;
        this.baseAgility = baseAgility;
        this.baseIntelligence = baseIntelligence;
        this.strengthGain = strengthGain;
        this.agilityGain = agilityGain;
        this.intelligenceGain = intelligenceGain;
    }

    public static Hero createFromJSON(String name, JSONObject data) throws JSONException {
        JSONObject heroAttributes = data.getJSONObject(ATTRIBUTES);
        JSONObject heroDamage = heroAttributes.getJSONObject(DAMAGE);

        return new Hero(name, data.getString(HERO_DISPLAY_NAME), data.getString(HERO_ATTACK_RANGE_TYPE),
                data.getString(HERO_ROLES), heroDamage.getDouble(MIN), heroDamage.getDouble(MAX), heroAttributes.getDouble(MOVESPEED),
                heroAttributes.getDouble(ARMOR), Specification.fromString(data.getString(HERO_SPECIFICATION)),
                heroAttributes.getJSONObject(STRENGTH).getDouble(BASE), heroAttributes.getJSONObject(AGILITY).getDouble(BASE), heroAttributes.getJSONObject(INTELLIGENCE).getDouble(BASE),
                heroAttributes.getJSONObject(STRENGTH).getDouble(GAIN), heroAttributes.getJSONObject(AGILITY).getDouble(GAIN), heroAttributes.getJSONObject(INTELLIGENCE).getDouble(GAIN));
    }
}
