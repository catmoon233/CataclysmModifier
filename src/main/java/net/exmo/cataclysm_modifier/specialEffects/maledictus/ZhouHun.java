package net.exmo.cataclysm_modifier.specialEffects.maledictus;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ZhouHun {


    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.ZhouHun.id(), player, (e) -> {
                if (event.getSource().getEntity() instanceof LivingEntity livingEntity){
                    int amp = livingEntity.hasEffect(ModEffect.EFFECTGHOST_SICKNESS.get()) ? player.getEffect(ModEffect.EFFECTGHOST_SICKNESS.get()).getAmplifier() + 1 : 0;
                    int during = livingEntity.hasEffect(ModEffect.EFFECTGHOST_SICKNESS.get()) ? player.getEffect(ModEffect.EFFECTGHOST_SICKNESS.get()).getDuration() +200 : 200;
                    livingEntity.removeEffect(ModEffect.EFFECTGHOST_SICKNESS.get());
                    livingEntity.addEffect(new MobEffectInstance(ModEffect.EFFECTGHOST_SICKNESS.get(),during,Math.min(amp,4)));
                }
                if (Math.random() <=0.1){
                    event.getLivingHurtEvent().setCanceled(true);
                    player.sendSystemMessage(Component.translatable("message.zhouhun"));
                    player.addEffect(new MobEffectInstance(ModEffect.EFFECTGHOST_FORM.get(),20,0));

                    player.playNotifySound(ModSounds.MALEDICTUS_DEATH.get(), SoundSource.PLAYERS,  1.0f, 1.0f);
                    player.serverLevel().sendParticles(ParticleTypes.SOUL, player.getX(), player.getY(), player.getZ(), 8, 0.1, 0.1, 0.1, 0.3);
                }
            });
        }
    }
}
