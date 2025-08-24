package net.exmo.cataclysm_modifier.mixins;

import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import net.exmo.exmodifier.Exmodifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sandstorm_Entity.class)
public class Sandstorm_EntityMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/effect/Sandstorm_Entity;setLifespan(I)V",shift = At.Shift.BEFORE))
    public void tick(CallbackInfo ci)
    {
        Sandstorm_Entity sandstorm_entity = (Sandstorm_Entity) (Object) this;

        if (sandstorm_entity.getPersistentData()!=null && sandstorm_entity.getPersistentData().contains("exmodifier:shabaofeng") && !sandstorm_entity.getPersistentData().getBoolean("exmodifier:shabaofeng"))return;
        double radius = (double)11.0F;

        Level world = sandstorm_entity.level();

        for(LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, sandstorm_entity.getBoundingBox().inflate(radius))) {
            if (!(entity instanceof Player) || !((Player)entity).getAbilities().invulnerable) {
                Vec3 diff = entity.position().subtract(sandstorm_entity.position().add(world.random.nextFloat()  - 0.5F, world.random.nextFloat()  - 0.5F, world.random.nextFloat() -0.5f));
                diff = diff.normalize().scale(0.4);
                entity.setDeltaMovement(entity.getDeltaMovement().subtract(diff));
            }
        }
    }
}
