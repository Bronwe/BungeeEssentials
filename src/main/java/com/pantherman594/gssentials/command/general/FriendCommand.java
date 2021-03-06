/*
 * BungeeEssentials: Full customization of a few necessary features for your server!
 * Copyright (C) 2016 David Shen (PantherMan594)
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

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pantherman594.gssentials.Dictionary;
import com.pantherman594.gssentials.Permissions;
import com.pantherman594.gssentials.PlayerData;
import com.pantherman594.gssentials.command.BECommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by David on 12/05.
 *
 * @author David
 */
@SuppressWarnings("unused")
public class FriendCommand extends BECommand implements TabExecutor {
    public FriendCommand() {
        super("friend", Permissions.General.FRIEND);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            PlayerData playerData = PlayerData.getData(((ProxiedPlayer) sender).getUniqueId());

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

                    sender.sendMessage(Dictionary.format(Dictionary.FRIEND_BODY, "NAME", name, "SERVER", server));
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
            } else if (args.length == 2 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("deny"))) {
                ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
                String uuid;
                PlayerData playerData2;

                if (p == sender) {
                    sender.sendMessage(Dictionary.color("&cYou can't be friends with yourself!."));
                } else {

                    if (p != null) { // If the player is online, pull up the player's data.
                        uuid = p.getUniqueId().toString();
                        playerData2 = PlayerData.getData(p.getUniqueId());
                    } else { // If the player is offline, lookup the player's uuid and load it.
                        if (ProxyServer.getInstance().getConfig().isOnlineMode()) {
                            try {
                                BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + args[1]).openStream()));
                                uuid = (((JsonObject) new JsonParser().parse(in)).get("id")).toString().replaceAll("\"", "").replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
                                in.close();
                            } catch (Exception e) {
                                sender.sendMessage(Dictionary.format("&cError: Could not find player."));
                                return;
                            }
                        } else {
                            uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + args[1]).getBytes(StandardCharsets.UTF_8)).toString();
                        }
                        playerData2 = new PlayerData(uuid, null);
                    }

                    if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("accept")) {
                        if (playerData.getFriends().contains(uuid)) { // Tell player if they are already friends
                            sender.sendMessage(Dictionary.format(Dictionary.FRIEND_OLD, "NAME", args[1]));
                        } else {
                            if (p != null && !PlayerData.getData(p.getUniqueId()).isHidden()) { // Will only add if player is online.

                                // playerData2 = PlayerData.getData(p.getUniqueId());  // Doesn't seem to be necessary, already loaded earlier. Will remove if that's the case.
                                // uuid = p.getUniqueId().toString();

                                if (playerData.getInRequests().contains(uuid)) {
                                /*
                                 * If the command sender already has an incoming request from the target player, will treat as accepting the request.
                                 * Will add as a friend on both sides and remove the requests.
                                 */
                                    playerData.getInRequests().remove(uuid);
                                    playerData.getFriends().add(uuid);

                                    playerData2.getOutRequests().remove(((ProxiedPlayer) sender).getUniqueId().toString());
                                    playerData2.getFriends().add(((ProxiedPlayer) sender).getUniqueId().toString());

                                    p.sendMessage(Dictionary.format(Dictionary.FRIEND_NEW, "NAME", sender.getName()));
                                    sender.sendMessage(Dictionary.format(Dictionary.FRIEND_NEW, "NAME", p.getName()));

                                } else if (!playerData.getOutRequests().contains(uuid)) {
                                    // If not sender's outgoing requests already, will add. Will also add to target's incoming requests.
                                    playerData.getOutRequests().add(uuid);
                                    playerData2.getInRequests().add(((ProxiedPlayer) sender).getUniqueId().toString());

                                    p.sendMessage(Dictionary.format(Dictionary.INREQUESTS_NEW, "NAME", sender.getName()));
                                    sender.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_NEW, "NAME", p.getName()));
                                } else {
                                    // If none of the above true, means sender has already sent a request.
                                    sender.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_OLD, "NAME", p.getName()));
                                }
                            } else {
                                sender.sendMessage(Dictionary.format(Dictionary.ERROR_PLAYER_OFFLINE));
                            }
                        }
                    } else { // For denying/removing
                        if (playerData.getFriends().contains(uuid)) { // If they are currently friends, will remove from both.
                            playerData.getFriends().remove(uuid);
                            playerData2.getFriends().remove(((ProxiedPlayer) sender).getUniqueId().toString());

                            sender.sendMessage(Dictionary.format(Dictionary.FRIEND_REMOVE, "NAME", playerData2.getName()));
                            if (p != null) {
                                p.sendMessage(Dictionary.format(Dictionary.FRIEND_REMOVE, "NAME", sender.getName()));
                            }

                        } else if (playerData.getOutRequests().contains(uuid)) { // If sender has a pending outgoing request, will remove that and the target's incoming request.
                            playerData.getOutRequests().remove(uuid);
                            playerData2.getInRequests().remove(((ProxiedPlayer) sender).getUniqueId().toString());

                            sender.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_REMOVE, "NAME", playerData2.getName()));
                            if (p != null) {
                                p.sendMessage(Dictionary.format(Dictionary.INREQUESTS_REMOVE, "NAME", sender.getName()));
                            }

                        } else if (playerData.getInRequests().contains(uuid)) { // If sender has incoming request, will treat as denying request.
                            playerData.getInRequests().remove(uuid);
                            playerData2.getOutRequests().remove(((ProxiedPlayer) sender).getUniqueId().toString());

                            sender.sendMessage(Dictionary.format(Dictionary.INREQUESTS_REMOVE, "NAME", playerData2.getName()));
                            if (p != null) {
                                p.sendMessage(Dictionary.format(Dictionary.OUTREQUESTS_REMOVE, "NAME", sender.getName()));
                            }

                        } else {
                            sender.sendMessage(Dictionary.format(Dictionary.CANNOT_REMOVE_FRIEND, "NAME", playerData2.getName()));
                        }
                        if (p != null) { // If target player is online, will save. If not, will write to disk and unload.
                            PlayerData.setData(p.getUniqueId().toString(), playerData2);
                        } else {
                            playerData2.save();
                        }
                    }
                }
            } else {
                sender.sendMessage(Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, "HELP", getName() + " [list|add <player>|remove <player>]"));
            }
        } else {
            sender.sendMessage(Dictionary.color("&cConsole does not have any friends."));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        switch (args.length) {
            case 2:
                return tabPlayers(sender, args[1]);
            case 1:
                return tabStrings(args[0], new String[]{"list", "add", "remove"});
            default:
                return ImmutableSet.of();
        }
    }
}
