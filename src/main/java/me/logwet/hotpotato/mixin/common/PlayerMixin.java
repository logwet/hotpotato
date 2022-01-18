package me.logwet.hotpotato.mixin.common;

import me.logwet.hotpotato.HotPotato;
import me.logwet.hotpotato.PlayerPatch;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerPatch {
    @Unique private int timeTracker = 0;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void hotpotato$resetTimeTracker() {
        this.timeTracker = 0;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void checkStatus(CallbackInfo ci) {
        if (!this.level.isClientSide) {
            this.timeTracker++;

            if (this.timeTracker >= HotPotato.HOT_POTATO_DELAY) {
                this.hotpotato$resetTimeTracker();
                HotPotato.killPlayer((Player) (Object) this);
            }
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void checkFall(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == DamageSource.FALL) {
            this.hotpotato$resetTimeTracker();
        }
    }
}
