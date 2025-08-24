package net.exmo.cataclysm_modifier.content.item;

import com.github.L_Ender.cataclysm.items.The_Incinerator;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class The_Abyss_Incinerator extends The_Incinerator {
    public final Multimap<Attribute, AttributeModifier> incineratorAttributes;
    public The_Abyss_Incinerator(Properties group) {
        super(group);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (double)32F, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-2.5F, AttributeModifier.Operation.ADDITION));
        builder.put((Attribute) ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, "Tool modifier", (double)2.0F, AttributeModifier.Operation.ADDITION));
        this.incineratorAttributes = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.incineratorAttributes : super.getDefaultAttributeModifiers(equipmentSlot);

    }
}
