package net.exmo.cataclysm_modifier.mixins;

import net.exmo.cataclysm_modifier.init.CMItemInit;
import net.exmo.exmodifier.Exmodifier;
import net.exmo.exmodifier.events.ExCustomTabEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.exmo.exmodifier.Exmodifier.getTabIcon;

@Mixin(Exmodifier.class)
public class TabRegisterMixin {
    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/exmo/exmodifier/events/ExCustomTabEvent;addTab(Ljava/lang/String;Lnet/minecraft/world/item/ItemStack;)V"),remap = false)
    public void setup(ExCustomTabEvent instance, String tabName, ItemStack itemStack) {
        instance.addTab("cataclysm_modifier_tab", CMItemInit.THE_Soul_INCINERATOR.get().getDefaultInstance());
        instance.addTab("exmodifier_tab", getTabIcon());
    }
}
