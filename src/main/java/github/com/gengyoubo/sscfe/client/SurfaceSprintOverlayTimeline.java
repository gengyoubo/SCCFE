package github.com.gengyoubo.sscfe.client;

/**
 * Per-player clock for the additive surface-sprint animation.
 *
 * This is deliberately a normal extension class rather than a nested Mixin
 * class. Mixin-owned packages must not be referenced by transformed classes.
 */
public final class SurfaceSprintOverlayTimeline {
    public boolean active;
    public double startedAt;
}
