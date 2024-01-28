package net.goose.lifesteal.util;

import net.goose.lifesteal.LifeSteal;
import net.minecraft.resources.ResourceLocation;

public class ModResources {
    public static ResourceLocation BARREL_1 = new ResourceLocation("minecraft", "chests/barrel_1");
    public static ResourceLocation MINERS_HOME_TABLE = new ResourceLocation("minecraft", "chests/miners_home");
    public static ResourceLocation MINERS_RUINED_SHACK_TABLE = new ResourceLocation("minecraft", "chests/miners_ruined_shack");
    public static ResourceLocation RICH_CART_TABLE = new ResourceLocation("minecraft", "chests/rich_cart");
    public static ResourceLocation RUINED_LIBRARY_TABLE = new ResourceLocation("minecraft", "chests/ruined_library");

    public static ResourceLocation modLoc(String name) {
        return new ResourceLocation(LifeSteal.MOD_ID, name);
    }

    public static String modLocString(String name) {
        return new ResourceLocation(LifeSteal.MOD_ID, name).toString();
    }
}
