package me.logwet.hotpotato.mixin.common.block;

import me.logwet.hotpotato.PlayerPatch;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BushBlock.class)
public abstract class BushBlockMixin extends Block {
    public BushBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Player && !level.isClientSide) {
            System.out.println("Touched grass");
            ((PlayerPatch) entity).hotpotato$resetTimeTracker();
        }
    }
}
