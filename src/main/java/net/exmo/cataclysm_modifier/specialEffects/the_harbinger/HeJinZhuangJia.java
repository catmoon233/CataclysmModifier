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
public class HeJinZhuangJia {
    @SubscribeEvent
    public static void OnTick(MobEffectEvent.Applicable event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ExUtil.ifHasSpecialEffectAll(SpecialEffects.HeJinZhuangJia.id(), player, (e) -> {
            if (event.getEffectInstance().getEffect().equals(MobEffects.WITHER)){
                event.setResult(MobEffectEvent.Applicable.Result.DENY);
            }
        });
    }
    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
            if (event.getEntity() instanceof Player player) {
                ExUtil.ifHasSpecialEffectAll(SpecialEffects.HeJinZhuangJia.id(), player, (e) -> {
                    if (event.getSource().is(DamageTypes.IN_FIRE) || event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.LAVA)) {
                        event.getLivingHurtEvent().setCanceled(true);
                    }else {
                        if (player.getAbsorptionAmount() < player.getMaxHealth() * 2) {
                            player.setAbsorptionAmount(Math.min(player.getAbsorptionAmount() + event.getAmount() * e.getSpecialTagSettingFloatOrDefault(SpecialEffects.HeJinZhuangJia.id(), "absorption", 0.1f), player.getMaxHealth() * 2));
                        }
                    }
                });
            }
        }


}
