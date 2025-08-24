package net.exmo.cataclysm_modifier.specialEffects.maledictus;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExLivingHurtEvent;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ZhangJi {


    public static void spawnHalberd(double x, double z, double minY, double maxY, float rotation, int delay, Level world, LivingEntity player) {
        BlockPos blockpos = BlockPos.containing(x, maxY, z);
        boolean flag = false;
        double d0 = (double)0.0F;

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
        } while(blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            world.addFreshEntity(new Phantom_Halberd_Entity(world, x, (double)blockpos.getY() + d0, z, rotation, delay, player, (float) CMConfig.PhantomHalberddamage));
        }

    }

    @SubscribeEvent
    public static void OnAttack(ExLivingHurtEvent event) {

        if (event.getSource().getEntity() instanceof Player player) {

            ExUtil.ifHasSpecialEffectAll(SpecialEffects.ZhanJi.id(), player, (e) -> {
                if (event.getEntity().hasEffect(ModEffect.EFFECTGHOST_SICKNESS.get())){
                    //敌人脚下
                    spawnHalberd(event.getEntity().getX(), event.getEntity().getZ(), event.getEntity().getY() - 5f, event.getEntity().getY() , 0, 2, event.getEntity().level(), player);
                }
            });
        }
    }

}
