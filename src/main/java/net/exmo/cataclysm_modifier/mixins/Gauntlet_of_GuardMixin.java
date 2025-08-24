package net.exmo.cataclysm_modifier.mixins;

import com.github.L_Ender.cataclysm.items.Gauntlet_of_Guard;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gauntlet_of_Guard.class)
public class Gauntlet_of_GuardMixin {
    private static LivingEntity livingEntity;
    @Redirect(method = "onUseTick",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    public void onUseTick(LivingEntity instance, Vec3 vec3) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            //if (serverPlayer)
            if (serverPlayer == instance)return;
            ExUtil.ifHasSpecialEffectAll(SpecialEffects.XuKongQianghua.id(), serverPlayer, (e) -> {
                var damageSource = new DamageSource(serverPlayer.serverLevel().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD),serverPlayer);
                instance.hurt(damageSource, 3);
            });
        }
        Vec3 diff = instance.position().subtract(livingEntity.position().add((double)0.0F, (double)0.0F, (double)0.0F));
        diff = diff.normalize().scale(0.1);
        instance.setDeltaMovement(instance.getDeltaMovement().subtract(diff));
    }
    @Inject(method = "onUseTick",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V",shift = At.Shift.BEFORE))
    public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count, CallbackInfo ci) {
        Gauntlet_of_GuardMixin.livingEntity = livingEntityIn;
    }

}
