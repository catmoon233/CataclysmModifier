package net.exmo.cataclysm_modifier.specialEffects.ender_guardian;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MoyingQianghua {
    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.MoyingQianghua.id(), player, (e) -> {
                if (player.isUsingItem()) ExLivingHurtEvent.TotalAmount-=e.getSpecialTagSettingFloatOrDefault(SpecialEffects.MoyingQianghua.id(),"attack",0.2f);
                if (!event.getSource().is(DamageTypes.EXPLOSION))return;
                ExLivingHurtEvent.TotalAmount-=e.getSpecialTagSettingFloatOrDefault(SpecialEffects.MoyingQianghua.id(),"defense",0.4f);

            });
        }
    }
}
