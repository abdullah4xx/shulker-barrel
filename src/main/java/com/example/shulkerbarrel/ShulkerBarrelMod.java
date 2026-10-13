package com.example.shulkerbarrel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;

public class ShulkerBarrelMod implements ModInitializer {
    @Override
    public void onInitialize() {
        DefaultItemComponentEvents.MODIFY.register(context ->
            context.modify(Items.BARREL, builder -> {
                builder.set(DataComponents.MAX_STACK_SIZE, 1);
                builder.set(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            })
        );
    }
}
