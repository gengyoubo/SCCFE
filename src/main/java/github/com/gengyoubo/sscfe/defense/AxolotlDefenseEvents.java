package github.com.gengyoubo.sscfe.defense;

import github.com.gengyoubo.sscfe.Sscfe;
import github.com.gengyoubo.sscfe.affinity.FormAffinityGoal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Makes nearby vanilla creatures defend a player using a matching form. */
@Mod.EventBusSubscriber(modid = Sscfe.MOD_ID)
public final class AxolotlDefenseEvents {
    private static final double DEFENSE_RADIUS = 20.0D;

    private AxolotlDefenseEvents() {
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || player.level().isClientSide
                || event.getAmount() <= 0.0F) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)
                || attacker instanceof Axolotl
                || attacker == player) {
            return;
        }

        for (Mob mob : player.level().getEntitiesOfClass(Mob.class,
                player.getBoundingBox().inflate(DEFENSE_RADIUS), candidate ->
                        candidate.isAlive() && !candidate.isNoAi()
                                && candidate != attacker
                                && FormAffinityGoal.matchesSpecies(player, candidate))) {
            if (mob instanceof Axolotl axolotl) {
                axolotl.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, attacker);
                axolotl.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                AxolotlAi.updateActivity(axolotl);
            } else {
                mob.setTarget(attacker);
            }
        }
    }
}
