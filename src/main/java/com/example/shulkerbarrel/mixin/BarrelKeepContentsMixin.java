package com.example.shulkerbarrel.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseContainerBlockEntity.class)
public class BarrelKeepContentsMixin {
    // require = 0: if the method name differs in 26.2 the game still starts (the mixin just won't apply)
    @Inject(method = "preRemoveSideEffects", at = @At("HEAD"), cancellable = true, require = 0)
    private void barrel$keepContents(BlockPos pos, BlockState state, CallbackInfo ci) {
        if ((Object) this instanceof BarrelBlockEntity) {
            ci.cancel();
        }
    }
}
