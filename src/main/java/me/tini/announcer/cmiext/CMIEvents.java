package me.tini.announcer.cmiext;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.Modules.Warnings.CMIPlayerWarning;
import com.Zrips.CMI.events.CMIAfkKickEvent;
import com.Zrips.CMI.events.CMIIpBanEvent;
import com.Zrips.CMI.events.CMIIpUnBanEvent;
import com.Zrips.CMI.events.CMIPlayerBanEvent;
import com.Zrips.CMI.events.CMIPlayerJailEvent;
import com.Zrips.CMI.events.CMIPlayerKickEvent;
import com.Zrips.CMI.events.CMIPlayerUnBanEvent;
import com.Zrips.CMI.events.CMIPlayerUnjailEvent;
import com.Zrips.CMI.events.CMIPlayerWarnEvent;

import me.tini.announcer.BanAnnouncerPlugin;
import me.tini.announcer.PunishmentAction;
import me.tini.announcer.PunishmentAction.Type;
import me.tini.announcer.bukkit.BanAnnouncerBukkit;
import me.tini.announcer.bukkit.BukkitPunishmentListener;

public class CMIEvents extends BukkitPunishmentListener {

    private CMI cmi;

    public CMIEvents(BanAnnouncerPlugin plugin) {
        super((BanAnnouncerBukkit) plugin);

        cmi = CMI.getInstance();
    }

    @EventHandler
    public void onPlayerBan(CMIPlayerBanEvent event) {
        final UUID bannedId = event.getBanned();
        final CMIUser user = cmi.getPlayerManager().getUser(bannedId);
        final CommandSender staff = event.getBannedBy();
        final String reason = event.getReason();
        final Long until = event.getUntil();
        final boolean isPermanent = until == null;

        PunishmentAction p = new PunishmentAction();
        if (isPermanent) {
            p.setType(Type.BAN);
            p.setPermanent(true);
        } else {
            p.setType(Type.TEMPBAN);
            p.setStart(System.currentTimeMillis());
            p.setEnd(until);
        }

        if (staff instanceof Player) {
            p.setOperator(staff.getName());
        } else {
            p.setOperator("Console");
        }

        p.setPlayerId(bannedId.toString());
        p.setPlayer(user.getName());

        p.setReason(reason);

        handlePunishment(p);
    }

    @EventHandler
    public void onPlayerUnBan(CMIPlayerUnBanEvent event) {
        final Player unbanned = event.getPlayer();
        final CommandSender staff = event.getBannedBy();

        PunishmentAction p = new PunishmentAction();
        p.setType(Type.UNBAN);

        if (staff instanceof Player) {
            p.setOperator(staff.getName());
        } else {
            p.setOperator("Console");
        }

        p.setPlayerId(unbanned.getUniqueId().toString());
        p.setPlayer(unbanned.getName());

        handlePunishment(p);
    }

    @EventHandler
    public void onIpBan(CMIIpBanEvent event) {
        final String bannedIp = event.getIp();
        final CommandSender staff = event.getBannedBy();
        final String reason = event.getReason();
        final Long until = event.getUntil();
        final boolean isPermanent = until == null;

        PunishmentAction p = new PunishmentAction();
        if (isPermanent) {
            p.setType(Type.BANIP);
            p.setPermanent(true);
        } else {
            p.setType(Type.TEMPBANIP);
            p.setStart(System.currentTimeMillis());
            p.setEnd(until);
        }

        if (staff instanceof Player) {
            p.setOperator(staff.getName());
        } else {
            p.setOperator("Console");
        }

        p.setPlayer(bannedIp);

        p.setReason(reason);

        handlePunishment(p);
    }

    @EventHandler
    public void onIpUnBan(CMIIpUnBanEvent event) {
        final String unbanned = event.getIp();
        final CommandSender staff = event.getBannedBy();

        PunishmentAction p = new PunishmentAction();
        p.setType(Type.UNBANIP);

        if (staff instanceof Player) {
            p.setOperator(staff.getName());
        } else {
            p.setOperator("Console");
        }

        p.setPlayer(unbanned);

        handlePunishment(p);
    }

    @EventHandler
    public void onPlayerKick(CMIPlayerKickEvent event) {
        final UUID kickedId = event.getBanned();
        final CMIUser user = cmi.getPlayerManager().getUser(kickedId);
        final CommandSender staff = event.getBannedBy();
        final String reason = event.getReason();

        PunishmentAction p = new PunishmentAction();
        p.setType(Type.KICK);

        if (staff instanceof Player) {
            p.setOperator(staff.getName());
        } else {
            p.setOperator("Console");
        }

        p.setPlayerId(kickedId.toString());
        p.setPlayer(user.getName());

        p.setReason(reason);

        handlePunishment(p);
    }

    @EventHandler
    public void onAfkKick(CMIAfkKickEvent event) {
        final Player kicked = event.getPlayer();

        PunishmentAction p = new PunishmentAction();
        p.setType(Type.KICK);

        p.setPlayerId(kicked.getUniqueId().toString());
        p.setPlayer(kicked.getName());

        p.setOperator("AFK-Kick");
        p.setReason("AFK-Kick");

        handlePunishment(p);
    }

    @EventHandler
    public void onPlayerWarn(CMIPlayerWarnEvent event) {
        final CMIUser user = event.getUser();
        final CMIPlayerWarning warning = event.getWarning();
        final String staff = warning.getGivenBy();
        final String reason = warning.getReason();

        PunishmentAction p = new PunishmentAction();
        p.setType(Type.WARN);

        p.setOperator(staff);
        p.setReason(reason);

        p.setPlayerId(user.getUniqueId().toString());
        p.setPlayer(user.getName());

        handlePunishment(p);
    }

    @EventHandler
    public void onPlayerJail(CMIPlayerJailEvent event) {
        final CMIUser user = event.getUser();
        final String jail = event.getCell().getJail().getName();
        final String reason = user.getJailedReason();

        PunishmentAction p = new PunishmentAction();
        p.setType(Type.JAIL);

        p.setPlayerId(user.getUniqueId().toString());
        p.setPlayer(user.getName());

        p.setJail(jail);
        p.setReason(reason);

        p.setOperator("?");

        handlePunishment(p);
    }

    @EventHandler
    public void onPlayerUnjail(CMIPlayerUnjailEvent event) {
        final CMIUser user = event.getUser();
        final String jail = event.getCell().getJail().getName();

        PunishmentAction p = new PunishmentAction();
        p.setType(Type.UNJAIL);

        p.setPlayerId(user.getUniqueId().toString());
        p.setPlayer(user.getName());

        p.setJail(jail);

        p.setOperator("?");
        p.setReason("?");

        handlePunishment(p);
    }
}
