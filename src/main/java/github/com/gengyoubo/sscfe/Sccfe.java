package github.com.gengyoubo.sscfe;

import github.com.gengyoubo.sscfe.client.SccfeClientConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;

@Mod(Sccfe.MOD_ID)
public final class Sccfe {
    public static final String MOD_ID = "sccfe";

    @SuppressWarnings("removal")
    public Sccfe() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SccfeClientConfig.SPEC, "sccfe-client.toml");
    }
}
