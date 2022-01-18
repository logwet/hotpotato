package me.logwet.hotpotato.mixin.common;

import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DamageSource.class)
public interface DamageSourceFactory {
    @Invoker("<init>")
    static DamageSource newDamageSource(String string) {
        throw new AssertionError();
    }

    @Invoker("bypassArmor")
    DamageSource invokeBypassArmor();

    @Invoker("bypassMagic")
    DamageSource invokeBypassMagic();
}
