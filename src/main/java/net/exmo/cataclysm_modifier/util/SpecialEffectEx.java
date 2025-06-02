package net.exmo.cataclysm_modifier.util;


import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.content.specialEffects.SpecialEffect;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.util.TriConsumer;

public abstract class SpecialEffectEx  {
    static Random random = new Random();






    public static double getPlayerDamage(LivingEntity player) {
        return player.getAttribute(Attributes.ATTACK_DAMAGE) != null ? player.getAttributeValue(Attributes.ATTACK_DAMAGE) : (double)0.0F;
    }

    public static int randomInt(int a) {
        return random.nextInt(a);
    }

    public static ResourceLocation getEnchantmentID(Enchantment enchantment) {
        return ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
    }

    public static void hurtEventHandle(LivingHurtEvent event, BiConsumer<Player, LivingEntity> consumer) {
        Entity var3 = event.getSource().getEntity();
        if (var3 instanceof Player player) {
            consumer.accept(player, event.getEntity());
        }

    }

    public static void hurtEventHandleSe(LivingHurtEvent event, BiConsumer<Player, LivingEntity> consumer, EquipmentSlot equipmentSlot, SpecialEffect specialEffect) {
        Entity var4 = event.getSource().getEntity();
        if (var4 instanceof Player player) {
            if (ExUtil.hasSpecialEffect(specialEffect, player, equipmentSlot)) {
                consumer.accept(player, event.getEntity());
            }
        }

    }
    public static void hurtEventHandleSe(LivingHurtEvent event, BiConsumer<Player, LivingEntity> consumer, EquipmentSlot equipmentSlot ,String id ) {
        Entity var4 = event.getSource().getEntity();
        if (var4 instanceof Player player) {
            if (ExUtil.hasSpecialEffect(SpecialEffects.specialEffects.get(id), player, equipmentSlot)) {
                consumer.accept(player, event.getEntity());
            }
        }

    }

    public static void hurtEventAmountBase(LivingHurtEvent event, float amount) {
        event.setAmount(event.getAmount() * (1.0F + amount));
    }
}
