package net.exmo.cataclysm_modifier;

import net.exmo.exmodifier.content.specialEffects.SpecialEffect;

import java.util.HashMap;
import java.util.Map;

public class SpecialEffects {
    public static Map<String , SpecialEffect> specialEffects = new HashMap<>();
    public final static SpecialEffect ZANGYAN = Builder.of("zangyan").build();
    public static class Builder{
        private String id;

        public static Builder of (String id) {
            return new Builder(id);
        }

        public Builder(String id) {
            this.id = id;
        }
        public SpecialEffect build(){
            SpecialEffect specialEffect = new SpecialEffect(id, null);
            specialEffects.put(id, specialEffect);
            return specialEffect;
        }
    }
}

