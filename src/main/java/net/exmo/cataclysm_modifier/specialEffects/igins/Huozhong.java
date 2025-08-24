 package net.exmo.cataclysm_modifier.specialEffects.igins;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Huozhong {


    // 水中不消耗氧气
    @SubscribeEvent
    public static void onLivingUpdate(TickEvent.PlayerTickEvent event) {
        ExUtil.ifHasSpecialEffect(SpecialEffects.HUOZHONG, event.player, e -> {
            if (event.player.isInWater() && !event.player.getAbilities().flying) {
                event.player.setAirSupply(event.player.getMaxAirSupply()); // 保持最大氧气
            }
        });
    }

    // 火焰伤害转为伤害吸收
    @SubscribeEvent
    public static void onHurt(ExLivingHurtEvent event) {
        if ((event.getSource().is(DamageTypes.IN_FIRE) || event.getSource().is(DamageTypes.ON_FIRE) || (event.getSource().getEntity()!=null && event.getSource().getEntity() instanceof LivingEntity livingEntity && EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, livingEntity) > 0)
       ) && event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.HUOZHONG, player, e -> {
                float damage = event.getAmount();
                float absorptionToAdd = damage * e.getSpecialTagSettingFloatOrDefault(
                        SpecialEffects.HUOZHONG.id(), "fire_to_absorption", 1.0f);

                float currentAbsorption = player.getAbsorptionAmount();
                if (currentAbsorption < 80) {
                    player.setAbsorptionAmount(Math.min(currentAbsorption + absorptionToAdd, 80)); // 最大40点吸收
                }
                ExLivingHurtEvent.TotalAmount *=0.1f;
            });
        }
    }

    // 吸收值≥10时附加炽热烙印
    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        LivingEntity target = event.getEntity();
        if (
                event.getSource().getEntity() instanceof Player player) {

            ExUtil.ifHasSpecialEffect(SpecialEffects.HUOZHONG, player, EquipmentSlot.CHEST, e -> {
                if (player.getAbsorptionAmount() >= 10) {
                    int currentAmplifier = target.hasEffect(ModEffect.EFFECTBLAZING_BRAND.get())
                            ? target.getEffect(ModEffect.EFFECTBLAZING_BRAND.get()).getAmplifier() : -1;

                    if (currentAmplifier <= 5) { // 最多叠加到5层
                        target.addEffect(new MobEffectInstance(
                                ModEffect.EFFECTBLAZING_BRAND.get(),
                                100, // 持续时间
                                Math.min(currentAmplifier + 1, 5), // 层数+1
                                false, true));
                    }
                }
            });
        }
    }

    // 吸收值≥20时生命回复
    @SubscribeEvent
    public static void onHurtEnemy(ExLivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        if (event.getSource().getEntity() instanceof Player player) {

            ExUtil.ifHasSpecialEffect(SpecialEffects.HUOZHONG, player, EquipmentSlot.CHEST, e -> {
                if (player.getAbsorptionAmount() >= 20 &&
                        target.hasEffect(ModEffect.EFFECTBLAZING_BRAND.get()) &&
                        target.getEffect(ModEffect.EFFECTBLAZING_BRAND.get()).getAmplifier() >= 5) {

                    player.heal(2.0f); // 回复2点生命
                }
            });
        }
    }
}
