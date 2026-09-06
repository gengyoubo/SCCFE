package github.com.gengyoubo.sscfe.sound;

/** Central volume policy for data-driven form sounds. */
public final class FormSoundVolumePolicy {
    private static final float NORMAL_POWER_MULTIPLIER = 1.5F;
    private static final float PLAYER_CALL_MULTIPLIER = 10.0F;
    private static final ThreadLocal<Boolean> PLAYER_CALL = ThreadLocal.withInitial(() -> false);

    private FormSoundVolumePolicy() {
    }

    public static void beginPlayerCall() {
        PLAYER_CALL.set(true);
    }

    public static void endPlayerCall() {
        PLAYER_CALL.remove();
    }

    public static float scale(float volume) {
        float multiplier = Boolean.TRUE.equals(PLAYER_CALL.get())
                ? PLAYER_CALL_MULTIPLIER : NORMAL_POWER_MULTIPLIER;
        return volume * multiplier;
    }
}
