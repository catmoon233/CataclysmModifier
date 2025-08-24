package net.exmo.cataclysm_modifier.specialEffects.ender_guardian;

import com.github.L_Ender.cataclysm.items.Gauntlet_of_Guard;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class XuKongQianghua {

    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {
        if (event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD))return;
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.XuKongQianghua.id(), serverPlayer, (e) -> {
                event.getEntity().invulnerableTime =0;
                var damageSource = new DamageSource(serverPlayer.serverLevel().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD),serverPlayer);
                event.getEntity().hurt(damageSource, 2);
                event.getEntity().invulnerableTime =0;
            });
        }
    }



}
