package net.exmo.cataclysm_modifier.specialEffects.netherite;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ZhanChuiGaizZhuang {
    @SubscribeEvent
    public static void OnHurt (ExLivingHurtEvent event)
    {

        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.Zhanchuigaizhuang, player, EquipmentSlot.MAINHAND, (e) -> {
                Entity entity = event.getEntity();
                if (entity instanceof LivingEntity livingEntity && livingEntity.isOnFire()) {
                    event.addMutiAmount( ( e.getSpecialTagSettingFloatOrDefault(SpecialEffects.Zhanchuigaizhuang.id(), "attack", 0.2f)));
                }
            });
        }
    }
}
