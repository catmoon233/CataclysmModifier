package net.exmo.cataclysm_modifier.specialEffects;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.events.ExCustomTabEvent;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ExamPle {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CV {


    @SubscribeEvent
    public static void TLD(ExCustomTabEvent event) {
        event.addTab("cataclysm_modifier_tab", ModItems.IGNITIUM_ELYTRA_CHESTPLATE.get().getDefaultInstance());

    }
}
    @SubscribeEvent
    public static void OnTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ExUtil.ifHasSpecialEffectAll(SpecialEffects.XuKongNengYuan.id(), player, (e) -> {

        });
    }

    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.XuKongNengYuan.id(), player, (e) -> {

            });
        }
    }

    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.XuKongNengYuan.id(), player, (e) -> {

            });
        }
    }
}
