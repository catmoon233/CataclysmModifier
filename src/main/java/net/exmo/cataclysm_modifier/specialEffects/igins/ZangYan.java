package net.exmo.cataclysm_modifier.specialEffects.igins;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.content.specialEffects.SpecialEffect;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ZangYan  {
    @SubscribeEvent
    public static void OnHurt (ExLivingHurtEvent event)
    {
        if (event.getEntity() instanceof Player player) {
             ExUtil.ifHasSpecialEffect(SpecialEffects.ZANGYAN, player, EquipmentSlot.MAINHAND,(e)->{
                Entity entity = event.getSource().getEntity();
                if (entity != null && entity.fireImmune()) {
                    event.addMutiAmount( (-e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZANGYAN.id(),"defense",0.2f)));
                }
             });
        }
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.ZANGYAN, player, EquipmentSlot.MAINHAND, (e) -> {
                Entity entity = event.getEntity();
                if (entity instanceof LivingEntity livingEntity && livingEntity.isOnFire()) {
                    event.addMutiAmount( ( e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZANGYAN.id(), "attack", 0.2f)));
                }
            });
        }
    }

    @SubscribeEvent
    public static void OnHurt1 (ExLivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.ZANGYAN, player, EquipmentSlot.MAINHAND, (e) -> {
                Entity entity = event.getEntity();
                if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(ModEffect.EFFECTBLAZING_BRAND.get())) {
                    int level = livingEntity.getEffect(ModEffect.EFFECTBLAZING_BRAND.get()).getAmplifier() + 1;
                    event.addAmount( level * (e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZANGYAN.id(), "burn", 1)));
                }
            });
        }
    }
}
