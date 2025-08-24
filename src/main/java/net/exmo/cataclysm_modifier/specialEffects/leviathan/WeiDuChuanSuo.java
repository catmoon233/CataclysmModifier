package net.exmo.cataclysm_modifier.specialEffects.leviathan;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.cataclysm_modifier.util.CMParticle;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WeiDuChuanSuo {
    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.WeiDuChuanSuo, serverPlayer, (e) -> {
                if (Math.random() <=  0.1){
                    event.getLivingHurtEvent().setCanceled(true);
                    serverPlayer.serverLevel().sendParticles(ParticleTypes.REVERSE_PORTAL,  serverPlayer.getX(), serverPlayer.getY()+1, serverPlayer.getZ(), 15, 0.3f, 0.3f, 0.3f, 0.35f);
                    serverPlayer.sendSystemMessage(Component.translatable("message.weiduchuansuo"));
                    serverPlayer.playNotifySound(SoundEvents.END_PORTAL_SPAWN,  SoundSource.PLAYERS,5f, 1.0f);
                }
            });
        }
    }
}
