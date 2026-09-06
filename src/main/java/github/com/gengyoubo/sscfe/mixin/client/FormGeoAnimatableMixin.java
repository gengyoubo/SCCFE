package github.com.gengyoubo.sscfe.mixin.client;

import github.com.gengyoubo.sscfe.client.SscfeClientConfig;
import github.com.gengyoubo.sscfe.client.SurfaceSprintOverlayAccess;
import github.com.gengyoubo.sscfe.client.SurfaceSprintOverlayTimeline;
import net.minecraft.world.entity.player.Player;
import net.onixary.shapeShifterCurseForge.client.render.FormGeoAnimatable;
import net.onixary.shapeShifterCurseForge.form.FormManager;
import net.onixary.shapeShifterCurseForge.power.CrawlingScaleService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(FormGeoAnimatable.class)
public abstract class FormGeoAnimatableMixin implements SurfaceSprintOverlayAccess {
    @Unique
    private final Map<UUID, SurfaceSprintOverlayTimeline> sscfe$overlayTimelines = new HashMap<>();

    @Override
    public float sscfe$surfaceSprintOverlayTime(float partialTick) {
        FormGeoAnimatable self = (FormGeoAnimatable) (Object) this;
        Player player = self.getPlayer();
        if (player == null || self.isInventoryPreview() || !SscfeClientConfig.PREFER_NEW_ANIMATIONS.get()) {
            return -1.0F;
        }

        SurfaceSprintOverlayTimeline overlay = sscfe$overlayTimelines.computeIfAbsent(
                player.getUUID(), ignored -> new SurfaceSprintOverlayTimeline());
        boolean crawling = "axolotl_3".equals(FormManager.current(player).id().getPath())
                && (player.isShiftKeyDown() || CrawlingScaleService.isForcedCrawling(player))
                && !player.isInWater() && !player.isFallFlying();
        boolean surfaceSprinting = "axolotl_3".equals(FormManager.current(player).id().getPath())
                && player.isSprinting() && player.isInWater() && !player.isFallFlying();
        if (!(crawling || surfaceSprinting)) {
            overlay.active = false;
            return -1.0F;
        }

        double now = player.tickCount + partialTick;
        if (!overlay.active) {
            overlay.active = true;
            overlay.startedAt = now;
        }
        return (float) ((now - overlay.startedAt) / 20.0D);
    }
}
