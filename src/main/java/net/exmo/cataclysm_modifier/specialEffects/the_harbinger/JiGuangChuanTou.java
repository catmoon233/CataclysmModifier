package net.exmo.cataclysm_modifier.specialEffects.the_harbinger;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.exmo.exmodifier.util.TickCooldown;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class JiGuangChuanTou {



    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {
        if (event.getSource().is(DamageTypes.MAGIC))return;
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            if (!TickCooldown.cooldownOk(serverPlayer, SpecialEffects.JiGuangChuanTou.id(), 1))return;

                ExUtil.ifHasSpecialEffectAll(SpecialEffects.JiGuangChuanTou.id(), serverPlayer, (e) -> {
                LivingEntity entity = event.getEntity();
                if (!entity.hasEffect(MobEffects.WITHER)) entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100,1));
                if (event.getSource().is(DamageTypes.MAGIC))return;
                entity.invulnerableTime =0;
                var damageSource = new DamageSource(serverPlayer.serverLevel().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC),serverPlayer);
                entity.hurt(damageSource, 5);
                entity.invulnerableTime =0;
            });
        }
    }

}
