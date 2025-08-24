package net.exmo.cataclysm_modifier.mixins;

import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.items.Sandstorm_In_A_Bottle;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Sandstorm_In_A_Bottle.class)
public class Sandstorm_In_A_BottleMixin {
    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    public boolean use(Level instance, Entity entity) {
        if (entity instanceof Sandstorm_Entity sandstormEntity) {
            if (sandstormEntity.getCreatorEntity() instanceof Player player) {
                if (ExUtil.ifHasSpecialEffectAll(SpecialEffects.ShabaoFeng.id() , player,(e)->{
                    sandstormEntity.getPersistentData().putBoolean("exmodifier:shabaofeng",true);
                    sandstormEntity.setLifespan(400);
                }));

             return    instance.addFreshEntity(sandstormEntity);
            }
        }
     return false;
    }
}
