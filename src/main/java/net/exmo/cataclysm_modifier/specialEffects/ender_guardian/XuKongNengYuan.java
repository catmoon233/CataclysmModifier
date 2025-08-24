package net.exmo.cataclysm_modifier.specialEffects.ender_guardian;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class XuKongNengYuan {
    // 新增末地属性修饰符
    private static final AttributeModifier END_SPEED_BOOST = new AttributeModifier(
        "xukong_end_speed", 
        0.2, 
        AttributeModifier.Operation.MULTIPLY_BASE
    );
    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {

        // 获取攻击者并检查玩家身份
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.XuKongNengYuan.id(), serverPlayer, (e) -> {
                event.addAmountB(-3);
            });
        }
    }

    @SubscribeEvent
    public static void OnTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ExUtil.ifHasSpecialEffectAll(SpecialEffects.XuKongNengYuan.id(), player, (e) -> {
            // 仅在末地生效
            if (player.level() instanceof ServerLevel && ((ServerLevel) player.level()).dimensionType().effectsLocation().toString().contains("the_end")) {
                
                // 移动速度加成
                if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(END_SPEED_BOOST)) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(END_SPEED_BOOST);
                }
                
                // 伤害吸收逻辑
                float maxAbsorption = player.getMaxHealth() * 2;

                
                // 每秒增加0.5点吸收值
                float absorptionAmount = player.getAbsorptionAmount();
                if (player.tickCount % 20 == 0 && absorptionAmount < maxAbsorption) {
                    float newAbsorption = Math.min(absorptionAmount + 0.5f, maxAbsorption);
                    player.setAbsorptionAmount(newAbsorption+0.5f);
                }
                
                //
                
            } else {
                // 离开末地时移除效果
                if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(END_SPEED_BOOST)) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(END_SPEED_BOOST);
                }

            }
        });
    }
}