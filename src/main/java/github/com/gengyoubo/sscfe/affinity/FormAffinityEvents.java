package github.com.gengyoubo.sscfe.affinity;

import github.com.gengyoubo.sscfe.Sccfe;
import github.com.gengyoubo.sscfe.mixin.MobGoalSelectorAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Sccfe.MOD_ID)
public final class FormAffinityEvents {
    private FormAffinityEvents() {
    }

    @SubscribeEvent
    public static void entityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof Mob mob)
                || !FormAffinityGoal.supports(mob)) {
            return;
        }
        ((MobGoalSelectorAccessor) mob).sccfe$getGoalSelector().addGoal(6, new FormAffinityGoal(mob));
    }

    @SubscribeEvent
    public static void changeTarget(LivingChangeTargetEvent event) {
        if (event.getNewTarget() instanceof Player player && event.getEntity() instanceof Mob mob
                && FormAffinityGoal.matchesSpecies(player, mob)) {
            event.setCanceled(true);
        }
    }
}
