package net.kosmo.music.mixin;

import net.kosmo.music.ClientMusic;
import net.kosmo.music.gui.PlaySoundScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen extends Screen {
    @Unique
    private final Text TITLE_SCREEN_BUTTON = Text.translatable("gui.musicnotification.playsound.title_screen");

    protected MixinTitleScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        if (ClientMusic.config.SHOW_TITLE_SCREEN_BUTTON) {
            this.addDrawableChild(ButtonWidget.builder(TITLE_SCREEN_BUTTON, (button) -> {
                assert this.client != null;
                this.client.setScreen(new PlaySoundScreen());
            }).dimensions(10, 10, this.textRenderer.getWidth(TITLE_SCREEN_BUTTON), 20).build());
        }
    }
}