package github.com.gengyoubo.sscfe;

import github.com.gengyoubo.sscfe.client.SscfeClientConfig;
import github.com.gengyoubo.sscfe.init.ModForms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;

@Mod(Sscfe.MOD_ID)
public final class Sscfe {
    public static final String MOD_ID = "sscfe";

    @SuppressWarnings("removal")
    public Sscfe() {
        registerForms();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SscfeClientConfig.SPEC, "sscfe-client.toml");
    }

    private static void registerForms() {
        ModForms.init();
    }
}
