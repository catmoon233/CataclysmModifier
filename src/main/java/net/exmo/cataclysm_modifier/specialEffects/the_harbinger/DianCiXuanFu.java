package net.exmo.cataclysm_modifier.specialEffects.the_harbinger;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DianCiXuanFu {
    @SubscribeEvent
    public static void OnTick(MobEffectEvent.Applicable event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ExUtil.ifHasSpecialEffectAll(SpecialEffects.DianCiXuanFu.id(), player, (e) -> {
            if (event.getEffectInstance().getEffect().equals(MobEffects.LEVITATION)){
                event.setResult(MobEffectEvent.Applicable.Result.DENY);
            }
        });
    }

    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (!event.getSource().is(DamageTypes.FALL))return;
        if (event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.DianCiXuanFu.id(), player, (e) -> {
                event.getLivingHurtEvent().setCanceled(true);
            });
        }
    }
}
