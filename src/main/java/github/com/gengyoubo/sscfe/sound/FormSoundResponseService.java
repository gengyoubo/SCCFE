package github.com.gengyoubo.sscfe.sound;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.onixary.shapeShifterCurseForge.form.FormManager;

/** Makes nearby vanilla creatures answer a successful form call. */
public final class FormSoundResponseService {
    private static final double RESPONSE_RADIUS = 16.0D;
    private static final float RESPONSE_VOLUME = 1.0F;

    private FormSoundResponseService() {
    }

    public static void respond(ServerPlayer player) {
        Response response = responseFor(FormManager.current(player).id().getPath(), player.isShiftKeyDown());
        if (response == null) {
            return;
        }

        Level level = player.level();
        for (Mob mob : level.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(RESPONSE_RADIUS),
                nearby -> nearby.getType() == response.entityType && !nearby.isSilent())) {
            BlockPos position = mob.blockPosition();
            SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(response.sound);
            level.playSound(null, position, sound, mob.getSoundSource(), RESPONSE_VOLUME,
                    0.9F + level.random.nextFloat() * 0.2F);
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
}
