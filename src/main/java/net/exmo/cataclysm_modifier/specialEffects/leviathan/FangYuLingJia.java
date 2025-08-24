package net.exmo.cataclysm_modifier.specialEffects.leviathan;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FangYuLingJia {
    @SubscribeEvent
    public static void OnTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
            ExUtil.ifHasSpecialEffect(SpecialEffects.FangYuLingJia, player, (e) -> {
                if (player.hasEffect(ModEffect.EFFECTBONE_FRACTURE.get())) {
                    player.removeEffect(ModEffect.EFFECTBONE_FRACTURE.get());
                }
            });

    }
    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.FangYuLingJia, player, (e) -> {
                // 免疫骨裂debuff

                event.addAmountB(-1);
                if (player.getHealth() >= player.getMaxHealth()-0.1f) {
                    ExLivingHurtEvent.TotalAmount -= 0.5F;
                }
            });
        }
    }
}