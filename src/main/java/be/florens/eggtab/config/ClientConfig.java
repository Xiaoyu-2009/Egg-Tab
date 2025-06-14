package be.florens.eggtab.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * 客户端配置类
 * 原作者: Florens Pauwels
 * 移植到1.20.1版本: Xiaoyu_2009
 */
public class ClientConfig {

    final ForgeConfigSpec.BooleanValue eggsGroup;
    final ForgeConfigSpec.BooleanValue booksGroup;

    ClientConfig(final ForgeConfigSpec.Builder builder) {
        builder.push("general");
        eggsGroup = builder
                .comment("Move spawn eggs to separate tab")
                .translation("config.eggtab.eggs_group")
                .define("eggsGroup", true);

        booksGroup = builder
                .comment("Move enchanted books to separate tab")
                .translation("config.eggtab.books_group")
                .define("booksGroup", true);
        builder.pop();
    }
}