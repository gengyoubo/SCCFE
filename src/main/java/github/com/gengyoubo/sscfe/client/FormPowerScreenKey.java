package github.com.gengyoubo.sscfe.client;

import com.mojang.blaze3d.platform.InputConstants;
import github.com.gengyoubo.sscfe.Sscfe;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Sscfe.MOD_ID, value = Dist.CLIENT)
public final class FormPowerScreenKey {
    private static final KeyMapping OPEN_FORM_POWERS = new KeyMapping(
            "key.sscfe.form_powers",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "key.categories.sscfe");

    private FormPowerScreenKey() {
    }

    @Mod.EventBusSubscriber(modid = Sscfe.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Registration {
        private Registration() {
        }

        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(OPEN_FORM_POWERS);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        while (OPEN_FORM_POWERS.consumeClick()) {
            if (minecraft.screen instanceof FormPowerScreen) {
                minecraft.setScreen(null);
            } else if (minecraft.player != null && minecraft.level != null && minecraft.screen == null) {
                minecraft.setScreen(new FormPowerScreen(minecraft.player));
            }
        }
    }
}
