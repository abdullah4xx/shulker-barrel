package com.example.shulkerbarrel;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.item.v1.ItemTooltipCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.List;

public class ShulkerBarrelClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, flag, lines) -> {
            if (!stack.is(Items.BARREL)) return;
            ItemContainerContents c = stack.get(DataComponents.CONTAINER);
            if (c == null) return;
            List<ItemStack> items = c.nonEmptyItemCopyStream().toList();
            int shown = 0;
            for (ItemStack s : items) {
                if (shown >= 5) break;
                lines.add(s.getHoverName().copy().append(Component.literal(" x" + s.getCount())));
                shown++;
            }
            if (items.size() > shown) {
                lines.add(Component.literal("... +" + (items.size() - shown) + " more"));
            }
        });
    }
}
