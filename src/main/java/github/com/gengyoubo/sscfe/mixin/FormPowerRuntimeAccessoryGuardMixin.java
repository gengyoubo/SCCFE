package github.com.gengyoubo.sscfe.mixin;

import com.google.gson.JsonObject;
import net.minecraft.world.entity.player.Player;
import net.onixary.shapeShifterCurseForge.power.FormPowerRuntime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Prevents an optional Curios backend from crashing older core versions. */
@Mixin(FormPowerRuntime.class)
public abstract class FormPowerRuntimeAccessoryGuardMixin {
    @Inject(method = "checkAccessory", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private static void sscfe$skipWhenCuriosMissing(Player actor, JsonObject condition,
                                                      CallbackInfoReturnable<Boolean> callback) {
        try {
            Class.forName("top.theillusivec4.curios.api.CuriosApi");
        } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
            callback.setReturnValue(false);
        }
    }
}
