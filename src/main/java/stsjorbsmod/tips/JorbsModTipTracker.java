package stsjorbsmod.tips;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stsjorbsmod.JorbsMod;

import java.io.IOException;
import java.util.Properties;

import static stsjorbsmod.JorbsMod.makeID;

// This tracker is for First-Time User Experience (FTUE) tips, not on-hover tooltips
public class JorbsModTipTracker {
    public enum TipKey {
        SNAP,
        MEMORY
    }

    private static final Logger logger = LogManager.getLogger(JorbsModTipTracker.class.getName());
    public static SpireConfig config;
    private static final Properties DEFAULTS = new Properties();
    static {
        for(TipKey key : TipKey.values()) {
            DEFAULTS.setProperty(key.name(), "false");
        }
    }

    public static void initialize() {
        try {
            config = new SpireConfig(JorbsMod.MOD_ID, "JorbsModTipTracker", new Properties());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void save() {
        try {
            config.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean shouldShow(TipKey key) {
        return !config.getBool(key.name());
    }

    public static void neverShowAgain(TipKey key) {
        logger.info(key + " will never be shown again!");
        config.setBool(key.name(), true);
        save();
    }

    public static void showAgain(TipKey key) {
        logger.info(key + " is reactivated");
        config.setBool(key.name(), false);
        save();
    }

    public static void reset() {
        logger.info("resetting state of all tips");
        for(TipKey key : TipKey.values()) {
            config.setBool(key.name(), false);
        }
        save();
    }
}
