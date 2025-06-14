package be.florens.eggtab;

import be.florens.eggtab.config.Config;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

/**
 * EggTab Mod
 * 原作者: Florens Pauwels
 * 移植到1.20.1版本: Xiaoyu_2009
 */
@Mod(EggTab.MOD_ID)
public class EggTab {
    public static final String MOD_ID = "eggtab";

    // 创建创造模式标签页注册表
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    // 预注册创造模式标签页
    private static final RegistryObject<CreativeModeTab> EGG_TAB = CREATIVE_TABS.register("egg_group", 
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID + ".egg_group"))
            .icon(() -> new ItemStack(Items.CREEPER_SPAWN_EGG))
            .build()
    );

    private static final RegistryObject<CreativeModeTab> BOOK_TAB = CREATIVE_TABS.register("book_group", 
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MOD_ID + ".book_group"))
            .icon(() -> new ItemStack(Items.ENCHANTED_BOOK))
            .build()
    );

    public static CreativeModeTab EGG_GROUP;
    public static CreativeModeTab BOOK_GROUP;

    public EggTab() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CREATIVE_TABS.register(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::onTabContents);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        EGG_GROUP = EGG_TAB.get();
        BOOK_GROUP = BOOK_TAB.get();
    }

    private void onTabContents(BuildCreativeModeTabContentsEvent event) {
        if (Config.eggsGroup && event.getTab() == EGG_TAB.get()) {

            for (Item item : ForgeRegistries.ITEMS) {
                if (isSpawnEgg(item)) {
                    event.accept(new ItemStack(item));
                }
            }
        }
        
        // 附魔书标签页
        if (Config.booksGroup && event.getTab() == BOOK_TAB.get()) {

            // 添加所有附魔书到自定义标签页
            addAllEnchantedBooks(event);
        }
    }
    
    /**
     * 添加所有附魔类型的附魔书
     */
    private void addAllEnchantedBooks(BuildCreativeModeTabContentsEvent event) {
        // 添加基础附魔书（无附魔）
        event.accept(new ItemStack(Items.ENCHANTED_BOOK));
        
        // 添加各种附魔的附魔书
        for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
            // 对于每个附魔，添加该附魔的各个等级
            for (int level = 1; level <= enchantment.getMaxLevel(); level++) {
                ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
                Map<Enchantment, Integer> enchMap = new HashMap<>();
                enchMap.put(enchantment, level);
                EnchantmentHelper.setEnchantments(enchMap, enchantedBook);

                event.accept(enchantedBook);
            }
        }
    }

    public static boolean isSpawnEgg(Item item) {
        return item instanceof SpawnEggItem;
    }

    /**
     * 获取注册表项的标识符
     */
    public static ResourceLocation getRegistryEntryIdentifier(Map.Entry<?, ?> entry) {
        if (entry.getKey() instanceof ResourceLocation) {
            return (ResourceLocation) entry.getKey();
        }
        return null;
    }
} 