package net.exmo.cataclysm_modifier.specialEffects.maledictus;

import com.github.L_Ender.cataclysm.items.The_Annihilator;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ShaungChui {
    public static boolean isCooldown = false;


    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {

        if (event.getSource().getEntity() instanceof Player player) {
            if (isCooldown) {
                isCooldown = false;
                return;
            }
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.ShuangChui.id(), player, (e) -> {
                if (player.getMainHandItem().getItem() instanceof The_Annihilator) {
                    if (player.getOffhandItem().getItem() instanceof The_Annihilator) {
                        CriticalHitEvent criticalHitEvent = new CriticalHitEvent(player, event.getEntity(), 1.0f, false);
                        MinecraftForge.EVENT_BUS.post(criticalHitEvent);
                        isCooldown = true;
                        event.getEntity().invulnerableTime  = 0;
                        event.getEntity().hurt(event.getSource(), (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.5f*criticalHitEvent.getDamageModifier()));
                        event.getEntity().invulnerableTime  = 0;
                    }
                }
            });
        }
    }

}
