package github.com.gengyoubo.sscfe.form;

import github.com.gengyoubo.sscfe.Sscfe;
import net.minecraft.resources.ResourceLocation;
import net.onixary.shapeShifterCurseForge.api.registry.SscForm;
import net.onixary.shapeShifterCurseForge.form.FormBodyType;

/** Java registration for {@code sscfe:catgirl_0}. */
public final class Catgirl0Form extends SscForm {
    public Catgirl0Form() {
        super(SscForm.builder(id("catgirl_0"))
                .group(id("catgirl_form"))
                .tier(1)
                .weight(1)
                .bodyType(FormBodyType.NORMAL)
                .widthScale(1.0F)
                .heightScale(1.0F)
                .eyeScale(1.0F)
                .addFlags("special_form"));
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(Sscfe.MOD_ID, path);
    }
}
