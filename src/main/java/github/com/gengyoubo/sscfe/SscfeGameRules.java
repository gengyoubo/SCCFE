package github.com.gengyoubo.sscfe;

import net.minecraft.world.level.GameRules;

/** Optional SCCFE rule; the core mod always preserves the current form. */
public final class SscfeGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> KEEP_FORM_AFTER_DEATH =
            GameRules.register("sscKeepFormAfterDeath", GameRules.Category.PLAYER,
                    GameRules.BooleanValue.create(true));

    private SscfeGameRules() { }

    public static void initialize() { }
}
