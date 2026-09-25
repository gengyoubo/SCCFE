package github.com.gengyoubo.sscfe.form;

import github.com.gengyoubo.sscfe.Sscfe;
import github.com.gengyoubo.sscfe.SscfeGameRules;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.onixary.shapeShifterCurseForge.api.SscApi;
import net.onixary.shapeShifterCurseForge.form.FormRegistry;

@Mod.EventBusSubscriber(modid = Sscfe.MOD_ID)
public final class KeepFormAfterDeathEvents {
    private KeepFormAfterDeathEvents() { }

    /** Run after SCCF copies the old player's data into the respawned player. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void clonePlayer(PlayerEvent.Clone event) {
        if (!event.isWasDeath()
                || event.getEntity().level().getGameRules().getBoolean(SscfeGameRules.KEEP_FORM_AFTER_DEATH)) {
            return;
        }
        SscApi.currentForm(event.getEntity()).ifPresent(data -> {
            data.setFormId(FormRegistry.ORIGINAL_BEFORE_ENABLE.toString());
            data.setPreviousFormId(FormRegistry.ORIGINAL_BEFORE_ENABLE.toString());
            data.setFormGroupId(FormRegistry.get(FormRegistry.ORIGINAL_BEFORE_ENABLE).groupId().toString());
            data.setFormTier(-1);
            data.setContentEnabled(false);
        });
    }
}
