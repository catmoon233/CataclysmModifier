package net.exmo.cataclysm_modifier.specialEffects.netherite;

import net.exmo.cataclysm_modifier.SpecialEffects;
import net.exmo.exmodifier.util.ExUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WajueGaizhuang {
    private static boolean canBreak(BlockPos i, Level level, Player player, double hardness) {
        BlockState state = level.getBlockState(i);
        if (state.isAir()) return false;
        if (!player.hasCorrectToolForDrops(state))
            return false;
        float speed = state.getDestroySpeed(player.level(), i);
        if (speed < 0) return false;
        return hardness < 0 || speed <= hardness;
    }
    @SubscribeEvent
    public static void onDig(BlockEvent.BreakEvent event){
        if (event.getPlayer() instanceof ServerPlayer serverPlayer){
            ExUtil.ifHasSpecialEffect(SpecialEffects.WajueGaizhuang, serverPlayer, EquipmentSlot.MAINHAND, (e) -> {
                // 获取被破坏方块的原始位置和玩家朝向
                var pos = event.getPos();
                var direction = serverPlayer.getDirection();
                
                // 计算前方偏移量（玩家面前第1格）
                var forwardPos = pos.relative(direction);
                
                // 遍历3x3x3立方体区域（中心点向前1格）
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            // 计算相对位置
                            var targetPos = forwardPos.offset(x, y, z);
                            
                            // 跳过空气方块
                            Level level = serverPlayer.level();
                            if (level.isEmptyBlock(targetPos)) continue;
                            var blockstate = level.getBlockState(targetPos);
                            if (!canBreak(targetPos, level, serverPlayer, blockstate.getDestroySpeed(level, targetPos)*3)) continue;

                            BlockEntity blockentity = level.getBlockEntity(targetPos);
                            ItemStack itemstack = serverPlayer.getMainHandItem();
                            ItemStack itemstack1 = itemstack.copy();
                            boolean flag1 = blockstate.canHarvestBlock(level, targetPos, serverPlayer); // previously player.hasCorrectToolForDrops(blockstate)
                            itemstack.mineBlock(level, blockstate, targetPos, serverPlayer);
                            if (itemstack.isEmpty() && !itemstack1.isEmpty())
                                net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(serverPlayer, itemstack1, InteractionHand.MAIN_HAND);
                            boolean flag = removeBlock(targetPos, flag1,level,serverPlayer);

                            if (flag && flag1) {
                                blockstate.getBlock().playerDestroy(level, serverPlayer, targetPos, blockstate,blockentity , itemstack1);
                            }

                        }
                    }
                }
            });
        }
    }
    private static boolean removeBlock(BlockPos p_180235_1_, boolean canHarvest, Level level, Player player) {
        BlockState state = level.getBlockState(p_180235_1_);
        boolean removed = state.onDestroyedByPlayer(level, p_180235_1_,player, canHarvest,level.getFluidState(p_180235_1_));
        if (removed)
            state.getBlock().destroy(level, p_180235_1_, state);
        return removed;
    }

}
