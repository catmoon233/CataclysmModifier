package net.exmo.cataclysm_modifier.specialEffects.ancient_remnant;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class YiHun {


    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.YiHun.id(), player, (e) -> {
                // 新增对亡灵生物伤害减少逻辑
                if (event.getEntity().getMobType() == MobType.UNDEAD) {
                    ExLivingHurtEvent.TotalAmount -= e.getSpecialTagSettingFloatOrDefault(SpecialEffects.YiHun.id(),"attack",0.10f);
                }
            });
        }
    }

    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.YiHun.id(), player, (e) -> {
                // 新增亡灵生物伤害减免逻辑
                if (event.getSource().getEntity() != null &&
                    event.getEntity().getMobType() == MobType.UNDEAD) {
                    ExLivingHurtEvent.TotalAmount -= e.getSpecialTagSettingFloatOrDefault(SpecialEffects.YiHun.id(),"defense",0.25f); // 减少20%伤害
                }
            });
        }
    }
}
