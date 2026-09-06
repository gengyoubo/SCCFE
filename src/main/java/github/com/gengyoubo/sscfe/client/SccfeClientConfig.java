package github.com.gengyoubo.sscfe.client;

import net.minecraftforge.common.ForgeConfigSpec;

public final class SccfeClientConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue PREFER_NEW_ANIMATIONS = BUILDER
            .comment("Use SCCFE's player_animation/new resources when present.",
                    "When disabled, Shape Shifter Curse uses its bundled legacy animations.")
            .define("animations.prefer_new_animations", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private SccfeClientConfig() {
    }
}
