package net.exmo.cataclysm_modifier.mixins;

import com.github.L_Ender.cataclysm.items.Cursed_bow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cursed_bow.class)
public class Cursed_bowMixin {
    @Inject(method = "releaseUsing",at = @At("HEAD"))
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeleft, CallbackInfo ci) {

    }
}
