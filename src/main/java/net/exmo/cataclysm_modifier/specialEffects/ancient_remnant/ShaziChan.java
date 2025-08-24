package net.exmo.cataclysm_modifier.specialEffects.ancient_remnant;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ShaziChan {

    @SubscribeEvent
    public static void OnTick(MobEffectEvent.Applicable event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ExUtil.ifHasSpecialEffectAll(SpecialEffects.ShazhiChan.id(), player, (e) -> {
            if (event.getEffectInstance().getEffect().equals(ModEffect.EFFECTCURSE_OF_DESERT.get())){
                event.setResult(MobEffectEvent.Applicable.Result.DENY);
            }
        });
    }

    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.ShazhiChan.id(), serverPlayer, (e) -> {
                LivingEntity entity = event.getEntity();
                if (!entity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100,(int) e.getSpecialTagSettingDoubleOrDefault(SpecialEffects.JiGuangChuanTou.id(),"slowness_level",1)));
                if (!entity.hasEffect(MobEffects.WEAKNESS)) entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, (int) e.getSpecialTagSettingDoubleOrDefault(SpecialEffects.JiGuangChuanTou.id(),"weakness_level",1)));

            });
        }
    }

}
