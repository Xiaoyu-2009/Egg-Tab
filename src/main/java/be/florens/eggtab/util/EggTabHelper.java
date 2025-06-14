package be.florens.eggtab.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

/**
 * 辅助功能类，用于处理创造模式标签页相关操作
 */
public class EggTabHelper {

    /**
     * 将物品添加到构建事件中
     * @param item 要添加的物品
     * @param event 创造模式标签页构建事件
     */
    public static void addItemToTab(Item item, BuildCreativeModeTabContentsEvent event) {
        ItemStack stack = new ItemStack(item);
        if (!stack.isEmpty()) {
            event.accept(stack);
        }
    }
} 