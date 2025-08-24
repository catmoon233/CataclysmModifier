package net.exmo.cataclysm_modifier.specialEffects.leviathan;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ShenYuanZhiShui {
    // 新增速度修饰符常量
    private static final AttributeModifier WATER_SPEED_BOOST = new AttributeModifier("shenyuan_water_speed", 0.2, AttributeModifier.Operation.MULTIPLY_BASE);

    @SubscribeEvent
    public static void OnTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ExUtil.ifHasSpecialEffectAll(SpecialEffects.ShenYuanZhiShui.id(), player, (e) -> {
            // 免疫深渊烧灼效果
            if (player.hasEffect(ModEffect.EFFECTABYSSAL_BURN.get())) {
                player.removeEffect(ModEffect.EFFECTABYSSAL_BURN.get());
            }

            // 水中效果处理
            if (player.isInWater()) {
                // 添加水下呼吸和夜视效果
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 1)); // II级效果
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 1));
                
                // 增加移动速度
                if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(WATER_SPEED_BOOST)) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(WATER_SPEED_BOOST);
                }
            } else {
                // 离开水时移除速度加成
                if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(WATER_SPEED_BOOST)) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(WATER_SPEED_BOOST);
                }
            }
        });
    }
}