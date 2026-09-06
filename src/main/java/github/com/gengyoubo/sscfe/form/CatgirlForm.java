package github.com.gengyoubo.sscfe.form;

import github.com.gengyoubo.sscfe.Sscfe;
import github.com.gengyoubo.sscfe.init.ModForms;
import net.minecraft.resources.ResourceLocation;
import net.onixary.shapeShifterCurseForge.api.registry.FormProperties;
import net.onixary.shapeShifterCurseForge.api.registry.SscForm;
import net.onixary.shapeShifterCurseForge.api.registry.SscRegistryObject;
import net.onixary.shapeShifterCurseForge.form.FormBodyType;

/** The standalone first-stage Catgirl form. SSC derives {@code sscfe:catgirl_0} from {@code catgirl}. */
public final class CatgirlForm extends SscForm {
    public static final SscRegistryObject<CatgirlForm> TYPE = ModForms.register(CatgirlForm::new);

    /**
     * Declares this Form in {@link ModForms}. Calling this method triggers this class's static
     * {@link #TYPE} declaration; the shared registrar is committed later by {@link ModForms}.
     */
    public static void init() {
        // Class initialization performs the deferred declaration before this method body runs.
    }

    public CatgirlForm() {
        super(ResourceLocation.fromNamespaceAndPath(Sscfe.MOD_ID, "catgirl"));
    }

    @Override
    protected int stage() {
        return 1;
    }

    @Override
    protected void configure(FormProperties properties) {
        properties.weight(1)
                .bodyType(FormBodyType.NORMAL)
                .scale(1.0F, 1.0F, 1.0F)
                .specialForm();
    }
}
