package me.logwet.hotpotato.mixin.common.block;

import net.minecraft.world.level.block.TallGrassBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TallGrassBlock.class)
public abstract class TallGrassBlockMixin extends BushBlockMixin {
    public TallGrassBlockMixin(Properties properties) {
        super(properties);
    }
}
