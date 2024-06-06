package me.tini.announcer.cmiext;

import me.tini.announcer.BanAnnouncerPlugin;
import me.tini.announcer.PunishmentListener;
import me.tini.announcer.extension.AbstractExtension;
import me.tini.announcer.plugin.bukkit.BanAnnouncerBukkit;

public class CMIExtension extends AbstractExtension {

    private CMIEventListener listener;

    public CMIExtension(BanAnnouncerPlugin plugin) {
        this.listener = new CMIEventListener((BanAnnouncerBukkit) plugin);
    }

    @Override
    public PunishmentListener getPunishmentListener() {
        return listener;
    }
}
