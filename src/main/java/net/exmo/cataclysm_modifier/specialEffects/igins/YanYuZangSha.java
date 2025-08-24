package net.exmo.cataclysm_modifier.specialEffects.igins;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.cataclysm_modifier.util.CMParticle;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.exmo.exmodifier.util.TickCooldown;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static net.exmo.exmodifier.util.TickCooldown.cooldownOkDis;

@Mod.EventBusSubscriber
public class YanYuZangSha {
    private static double getNoBrandArmor(LivingEntity livingEntity){
        if (livingEntity.getAttribute(Attributes.ARMOR)!=null){
            AtomicReference<Double> armor = new AtomicReference<>(livingEntity.getAttributeBaseValue(Attributes.ARMOR));
            AtomicReference<Double> armorB = new AtomicReference<>(1d);
            AtomicReference<Double> armorT = new AtomicReference<>(1d);
            livingEntity.getAttribute(Attributes.ARMOR).getModifiers().forEach(
                    e->{
                        if (e.getOperation() == AttributeModifier.Operation.ADDITION){
                            armor.updateAndGet(v -> ((double) (v + e.getAmount())));
                        }

                    }
            );
            livingEntity.getAttribute(Attributes.ARMOR).getModifiers().forEach(
                    e->{
                        if (e.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE){
                            armorB.updateAndGet(v -> ((double) (v + e.getAmount())));
                        }
                    }
            );
            livingEntity.getAttribute(Attributes.ARMOR).getModifiers().forEach(
                    e->{
                        if (!e.getId().equals(UUID.fromString("B237E76D-15E8-4513-A735-55BB25C33603")) && !e.getId().equals(UUID.fromString("68078724-8653-42D5-A245-9D14A1F54685"))){
                        if (e.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL){
                            armorT.updateAndGet(v -> ((double) (v + e.getAmount())));
                        }
                        }
                    }
            );
            return Math.max(armor.get()*armorB.get()*armorT.get(),1);
        }
        return 1;

    }
    @SubscribeEvent
    public static void OnHurt (ExLivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.YanYuZangSha, player, EquipmentSlot.MAINHAND, (e) -> {
                LivingEntity entity = event.getEntity();
                if (!TickCooldown.isCooldown(player, "yanyuzangsha") &&entity.hasEffect(ModEffect.EFFECTBLAZING_BRAND.get()) && entity.getEffect(ModEffect.EFFECTBLAZING_BRAND.get()).getAmplifier() >= 4){
                    event.getLivingHurtEvent().setAmount((float) (Math.pow(getNoBrandArmor(entity),e.getSpecialTagSettingDoubleOrDefault(SpecialEffects.YanYuZangSha.id(), "armor", 0.6))*player.getAttributeValue(Attributes.ATTACK_DAMAGE) * e.getSpecialTagSettingFloatOrDefault(SpecialEffects.YanYuZangSha.id(), "yanyuzangsha_damage", 1.0f)));
                    if (player instanceof ServerPlayer serverPlayer){
                        serverPlayer.sendSystemMessage(Component.translatable("message.yanyuzangsha"));
                        serverPlayer.playNotifySound(ModSounds.IGNIS_DEATH.get(), SoundSource.PLAYERS, 1f, 1f);
                        CMParticle.sendParticleCircle(serverPlayer.serverLevel(),entity, ParticleTypes.LAVA, 1.5f, 20,0);
                    }

                    cooldownOkDis(player, SpecialEffects.YanYuZangSha.id(),400);
                    entity.removeEffect(ModEffect.EFFECTBLAZING_BRAND.get());
                }
            });
        }
    }
}
