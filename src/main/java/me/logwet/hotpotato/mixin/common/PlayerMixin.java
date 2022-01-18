package me.logwet.hotpotato.mixin.common;

import java.util.Objects;
import me.logwet.hotpotato.HotPotato;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Unique private Vec3 lastPos;
    @Unique private int timeTracker = 0;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean hurt(DamageSource source, float amount);

    @Inject(method = "tick", at = @At("TAIL"))
    private void checkStatus(CallbackInfo ci) {
        Vec3 position = this.position();
        if (Objects.nonNull(position) && !this.level.isClientSide) {
            if (position.equals(this.lastPos)) {
                this.timeTracker++;
            } else {
                this.lastPos = position;
                this.timeTracker = 0;
            }

            if (this.timeTracker >= HotPotato.HOT_POTATO_DELAY) {
                this.timeTracker = 0;
                HotPotato.killPlayer((Player) (Object) this);
            }
        }
    }
}
