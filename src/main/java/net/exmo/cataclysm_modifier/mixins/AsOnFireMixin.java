package net.exmo.cataclysm_modifier.mixins;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class AsOnFireMixin {
    @Inject(method = "isOnFire",  at = @At("HEAD"), cancellable = true)
    public void isOnFire(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof Player livingEntity) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.HUOZHONG, livingEntity, (e) -> {
                cir.setReturnValue(true);
            });
        }
    }
}
