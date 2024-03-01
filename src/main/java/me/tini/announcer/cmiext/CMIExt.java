package me.tini.announcer.cmiext;

import me.tini.announcer.BanAnnouncerPlugin;
import me.tini.announcer.bukkit.BanAnnouncerBukkit;

public class CMIExt {

    public CMIExt(BanAnnouncerPlugin plugin) {
        BanAnnouncerBukkit p = (BanAnnouncerBukkit) plugin;

        CMIEvents e = new CMIEvents(p);

        p.getPunishmentListeners().addNew("CMI", "cmi", () -> e, false, "com.Zrips.CMI.CMI");
    }
}
