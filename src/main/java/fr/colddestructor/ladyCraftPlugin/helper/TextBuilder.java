package fr.colddestructor.ladyCraftPlugin.helper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public final class TextBuilder {

    public static final TextColor GREEN = TextColor.color(0x55FF55);
    public static final TextColor PINK = TextColor.color(0xFF55FF);
    public static final TextColor ORANGE = TextColor.color(0xFFAA00);
    public static final TextColor WHITE = TextColor.color(0xFFFFFF);

    private static final String BORDER = "━━━━━━━━━━━━━━━━━━━━";

    private Component component = Component.empty();

    private TextBuilder() {}

    public static TextBuilder create() {
        return new TextBuilder();
    }

    public TextBuilder text(String text, TextColor color) {
        component = component.append(Component.text(text, color));
        return this;
    }

    public TextBuilder bold(String text, TextColor color) {
        component = component.append(
                Component.text(text, color)
                        .decorate(TextDecoration.BOLD)
        );
        return this;
    }

    public TextBuilder append(Component other) {
        component = component.append(other);
        return this;
    }

    public Component build() {
        return component;
    }

    public TextBuilder newLine() {
        component = component.appendNewline();
        return this;
    }

    public static Component header() {
        return TextBuilder.create()
                .text("┏", GREEN)
                .text(BORDER, GREEN)
                .text("[", WHITE)
                .bold("LadyCraftPlugin", PINK)
                .text("]", WHITE)
                .text(BORDER, GREEN)
                .text("┓", GREEN)
                .build();
    }

    public static Component footer() {
        return TextBuilder.create()
                .newLine()
                .text("┗", GREEN)
                .text(BORDER, GREEN)
                .append(pluginName())
                .text(BORDER, GREEN)
                .text("┛", GREEN)
                .build();
    }

    public static Component pluginName() {
        return TextBuilder.create()
                .text("[", WHITE)
                .bold("LadyCraftPlugin", PINK)
                .text("]", WHITE)
                .build();
    }
}