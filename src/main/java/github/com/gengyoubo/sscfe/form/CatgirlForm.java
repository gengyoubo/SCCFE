package github.com.gengyoubo.sscfe.form;

import github.com.gengyoubo.sscfe.Sscfe;
import github.com.gengyoubo.sscfe.init.ModForms;
import net.minecraft.resources.ResourceLocation;
import net.onixary.shapeShifterCurseForge.api.registry.FormProperties;
import net.onixary.shapeShifterCurseForge.api.registry.SscForm;
import net.onixary.shapeShifterCurseForge.api.registry.SscRegistryObject;
import net.onixary.shapeShifterCurseForge.form.FormBodyType;

/** Shared definition and registration entry point for the catgirl family's main stages. */
public final class CatgirlForm extends SscForm {
    private static final ResourceLocation FAMILY =
            ResourceLocation.fromNamespaceAndPath(Sscfe.MOD_ID, "catgirl");

    public static final SscRegistryObject<CatgirlForm> STAGE1 =
            ModForms.register(() -> new CatgirlForm(1));
    public static final SscRegistryObject<CatgirlForm> STAGE2 =
            ModForms.register(() -> new CatgirlForm(2));

    private final int stage;

    /**
     * Declares the catgirl main stages together. The shared registrar is committed later
     * by {@link ModForms} after all families have declared their stages.
     */
    public static void init() {
        // Class initialization performs the deferred declaration before this method body runs.
    }

    private CatgirlForm(int stage) {
        super(FAMILY);
        this.stage = stage;
    }

    @Override
    protected int stage() {
        return stage;
    }

    @Override
    protected void configure(FormProperties properties) {
        properties.weight(1)
                .bodyType(FormBodyType.NORMAL)
                .scale(1.0F, 1.0F, 1.0F);

        switch (stage) {
            case 1 -> properties.specialForm();
            case 2 -> {
                // Stage 2 currently uses the shared family configuration.
            }
        }
    }
}
