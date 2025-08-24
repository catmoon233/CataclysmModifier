package net.exmo.cataclysm_modifier.mixins;

import com.github.L_Ender.cataclysm.client.model.item.Incinerator_Model;
import com.github.L_Ender.cataclysm.client.render.CMItemstackRenderer;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.exmo.cataclysm_modifier.init.CMItemInit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.exmo.cataclysm_modifier.Cataclysm_modifier.MODID;

@Mixin(CMItemstackRenderer.class)
public class ItemRenderMixin {
    @Shadow @Final private static Incinerator_Model THE_INCINERATOR_MODEL;
    private static final ResourceLocation THE_SOUL_INCINERATOR_TEXTURE = ResourceLocation.tryBuild(MODID, "textures/item/the_soul_incinerator.png");
    private static final ResourceLocation THE_ABYSS_INCINERATOR_TEXTURE = ResourceLocation.tryBuild(MODID, "textures/item/the_abyss_incinerator.png");

    @Inject(method = "renderByItem", at = @At("HEAD"))
    public void renderByItem(ItemStack itemStackIn, ItemDisplayContext transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, CallbackInfo ci){
        if (itemStackIn.getItem() == CMItemInit.THE_Soul_INCINERATOR.get()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5F, 0.5F, 0.5F);
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(THE_SOUL_INCINERATOR_TEXTURE), false, itemStackIn.hasFoil());
            THE_INCINERATOR_MODEL.renderToBuffer(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
        if (itemStackIn.getItem() == CMItemInit.THE_Abyss_INCINERATOR.get()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5F, 0.5F, 0.5F);
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(THE_ABYSS_INCINERATOR_TEXTURE), false, itemStackIn.hasFoil());
            THE_INCINERATOR_MODEL.renderToBuffer(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }
}
