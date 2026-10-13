package com.example.shulkerbarrel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.ArrayList;
import java.util.List;

public class ShulkerBarrelMod implements ModInitializer {
    @Override
    public void onInitialize() {
        DefaultItemComponentEvents.MODIFY.register(context ->
            context.modify(Items.BARREL, builder -> {
                builder.set(DataComponents.MAX_STACK_SIZE, 1);
                builder.set(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            })
        );

        UseItemCallback.EVENT.register((player, level, hand) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.is(Items.BARREL)) return InteractionResult.PASS;
            if (level.isClientSide()) return InteractionResult.SUCCESS;

            ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
            NonNullList<ItemStack> list = NonNullList.withSize(27, ItemStack.EMPTY);
            if (contents != null) contents.copyInto(list);

            boolean[] loading = {true};
            SimpleContainer inv = new SimpleContainer(27) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    if (loading[0]) return;
                    List<ItemStack> out = new ArrayList<>();
                    for (int i = 0; i < getContainerSize(); i++) out.add(getItem(i).copy());
                    stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(out));
                }
            };
            for (int i = 0; i < 27; i++) inv.setItem(i, list.get(i));
            loading[0] = false;

            ((ServerPlayer) player).openMenu(new SimpleMenuProvider(
                (id, playerInv, p) -> ChestMenu.threeRows(id, playerInv, inv),
                Component.translatable("block.minecraft.barrel")));
            return InteractionResult.SUCCESS;
        });
    }
}
