package github.com.gengyoubo.sscfe.mixin.client;

import github.com.gengyoubo.sscfe.client.SccfeClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.onixary.shapeShifterCurseForge.ShapeShifterCurseForge;
import net.onixary.shapeShifterCurseForge.client.render.FormAnimationSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FormAnimationSystem.class)
public abstract class FormAnimationSystemMixin {
    @Inject(method = "preferredResource", at = @At("HEAD"), cancellable = true, remap = false)
    private static void sccfe$preferExtensionAnimation(String animationFile, ResourceLocation legacy,
                                                       CallbackInfoReturnable<ResourceLocation> cir) {
        if (!SccfeClientConfig.PREFER_NEW_ANIMATIONS.get()) {
            return;
        }
        ResourceLocation modern = ResourceLocation.fromNamespaceAndPath(ShapeShifterCurseForge.RESOURCE_NAMESPACE,
                "player_animation/new/" + animationFile + ".json");
        if (Minecraft.getInstance().getResourceManager().getResource(modern).isPresent()) {
            cir.setReturnValue(modern);
        }
    }
}
