package net.exmo.cataclysm_modifier.specialEffects.netherite;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HairenerShou {
    @SubscribeEvent
    public static void OnTick (TickEvent.PlayerTickEvent event) {

        Player player = event.player;
        ExUtil.ifHasSpecialEffect(SpecialEffects.HairenerShou, player, (e) -> {
            if (player.getHealth()<= player.getMaxHealth()/2){
                int effect = Math.min ((int) (player.getMaxHealth() /20),4);
                if (player.hasEffect(ModEffect.EFFECTMONSTROUS.get()) && player.getEffect(ModEffect.EFFECTMONSTROUS.get()).getAmplifier()==0){
                    player.removeEffect(ModEffect.EFFECTMONSTROUS.get());
                player.addEffect(new MobEffectInstance(ModEffect.EFFECTMONSTROUS.get(), 200, effect));
            }
                }
        });
    }
    @SubscribeEvent
    public static void OnHurt (ExLivingHurtEvent event) {

        if (event.getEntity() instanceof Player player ){
        ExUtil.ifHasSpecialEffect(SpecialEffects.HairenerShou, player, (e) -> {
           if (player.hasEffect(ModEffect.EFFECTMONSTROUS.get())){
               int amount = player.getEffect(ModEffect.EFFECTMONSTROUS.get()).getAmplifier()+1;
               event.addAmountB(-amount *  (e.getSpecialTagSettingFloatOrDefault(SpecialEffects.HairenerShou.id(), "hurt", 1)));
           }
        });
    }
    }

}
