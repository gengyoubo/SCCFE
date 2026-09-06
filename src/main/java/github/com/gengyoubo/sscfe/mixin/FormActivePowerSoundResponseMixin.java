package github.com.gengyoubo.sscfe.mixin;

import github.com.gengyoubo.sscfe.sound.FormSoundResponseService;
import github.com.gengyoubo.sscfe.sound.FormSoundVolumePolicy;
import net.minecraft.server.level.ServerPlayer;
import net.onixary.shapeShifterCurseForge.power.FormActivePowerService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Connects the make_sound key to nearby vanilla creature responses. */
@Mixin(FormActivePowerService.class)
public abstract class FormActivePowerSoundResponseMixin {
    @Inject(method = "triggerHiss", at = @At("HEAD"), remap = false)
    private static void sscfe$beginHissCall(ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        FormSoundVolumePolicy.beginPlayerCall();
    }

    @Inject(method = "triggerHiss", at = @At("TAIL"), remap = false)
    private static void sscfe$respondToHiss(ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            FormSoundResponseService.respond(player);
        }
        FormSoundVolumePolicy.endPlayerCall();
    }

    @Inject(method = "triggerActive", at = @At("HEAD"), remap = false)
    private static void sscfe$beginSoundCall(ServerPlayer player, String key,
                                              CallbackInfoReturnable<Boolean> cir) {
        if ("key.shape-shifter-curse.make_sound".equals(key)) {
            FormSoundVolumePolicy.beginPlayerCall();
        }
    }

    @Inject(method = "triggerActive", at = @At("TAIL"), remap = false)
    private static void sscfe$respondToMakeSound(ServerPlayer player, String key,
                                                  CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && "key.shape-shifter-curse.make_sound".equals(key)) {
            FormSoundResponseService.respond(player);
        }
        if ("key.shape-shifter-curse.make_sound".equals(key)) {
            FormSoundVolumePolicy.endPlayerCall();
        }
    }
}
