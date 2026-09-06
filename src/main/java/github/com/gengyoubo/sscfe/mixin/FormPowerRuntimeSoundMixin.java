package github.com.gengyoubo.sscfe.mixin;

import github.com.gengyoubo.sscfe.sound.FormSoundVolumePolicy;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.onixary.shapeShifterCurseForge.power.FormPowerRuntime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/** Applies the SSCFE sound-volume policy to all data-driven play_sound actions. */
@Mixin(FormPowerRuntime.class)
public abstract class FormPowerRuntimeSoundMixin {
    @ModifyArg(
            method = "playSound",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;"
                            + "Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;"
                            + "Lnet/minecraft/sounds/SoundSource;FF)V",
                    remap = false
            ),
            index = 4,
            remap = false
    )
    private static float sscfe$increasePowerSoundVolume(float volume) {
        return FormSoundVolumePolicy.scale(volume);
    }
}
