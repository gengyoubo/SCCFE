package github.com.gengyoubo.sscfe.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.onixary.shapeShifterCurseForge.form.FormManager;

import github.com.gengyoubo.sscfe.Sscfe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Makes nearby vanilla creatures answer a successful form call. */
@Mod.EventBusSubscriber(modid = Sscfe.MOD_ID)
public final class FormSoundResponseService {
    private static final double RESPONSE_RADIUS = 24.0D;
    private static final int RESPONSE_COOLDOWN_TICKS = 10;
    private static final int MIN_RESPONSE_DELAY_TICKS = 2;
    private static final int MAX_RESPONSE_DELAY_TICKS = 6;
    private static final int MIN_LOOK_DURATION_TICKS = 20;
    private static final int MAX_LOOK_DURATION_TICKS = 40;
    private static final Map<UUID, Long> RESPONSE_COOLDOWNS = new HashMap<>();
    private static final List<PendingResponse> PENDING_RESPONSES = new ArrayList<>();
    private static final List<LookAtTask> LOOK_AT_TASKS = new ArrayList<>();

    private FormSoundResponseService() {
    }

    public static void respond(ServerPlayer player) {
        Response response = responseFor(FormManager.current(player).id().getPath(), player.isShiftKeyDown());
        if (response == null) {
            return;
        }

        Level level = player.level();
        long now = level.getGameTime();
        long cooldownUntil = RESPONSE_COOLDOWNS.getOrDefault(player.getUUID(), Long.MIN_VALUE);
        if (now < cooldownUntil) {
            return;
        }
        RESPONSE_COOLDOWNS.put(player.getUUID(), now + RESPONSE_COOLDOWN_TICKS);

        for (Mob mob : level.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(RESPONSE_RADIUS),
                nearby -> nearby.getType() == response.entityType && !nearby.isSilent())) {
            int delay = MIN_RESPONSE_DELAY_TICKS
                    + level.random.nextInt(MAX_RESPONSE_DELAY_TICKS - MIN_RESPONSE_DELAY_TICKS + 1);
            PENDING_RESPONSES.add(new PendingResponse(mob, player, response.sound, now + delay));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Iterator<PendingResponse> pendingIterator = PENDING_RESPONSES.iterator();
        while (pendingIterator.hasNext()) {
            PendingResponse pending = pendingIterator.next();
            if (pending.level().getGameTime() < pending.dueAt) {
                continue;
            }
            if (pending.mob.isAlive() && !pending.mob.isSilent()
                    && pending.player.isAlive() && pending.mob.level() == pending.player.level()) {
                SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(pending.sound);
                pending.level().playSound(null, pending.mob.blockPosition(), sound,
                        pending.mob.getSoundSource(), 1.2F + pending.level().random.nextFloat() * 0.2F,
                        0.9F + pending.level().random.nextFloat() * 0.2F);
                pending.mob.getLookControl().setLookAt(pending.player, 30.0F, 30.0F);
                LOOK_AT_TASKS.add(new LookAtTask(pending.mob, pending.player,
                        pending.level().getGameTime() + MIN_LOOK_DURATION_TICKS
                                + pending.level().random.nextInt(MAX_LOOK_DURATION_TICKS - MIN_LOOK_DURATION_TICKS + 1)));
            }
            pendingIterator.remove();
        }

        Iterator<LookAtTask> lookIterator = LOOK_AT_TASKS.iterator();
        while (lookIterator.hasNext()) {
            LookAtTask task = lookIterator.next();
            if (task.level().getGameTime() >= task.until
                    || !task.mob.isAlive() || !task.player.isAlive()
                    || task.mob.level() != task.player.level()) {
                lookIterator.remove();
                continue;
            }
            task.mob.getLookControl().setLookAt(task.player, 30.0F, 30.0F);
        }
    }

    private static Response responseFor(String formId, boolean sneaking) {
        if (formId.startsWith("form_axolotl_")) {
            return response(EntityType.AXOLOTL, "entity.axolotl.idle_air");
        }
        if (formId.startsWith("form_spider_")) {
            return response(EntityType.SPIDER, "entity.spider.ambient");
        }
        if (formId.startsWith("form_bat_")) {
            return response(EntityType.BAT, "entity.bat.ambient");
        }
        if (formId.startsWith("form_familiar_fox_") || formId.startsWith("form_snow_fox_")) {
            return response(EntityType.FOX, "entity.fox.ambient");
        }
        if (formId.startsWith("form_feral_cat_sp") || formId.startsWith("form_ocelot_")) {
            return response(EntityType.OCELOT, sneaking ? "entity.cat.hiss" : "entity.cat.ambient");
        }
        if (formId.startsWith("form_anubis_wolf_")) {
            return response(EntityType.WOLF, sneaking ? "entity.wolf.growl" : "entity.wolf.ambient");
        }
        if ("form_allay_sp".equals(formId)) {
            return response(EntityType.ALLAY, "entity.allay.ambient_without_item");
        }
        return null;
    }

    private static Response response(EntityType<? extends Mob> entityType, String soundId) {
        return new Response(entityType, ResourceLocation.fromNamespaceAndPath("minecraft", soundId));
    }

    private record Response(EntityType<? extends Mob> entityType, ResourceLocation sound) {
    }

    private record PendingResponse(Mob mob, ServerPlayer player, ResourceLocation sound, long dueAt) {
        private Level level() {
            return mob.level();
        }
    }

    private record LookAtTask(Mob mob, ServerPlayer player, long until) {
        private Level level() {
            return mob.level();
        }
    }
}
