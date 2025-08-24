package net.exmo.cataclysm_modifier.mixins;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.item.BowItem.getPowerForTime;

@Mixin(BowItem.class)
public abstract class YanDanMixin {
    @Shadow public abstract int getUseDuration(ItemStack p_40680_);

    @Shadow public abstract AbstractArrow customArrow(AbstractArrow arrow);

    private void shootFireball$emo_shootFireball(LivingEntity livingEntity, float damage,  float s) {
        Ignis_Fireball_Entity shot = new Ignis_Fireball_Entity(livingEntity.level(), livingEntity);
        shot.setPos(livingEntity.getX() - (double)(livingEntity.getBbWidth() + 1.0F) * 0.15 * (double) Mth.sin(livingEntity.yBodyRot * ((float)Math.PI / 180F)), livingEntity.getY() + (double)1.0F, livingEntity.getZ() + (double)(livingEntity.getBbWidth() + 1.0F) * 0.15 * (double)Mth.cos(livingEntity.yBodyRot * ((float)Math.PI / 180F)));
        shot.getPersistentData().putBoolean("IsPlayer", true);
        shot.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(),0, s, damage);
        shot.setUp(1);

        livingEntity.level().addFreshEntity(shot);
    }
    @Inject(method = "releaseUsing", at = @At("HEAD"))
    public void releaseUsing(ItemStack itemStack, Level p_40668_, LivingEntity p_40669_, int p_40670_, CallbackInfo ci) {
        if (p_40669_ instanceof ServerPlayer serverPlayer) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.YanDan, serverPlayer, EquipmentSlot.MAINHAND,(e) -> {
                int i = this.getUseDuration(itemStack) - p_40670_;
                ItemStack arrow = serverPlayer.getProjectile(itemStack);
                if (i < 0) return;
                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    ArrowItem arrowitem = (ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
                    AbstractArrow abstractarrow = arrowitem.createArrow(p_40668_, arrow, serverPlayer);
                    abstractarrow = this.customArrow(abstractarrow);
                    int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
                    if (j > 0) {
                        abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                    }

                    shootFireball$emo_shootFireball(  serverPlayer, (float) (abstractarrow.getBaseDamage()+3.0F),f*5f);
                }

            });
        }
    }
}
