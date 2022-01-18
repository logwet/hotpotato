package me.logwet.hotpotato;

import me.logwet.hotpotato.mixin.common.DamageSourceFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HotPotato implements ModInitializer {
    public static final String MODID = "hotpotato";
    public static final String VERSION =
            FabricLoader.getInstance()
                    .getModContainer(MODID)
                    .get()
                    .getMetadata()
                    .getVersion()
                    .getFriendlyString();
    public static final boolean IS_CLIENT =
            FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final int HOT_POTATO_DELAY = 900;

    private static final DamageSource HOT_POTATO_DAMAGE_SOURCE;

    static {
        HOT_POTATO_DAMAGE_SOURCE =
                ((DamageSourceFactory)
                                ((DamageSourceFactory)
                                                DamageSourceFactory.newDamageSource("hotpotato"))
                                        .invokeBypassArmor())
                        .invokeBypassMagic();
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MODID + " v" + VERSION + "] " + message);
    }

    public static void killPlayer(Player player) {
        player.hurt(HotPotato.HOT_POTATO_DAMAGE_SOURCE, 1000);
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Main class initialized!");
    }
}
