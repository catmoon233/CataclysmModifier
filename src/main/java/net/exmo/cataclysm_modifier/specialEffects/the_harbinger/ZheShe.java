package net.exmo.cataclysm_modifier.specialEffects.the_harbinger;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ZheShe {

    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.ZheShe.id(), player, (e) -> {
                // 新增伤害类型判断逻辑
                if (event.getSource().getDirectEntity() instanceof Projectile || event.getSource().is(DamageTypes.EXPLOSION)) {
                    // 减少50%伤害
                    ExLivingHurtEvent.TotalAmount-=e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZheShe.id(),"defense",0.5f);
                }
            });
        }
    }
}
