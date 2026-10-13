package com.example.shulkerbarrel.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public class BarrelKeepContentsMixin {
    @Inject(method = "preRemoveSideEffects", at = @At("HEAD"), cancellable = true, require = 1)
    private void barrel$keepContents(BlockPos pos, BlockState state, CallbackInfo ci) {
        if ((Object) this instanceof BarrelBlockEntity) {
            ci.cancel();
        }
    }
}
