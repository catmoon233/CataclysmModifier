package net.exmo.cataclysm_modifier.specialEffects.maledictus;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.cataclysm_modifier.util.CMParticle;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ZhouHuan {
    @SubscribeEvent
    public static void OnTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ExUtil.ifHasSpecialEffectAll(SpecialEffects.ZhouHuan.id(), player, (e) -> {
            if (player.getMainHandItem().getItem() instanceof ProjectileWeaponItem projectileWeaponItem){
                if (player.level() instanceof ServerLevel serverLevel) {
                    CMParticle.sendParticleCircle(serverLevel, player, ParticleTypes.GLOW_SQUID_INK, 2, 8,-1f);
                }

            }
            if (4-player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream()
                    .mapToDouble(AttributeModifier::getAmount).sum() >=1.5 ){
                if (player.level() instanceof ServerLevel serverLevel) {
                    CMParticle.sendParticleCircle(serverLevel, player, ParticleTypes.DRIPPING_LAVA, 2, 8,-1f);
                }

            }
            if (4-player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream()
                    .mapToDouble(AttributeModifier::getAmount).sum() < 1.5 ){
                if (player.level() instanceof ServerLevel serverLevel) {
                    CMParticle.sendParticleCircle(serverLevel, player, DustParticleOptions.REDSTONE, 2, 8,-1f);


                }

            }
        });
    }

    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.ZhouHuan.id(), player, (e) -> {
                if (player.getMainHandItem().getItem() instanceof ProjectileWeaponItem projectileWeaponItem){
                    if (!(event.getSource().getDirectEntity() instanceof Projectile))return;
                    event.addMutiAmount(e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZhouHuan.id(),"attack-bow",e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZhouHuan.id(),"attack",0.35f)));

                }
                if (4+player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream()
                        .mapToDouble(AttributeModifier::getAmount).sum() >=1.5 ){
                    event.addAmountB(event.getEntity().getHealth()*e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZhouHuan.id(),"attack>=1.5",0.005f));

                }
                if (4+player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream()
                        .mapToDouble(AttributeModifier::getAmount).sum() <1.5 ){
                    event.addMutiAmount(e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZhouHuan.id(),"attack<1.5",0.5f));

                }
            });
        }
    }

    @SubscribeEvent
    public static void OnHurt(ExLivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.ZhouHuan.id(), player, (e) -> {
                if (4-player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream()
                        .mapToDouble(AttributeModifier::getAmount).sum() <1.5 ){
                    event.addMutiAmount(e.getSpecialTagSettingFloatOrDefault(SpecialEffects.ZhouHuan.id(),"hurt<1.5",0.2f));

                }
            });
        }
    }
}
