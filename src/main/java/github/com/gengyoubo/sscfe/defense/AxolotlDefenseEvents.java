package github.com.gengyoubo.sscfe.defense;

import github.com.gengyoubo.sscfe.Sscfe;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.onixary.shapeShifterCurseForge.form.FormManager;

/** Makes nearby vanilla axolotls defend a player using an axolotl form. */
@Mod.EventBusSubscriber(modid = Sscfe.MOD_ID)
public final class AxolotlDefenseEvents {
    private static final double DEFENSE_RADIUS = 20.0D;

    private AxolotlDefenseEvents() {
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || player.level().isClientSide
                || event.getAmount() <= 0.0F
                || !isAxolotlForm(player)) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)
                || attacker instanceof Axolotl
                || attacker == player) {
            return;
        }

        for (Axolotl axolotl : player.level().getEntitiesOfClass(Axolotl.class,
                player.getBoundingBox().inflate(DEFENSE_RADIUS), candidate ->
                        candidate.isAlive() && !candidate.isNoAi())) {
            axolotl.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, attacker);
            axolotl.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            AxolotlAi.updateActivity(axolotl);
        }
    }

    private static boolean isAxolotlForm(Player player) {
        return "axolotl_form".equals(FormManager.current(player).groupId().getPath());
    }
}
