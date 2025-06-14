package be.florens.eggtab.config;

import be.florens.eggtab.EggTab;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = EggTab.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec CLIENT_SPEC;
    private static final ClientConfig CLIENT;
    public static boolean eggsGroup;
    public static boolean booksGroup;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT = specPair.getLeft();
        CLIENT_SPEC = specPair.getRight();
    }

    public static void bakeClient() {
        Config.eggsGroup = CLIENT.eggsGroup.get();
        Config.booksGroup = CLIENT.booksGroup.get();
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == Config.CLIENT_SPEC) {
            bakeClient();
        }
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == Config.CLIENT_SPEC) {
            bakeClient();
        }
    }
}