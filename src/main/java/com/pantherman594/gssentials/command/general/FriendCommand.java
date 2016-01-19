/*
 * BungeeEssentials: Full customization of a few necessary features for your server!
 * Copyright (C) 2015  David Shen (PantherMan594)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pantherman594.gssentials.command.general;

import com.pantherman594.gssentials.BungeeEssentials;
import com.pantherman594.gssentials.command.BECommand;
import com.pantherman594.gssentials.utils.Dictionary;
import com.pantherman594.gssentials.utils.Permissions;
import com.pantherman594.gssentials.utils.PlayerData;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by David on 12/05.
 *
 * @author David
 */
public class FriendCommand extends BECommand {

    public FriendCommand() {
        super("friend", Permissions.General.FRIEND);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            PlayerData playerData = BungeeEssentials.getInstance().getData(((ProxiedPlayer) sender).getUniqueId());
            if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("list"))) {
                sender.sendMessage(Dictionary.format(Dictionary.FRIEND_HEADER, "COUNT", String.valueOf(playerData.getFriends().size())));
                for (String uuid : playerData.getFriends()) {
                    String server = "Offline";
                    String name;
                    if (ProxyServer.getInstance().getPlayer(UUID.fromString(uuid)) != null) {
                        server = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid)).getServer().getInfo().getName();
                        name = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid)).getName();
                    } else {
                        name = (new PlayerData(uuid, null)).getName();
                    }
                    String msg = Dictionary.format(Dictionary.FRIEND_BODY, "NAME", name, "SERVER", server);
                    TextComponent finalMsg = new TextComponent(msg);
                    if (!server.equals("Offline")) {
                        finalMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + server));
                        finalMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to join your friend!").create()));
                    }
                    sender.sendMessage(finalMsg);
                }
                sender.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_HEADER, "COUNT", String.valueOf(playerData.getOutRequests().size())));
                for (String uuid : playerData.getOutRequests()) {
                    String name;
                    if (ProxyServer.getInstance().getPlayer(UUID.fromString(uuid)) != null) {
                        name = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid)).getName();
                    } else {
                        name = (new PlayerData(uuid, null)).getName();
                    }
                    sender.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_BODY, "NAME", name));
                }
                sender.sendMessage(Dictionary.format(Dictionary.INREQUESTS_HEADER, "COUNT", String.valueOf(playerData.getInRequests().size())));
                for (String uuid : playerData.getInRequests()) {
                    String name;
                    if (ProxyServer.getInstance().getPlayer(UUID.fromString(uuid)) != null) {
                        name = ProxyServer.getInstance().getPlayer(UUID.fromString(uuid)).getName();
                    } else {
                        name = (new PlayerData(uuid, null)).getName();
                    }
                    sender.sendMessage(Dictionary.format(Dictionary.INREQUESTS_BODY, "NAME", name));
                }
            } else if (args.length == 2 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove"))) {
                ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
                String uuid;
                PlayerData playerData2;
                if (p != null) {
                    uuid = p.getUniqueId().toString();
                    playerData2 = BungeeEssentials.getInstance().getData(p.getUniqueId());
                } else {
                    uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + args[1]).getBytes(StandardCharsets.UTF_8)).toString();
                    playerData2 = new PlayerData(uuid, null);
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (playerData.getFriends().contains(uuid)) {
                        sender.sendMessage(Dictionary.format(Dictionary.FRIEND_OLD, "NAME", args[1]));
                    } else {
                        if (p != null) {
                            playerData2 = BungeeEssentials.getInstance().getData(p.getUniqueId());
                            uuid = p.getUniqueId().toString();
                            if (playerData.getInRequests().contains(uuid)) {
                                playerData.getInRequests().remove(uuid);
                                playerData.getFriends().add(uuid);
                                playerData2.getOutRequests().remove(((ProxiedPlayer) sender).getUniqueId().toString());
                                playerData2.getFriends().add(((ProxiedPlayer) sender).getUniqueId().toString());
                                p.sendMessage(Dictionary.format(Dictionary.FRIEND_NEW, "NAME", sender.getName()));
                                sender.sendMessage(Dictionary.format(Dictionary.FRIEND_NEW, "NAME", p.getName()));
                            } else if (!playerData.getOutRequests().contains(uuid)) {
                                playerData.getOutRequests().add(uuid);
                                playerData2.getInRequests().add(((ProxiedPlayer) sender).getUniqueId().toString());
                                p.sendMessage(Dictionary.format(Dictionary.INREQUESTS_NEW, "NAME", sender.getName()));
                                sender.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_NEW, "NAME", p.getName()));
                            } else {
                                sender.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_OLD, "NAME", p.getName()));
                            }
                        } else {
                            sender.sendMessage(Dictionary.format(Dictionary.ERROR_PLAYER_OFFLINE));
                        }
                    }
                } else {
                    if (playerData.getFriends().contains(uuid)) {
                        playerData.getFriends().remove(uuid);
                        playerData2.getFriends().remove(((ProxiedPlayer) sender).getUniqueId().toString());
                        sender.sendMessage(Dictionary.format(Dictionary.FRIEND_REMOVE, "NAME", p.getName()));
                        if (p != null) {
                            p.sendMessage(Dictionary.format(Dictionary.FRIEND_REMOVE, "NAME", sender.getName()));
                        }
                    } else if (playerData.getOutRequests().contains(uuid)) {
                        playerData.getOutRequests().remove(uuid);
                        playerData2.getInRequests().remove(((ProxiedPlayer) sender).getUniqueId().toString());
                        sender.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_REMOVE, "NAME", p.getName()));
                        if (p != null) {
                            p.sendMessage(Dictionary.format(Dictionary.INREQUESTS_REMOVE, "NAME", sender.getName()));
                        }
                    } else if (playerData.getInRequests().contains(uuid)) {
                        playerData.getInRequests().remove(uuid);
                        playerData2.getOutRequests().remove(((ProxiedPlayer) sender).getUniqueId().toString());
                        sender.sendMessage(Dictionary.format(Dictionary.INREQUESTS_REMOVE, "NAME", p.getName()));
                        if (p != null) {
                            p.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_REMOVE, "NAME", sender.getName()));
                        }
                    }
                    if (p != null) {
                        BungeeEssentials.getInstance().setData(p.getUniqueId(), playerData2);
                    } else {
                        playerData2.save();
                    }
                }
            } else {
                sender.sendMessage(Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, "HELP", getName() + " [list|add <player>|remove <player>"));
            }
        } else {
            sender.sendMessage(Dictionary.colour("&cConsole does not have any friends."));
        }
    }
}
