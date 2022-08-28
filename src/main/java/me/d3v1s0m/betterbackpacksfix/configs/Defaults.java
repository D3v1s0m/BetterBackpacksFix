package me.d3v1s0m.betterbackpacksfix.configs;

import java.util.Arrays;
import java.util.List;

public enum Defaults {
    DISABLE_BACKPACKS("disable-backpacks", false, "If true, backpacks are disabled."),
    CLOSE_INV_ON_TRY("close-inv-on-try", false, "", "Closes the shulker when trying to put a backpack in it."),
    PERMISSIONS("permissions", "", ""),
    RELOAD("permissions.reload", "betterbackpacksfix.reload", "", "The permission required to use reload command."),
    DISABLE("permissions.disable", "betterbackpacksfix.disable", "", "The permission required to use disable backpacks command."),
    ENABLE("permissions.enable", "betterbackpacksfix.enable", "", "The permission required to use enable backpacks command."),
    BYPASS_DISABLED("permissions.bypassdisabled", "betterbackpacksfix.bypassdisabled", "", "The permission required to open backpacks even when disabled."),
    ALIASES("aliases", Arrays.asList("betterbackpackfix", "bbackpacksfix", "bbackpackfix", "bbpf"), "", "The aliases for the main command.", "Note: A restart is required for the aliases to take effect.");

    private final String path;

    private final Object value;

    private final List<String> comments;

    Defaults(String path, Object value, String... comments) {
        this.path = path;
        this.value = value;
        this.comments = Arrays.asList(comments);
    }

    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }

    public List<String> getComments() {
        return comments;
    }

    public static void setupValues() {
        for (Defaults def : Defaults.values()) {
            Configuration.get().addDefault(def.getPath(), def.getValue());
        }
    }

    public static void setupComments() {
        for (Defaults def : Defaults.values()) {
            Configuration.get().setComments(def.getPath(), def.getComments());
        }
    }
}
