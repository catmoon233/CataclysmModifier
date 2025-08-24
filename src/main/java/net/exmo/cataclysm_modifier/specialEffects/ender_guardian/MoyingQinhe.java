package net.exmo.cataclysm_modifier.specialEffects.ender_guardian;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MoyingQinhe {
    @SubscribeEvent
    public static void OnTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ExUtil.ifHasSpecialEffectAll(SpecialEffects.MoyingQinhe.id(), player, (e) -> {
            // 使周围16格内的末影人和潜影贝保持中立
            player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(16))
                .stream()
                .filter(entity -> entity instanceof EnderMan || entity instanceof Shulker)
                .forEach(entity -> {
                    entity.setLastHurtByMob(null);
                    ((PathfinderMob) entity).setTarget(null);
                });
        });
    }

    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {
        if (event.getSource().is(DamageTypes.MAGIC))return;
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.MoyingQinhe.id(), player, (e) -> {
                // 添加1点魔法伤害
                LivingEntity entity = event.getEntity();
                entity.invulnerableTime =0;
                entity.hurt(player.damageSources().magic(), 1.0f);
                entity.invulnerableTime =0;
            });
        }
    }

    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.MoyingQinhe.id(), player, (e) -> {
                // 减少10%末地生物伤害
                if (event.getSource().getEntity() instanceof EnderMan
                    || event.getSource().getEntity() instanceof Shulker
                    || event.getSource().getEntity() instanceof Endermite) {
                    ExLivingHurtEvent.TotalAmount   -=e.getSpecialTagSettingFloatOrDefault(SpecialEffects.XuKongNengYuan.id(),"defense",0.2f);
                }
            });
        }
    }
}
