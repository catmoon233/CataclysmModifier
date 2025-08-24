package net.exmo.cataclysm_modifier.mixins;

import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Ignis_Fireball_Entity.class)
public class IgnisFireballEntityMixin {
    @Inject(method = "onHitEntity",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public void onHitEntity(EntityHitResult result, CallbackInfo ci)
    {
        Ignis_Fireball_Entity ignisFireballEntity = (Ignis_Fireball_Entity) (Object) this;
        if (ignisFireballEntity.getPersistentData()!=null && ignisFireballEntity.getPersistentData().contains("IsPlayer") && ignisFireballEntity.getPersistentData().getBoolean("IsPlayer"))
        {
            result.getEntity().invulnerableTime = 0;
        }
    }

}
