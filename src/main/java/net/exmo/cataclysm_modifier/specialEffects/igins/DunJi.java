
package net.exmo.cataclysm_modifier.specialEffects.igins;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DunJi {
    @SubscribeEvent
    public static void OnHurt (ExLivingHurtEvent event)
    {

        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.DUNJI, player, EquipmentSlot.OFFHAND, (e) -> {
                Entity entity = event.getEntity();
                if (!player.getCooldowns().isOnCooldown(player.getOffhandItem().getItem())) {
                    player.getCooldowns().addCooldown(player.getOffhandItem().getItem(), 40);
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), 10, 1));
                    }
                }
            });
        }
    }

}
