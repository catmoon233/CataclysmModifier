package net.exmo.cataclysm_modifier.specialEffects.netherite;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.exmo.exmodifier.util.TickCooldown;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FangYuGaizhuang {
    @SubscribeEvent
    public static void OnHurt (ExLivingHurtEvent event){
        if (event.getEntity() instanceof Player player ){
            ExUtil.ifHasSpecialEffect(SpecialEffects.FangYuGaizhuang, player, (e) -> {
                if (!TickCooldown.isCooldown(player, SpecialEffects.FangYuGaizhuang.id())){
                    if (!player.hasEffect(ModEffect.EFFECTMONSTROUS.get()) ||player.getEffect(ModEffect.EFFECTMONSTROUS.get()).getAmplifier() < 4){
                        int amp = player.hasEffect(ModEffect.EFFECTMONSTROUS.get()) ? player.getEffect(ModEffect.EFFECTMONSTROUS.get()).getAmplifier() : -1;
                        player.removeEffect(ModEffect.EFFECTMONSTROUS.get());
                        TickCooldown.cooldownOk(player, SpecialEffects.FangYuGaizhuang.id(), 400*2);
                        player.addEffect(new MobEffectInstance(ModEffect.EFFECTMONSTROUS.get(), 200, amp+1));
                    }
                }
            });
        }
    }
}
