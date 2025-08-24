package net.exmo.cataclysm_modifier.specialEffects.ender_guardian;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.cataclysm_modifier.util.CMParticle;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.exmo.exmodifier.util.TickCooldown;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.exmo.exmodifier.util.TickCooldown.cooldownOk;
import static net.exmo.exmodifier.util.TickCooldown.cooldownOkDis;

@Mod.EventBusSubscriber
public class XuKongZhongJi {
    @SubscribeEvent
    public static void OnHurt (ExLivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ExUtil.ifHasSpecialEffect(SpecialEffects.XuKongZhongJi, player, EquipmentSlot.MAINHAND, (e) -> {
                LivingEntity entity = event.getEntity();
                if (!TickCooldown.isCooldown(player, "xukongzhongji") &&entity.hasEffect(ModEffect.EFFECTBLAZING_BRAND.get()) && entity.getEffect(ModEffect.EFFECTBLAZING_BRAND.get()).getAmplifier() >= 4){
                    if (player instanceof ServerPlayer serverPlayer){
                   //     serverPlayer.sendSystemMessage(Component.translatable("message.xukongzhongji"));
                    }
                    int standingOnY = Mth.floor(player.getY()) - 3;
                    Level world = player.level();
                    Vec3 looking = player.getLookAngle();
                    double headY = player.getY() + 1.0D;
                    Vec3[] all = new Vec3[]{looking, looking.yRot(0.5f), looking.yRot(0), looking.yRot(-0.5f)};
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5f, 1F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
                    ScreenShake_Entity.ScreenShake(world, player.position(), 30, 0.1f, 0, 30);
                    for (Vec3 vector3d : all) {
                        float f = (float) Mth.atan2(vector3d.z, vector3d.x);
                        for (int i = 0; i < 5 ;i++) {
                            double d2 = 1.75D * (double) (i + 1);
                            int j = 1 * i;
                            spawnFangs(player.getX() + (double) Mth.cos(f) * d2, headY, player.getZ() + (double) Mth.sin(f) * d2, standingOnY, f, j, world, player);
                        }
                    }
                    cooldownOk(player, SpecialEffects.XuKongZhongJi.id(),120);
                    entity.removeEffect(ModEffect.EFFECTBLAZING_BRAND.get());
                }
            });
        }
    }
    private static boolean spawnFangs(double x, double y, double z, int lowestYCheck, float yRot, int warmupDelayTicks, Level world, Player player) {
        BlockPos blockpos = BlockPos.containing(x, y, z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = world.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(world, blockpos1, Direction.UP)) {
                if (!world.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while (blockpos.getY() >= lowestYCheck);

        if (flag) {
            world.addFreshEntity(new Void_Rune_Entity(world, x, (double) blockpos.getY() + d0, z, yRot, warmupDelayTicks,(float) CMConfig.Voidrunedamage, player));
            return true;
        }
        return false;
    }
}
