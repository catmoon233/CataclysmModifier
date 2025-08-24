

package net.exmo.cataclysm_modifier.specialEffects.igins;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HuoYu {
    @SubscribeEvent
    public static void OnTick (TickEvent.PlayerTickEvent event)
    {

        Player player = event.player;
        ExUtil.ifHasSpecialEffect(SpecialEffects.HuoYu, player, (e) -> {
                if (player.isOnFire() && !player.hasEffect(MobEffects.REGENERATION)){
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 1));

                }
            });

    }
    @SubscribeEvent
    public static void OnHurt (ExLivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.HuoYu, player, (e) -> {
                if (player.getAbsorptionAmount()<40){
                    player.setAbsorptionAmount(player.getAbsorptionAmount()+1);
                }
            });
        }
    }

}
