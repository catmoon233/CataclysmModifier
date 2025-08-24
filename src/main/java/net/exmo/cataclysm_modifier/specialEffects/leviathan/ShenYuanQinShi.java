package net.exmo.cataclysm_modifier.specialEffects.leviathan;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class ShenYuanQinShi {
    // 新增攻击记录缓存
    private static final Map<ServerPlayer, Integer> attackRecords = new HashMap<>();
    private static final Map<ServerPlayer, Float> damageRecords = new HashMap<>();

    @SubscribeEvent
    public static void OnAttack(LivingHurtEvent event) {
        // 过滤虚空伤害来源防止递归
        if (event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) return;
        
        // 获取攻击者并检查玩家身份
        if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.ShenYuanQinShi.id(), serverPlayer, (e) -> {



                // 修改时间窗口判断条件：使用>=1000确保精确的1秒重置
                if (!attackRecords.containsKey(serverPlayer) || (serverPlayer.tickCount - attackRecords.get(serverPlayer)) >= 40) {
                    attackRecords.put(serverPlayer, serverPlayer.tickCount);
                    damageRecords.put(serverPlayer, 0f);
                }

                // 增加防御性检查确保damageRecords存在条目
                float accumulated = damageRecords.getOrDefault(serverPlayer, 0f);
                if (accumulated < 2.0f) {
                    float addDamage = Math.min(0.5f, 2.0f - accumulated);
                    event.getEntity().invulnerableTime =0;
                    var damageSource = new DamageSource(serverPlayer.serverLevel().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD),serverPlayer);
                    event.getEntity().hurt(damageSource, addDamage);
                    event.getEntity().invulnerableTime =0;
                    damageRecords.put(serverPlayer, accumulated + addDamage);
                }
            });
        }
    }
}