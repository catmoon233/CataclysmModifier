package net.exmo.cataclysm_modifier.specialEffects.netherite;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.content.helper.ModifierEntryHelper;
import net.exmo.exmodifier.content.modifier.ModifierEntry;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RongYanChongNeng {
    @SubscribeEvent
    public static void OnTick (TickEvent.PlayerTickEvent event)
    {

        Player player = event.player;
        ExUtil.ifHasSpecialEffect(SpecialEffects.RongYanChongNeng, player, (e) -> {
            if (player.isInLava() && !player.hasEffect(MobEffects.REGENERATION)){
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 2));

            }
        });
        if (player.tickCount%20==0 && player.isInLava() ) {
            for (ItemStack eq : player.getInventory().armor) {
                for (ModifierEntry modifierEntry : ModifierEntryHelper.of(eq).getModifierEntriesB()) {
                    if (modifierEntry.hasSpecialEffect(SpecialEffects.RongYanChongNeng)) {
                        eq.setDamageValue((int) Math.max(Math.ceil(eq.getDamageValue() - eq.getMaxDamage()*0.025f),0));
                    }
                }
            }
        }

    }
}
