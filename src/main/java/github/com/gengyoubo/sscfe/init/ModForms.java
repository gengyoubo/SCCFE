package github.com.gengyoubo.sscfe.init;

import github.com.gengyoubo.sscfe.Sscfe;
import github.com.gengyoubo.sscfe.form.CatgirlForm;
import net.onixary.shapeShifterCurseForge.api.registry.SscForm;
import net.onixary.shapeShifterCurseForge.api.registry.SscJavaRegistries;
import net.onixary.shapeShifterCurseForge.api.registry.SscRegistrar;
import net.onixary.shapeShifterCurseForge.api.registry.SscRegistryObject;

import java.util.function.Supplier;

/**
 * SCCFE's central deferred Form registration branch.
 *
 * <p>Each Form owns its declaration through its own {@code init()} method. This class gathers
 * those declarations and commits the shared {@code sscfe} registrar from the mod entry point.</p>
 */
public final class ModForms {
    private static final SscRegistrar REGISTRAR = SscJavaRegistries.registrar(Sscfe.MOD_ID);

    private ModForms() {
    }

    /** Declares one SCCFE Form without immediately committing it to SSC's global registry. */
    public static <T extends SscForm> SscRegistryObject<T> register(Supplier<T> factory) {
        return REGISTRAR.form(factory);
    }

    /**
     * Declares every SCCFE Form, then commits the shared deferred registry exactly once.
     * Add future Form classes to this list; individual Form classes own their declarations.
     */
    public static void init() {
        CatgirlForm.init();
        REGISTRAR.init();
    }
}
