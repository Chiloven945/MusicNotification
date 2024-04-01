package net.kosmo.music.gui;

import com.google.common.collect.Lists;
import net.kosmo.music.ClientMusic;
import net.kosmo.music.utils.resource.MusicManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SoundListWidget extends ElementListWidget<SoundListEntry> {
    private final List<SoundListEntry> entries = Lists.newArrayList();
    @Nullable
    private String currentSearch;

    public SoundListWidget(MinecraftClient minecraftClient, int width, int height, int y, int itemHeight) {
        super(minecraftClient, width, height, y, itemHeight);
        this.setRenderBackground(false);
    }

    public void update(List<MusicManager.Music> entries) {
        HashMap<Identifier, SoundListEntry> map = new HashMap<>();
        for (MusicManager.Music music : entries) {
            ClientMusic.LOGGER.info(String.valueOf(music.identifier));
            map.put(music.identifier, new SoundListEntry(this.client, music));
        }
        this.refresh(map.values(), this.getScrollAmount());
    }

    private void refresh(Collection<SoundListEntry> values, double scrollAmount) {
        this.entries.clear();
        this.entries.addAll(values);
        this.filterSounds();
        this.replaceEntries(this.entries);
        this.setScrollAmount(scrollAmount);
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    public void setCurrentSearch(@Nullable String currentSearch) {
        this.currentSearch = currentSearch;
    }

    private void filterSounds() {
        if (this.currentSearch != null) {
            this.entries.removeIf(entry -> !entry.getIdentifier().toString().toLowerCase(Locale.ROOT).contains(this.currentSearch));
            this.replaceEntries(this.entries);
        }
    }
}