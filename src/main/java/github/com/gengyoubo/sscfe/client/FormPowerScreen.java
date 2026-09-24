package github.com.gengyoubo.sscfe.client;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import net.onixary.shapeShifterCurseForge.form.FormDefinition;
import net.onixary.shapeShifterCurseForge.form.FormManager;
import net.onixary.shapeShifterCurseForge.power.FormPowerDefinition;
import net.onixary.shapeShifterCurseForge.power.FormPowerRegistry;

import java.util.ArrayList;
import java.util.List;

public final class FormPowerScreen extends Screen {
    private static final int PANEL_WIDTH = 360;
    private static final int PANEL_HEIGHT = 220;
    private static final int ROW_HEIGHT = 64;

    private final Player player;
    private final List<PowerEntry> activePowers = new ArrayList<>();
    private final List<PowerEntry> passivePowers = new ArrayList<>();
    private String formName;
    private int scrollOffset;

    public FormPowerScreen(Player player) {
        super(Component.translatable("screen.sscfe.form_powers.title"));
        this.player = player;
    }

    @Override
    protected void init() {
        super.init();
        activePowers.clear();
        passivePowers.clear();
        FormDefinition form = FormManager.current(player);
        formName = formName(form);

        for (ResourceLocation id : FormPowerRegistry.idsFor(player)) {
            FormPowerDefinition definition = FormPowerRegistry.get(id);
            if (definition == null) {
                continue;
            }
            PowerEntry entry = makeEntry(definition);
            if (isActive(definition)) {
                activePowers.add(entry);
            } else {
                passivePowers.add(entry);
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        int left = (width - PANEL_WIDTH) / 2;
        int top = (height - PANEL_HEIGHT) / 2;
        graphics.fill(left, top, left + PANEL_WIDTH, top + PANEL_HEIGHT, 0xE8101218);
        graphics.fill(left, top, left + PANEL_WIDTH, top + 1, 0xFFB99A63);
        graphics.drawCenteredString(font, formName, width / 2, top + 10, 0xFFFFFFFF);
        graphics.drawCenteredString(font, Component.translatable("screen.sscfe.form_powers.active"), left + 93, top + 31, 0xFFFFD37A);
        graphics.drawCenteredString(font, Component.translatable("screen.sscfe.form_powers.passive"), left + 267, top + 31, 0xFF9AD7C5);
        graphics.fill(left + 179, top + 29, left + 180, top + PANEL_HEIGHT - 12, 0x55333333);

        int listTop = top + 48;
        int listBottom = top + PANEL_HEIGHT - 20;
        int visibleRows = Math.max(1, (listBottom - listTop) / ROW_HEIGHT);
        drawEntries(graphics, activePowers, left + 12, listTop, listBottom, visibleRows, 0xFFFFD37A);
        drawEntries(graphics, passivePowers, left + 192, listTop, listBottom, visibleRows, 0xFF9AD7C5);
        graphics.drawCenteredString(font, Component.translatable("screen.sscfe.form_powers.close"), width / 2, top + PANEL_HEIGHT - 13, 0xFFAAAAAA);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void drawEntries(GuiGraphics graphics, List<PowerEntry> entries, int x, int top, int bottom, int visibleRows, int color) {
        if (entries.isEmpty()) {
            graphics.drawWordWrap(font, Component.translatable("screen.sscfe.form_powers.empty"), x, top + 3, 150, 0xFFAAAAAA);
            return;
        }
        int end = Math.min(entries.size(), scrollOffset + visibleRows);
        for (int i = scrollOffset; i < end; i++) {
            PowerEntry entry = entries.get(i);
            int y = top + (i - scrollOffset) * ROW_HEIGHT;
            if (entry.icon != ItemStack.EMPTY) {
                graphics.renderItem(entry.icon, x, y);
            }
            int textX = x + (entry.icon != ItemStack.EMPTY ? 22 : 0);
            int textWidth = 150 - (textX - x);
            List<net.minecraft.util.FormattedCharSequence> nameLines = font.split(entry.name, textWidth);
            int nameY = y + 1;
            for (var line : nameLines) {
                graphics.drawString(font, line, textX, nameY, color, false);
                nameY += font.lineHeight;
            }
            int descriptionY = nameY + 2;
            List<net.minecraft.util.FormattedCharSequence> descriptionLines = font.split(entry.description, textWidth);
            int maxDescriptionLines = Math.max(1, (ROW_HEIGHT - (descriptionY - y) - 3) / font.lineHeight);
            for (int lineIndex = 0; lineIndex < Math.min(descriptionLines.size(), maxDescriptionLines); lineIndex++) {
                graphics.drawString(font, descriptionLines.get(lineIndex), textX, descriptionY, 0xFFCCCCCC, false);
                descriptionY += font.lineHeight;
            }
            if (i + 1 < end) {
                graphics.fill(x, Math.min(y + ROW_HEIGHT - 2, bottom), x + 150, Math.min(y + ROW_HEIGHT - 1, bottom), 0x33333333);
            }
        }
        if (entries.size() > visibleRows) {
            graphics.drawString(font, (scrollOffset + 1) + "/" + entries.size(), x + 112, bottom + 2, 0xFF888888, false);
        }
    }

    private PowerEntry makeEntry(FormPowerDefinition definition) {
        ResourceLocation id = definition.id();
        String baseKey = "power." + id.getNamespace() + "." + id.getPath();
        Component name = I18n.exists(baseKey + ".name")
                ? Component.translatable(baseKey + ".name")
                : Component.literal(humanize(id.getPath()));
        Component description = I18n.exists(baseKey + ".description")
                ? Component.translatable(baseKey + ".description")
                : Component.translatable("screen.sscfe.form_powers.no_description");
        return new PowerEntry(name, description, icon(definition.data()));
    }

    private static ItemStack icon(JsonObject data) {
        if (!data.has("icon") || !data.get("icon").isJsonObject()) {
            return ItemStack.EMPTY;
        }
        JsonObject icon = data.getAsJsonObject("icon");
        if (!icon.has("item")) {
            return ItemStack.EMPTY;
        }
        ResourceLocation itemId = ResourceLocation.tryParse(icon.get("item").getAsString());
        if (itemId == null) {
            return ItemStack.EMPTY;
        }
        var item = ForgeRegistries.ITEMS.getValue(itemId);
        return item == null || item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
    }

    private static boolean isActive(FormPowerDefinition definition) {
        String type = definition.type().toString();
        JsonObject data = definition.data();
        return type.contains("active") || type.endsWith(":toggle") || data.has("key");
    }

    private static String formName(FormDefinition form) {
        String key = "form." + form.id().getNamespace() + "." + form.id().getPath() + ".name";
        if (I18n.exists(key)) {
            return I18n.get(key);
        }
        String codexKey = "codex.form." + form.id().getNamespace() + "." + form.id().getPath() + ".name";
        return I18n.exists(codexKey) ? I18n.get(codexKey) : humanize(form.id().getPath());
    }

    private static String humanize(String value) {
        String[] words = value.replace('_', ' ').split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (!result.isEmpty()) result.append(' ');
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return result.toString();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int visibleRows = Math.max(1, (PANEL_HEIGHT - 68) / ROW_HEIGHT);
        int maxOffset = Math.max(0, Math.max(activePowers.size(), passivePowers.size()) - visibleRows);
        scrollOffset = Mth.clamp(scrollOffset - (int) Math.signum(delta), 0, maxOffset);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (minecraft != null && minecraft.options.keyInventory.matches(keyCode, scanCode)
                || keyCode == 256) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private record PowerEntry(Component name, Component description, ItemStack icon) {
    }
}
