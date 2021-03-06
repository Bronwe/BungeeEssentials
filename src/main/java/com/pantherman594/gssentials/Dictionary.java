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

package com.pantherman594.gssentials;

import com.google.common.base.Joiner;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;

@SuppressWarnings("WeakerAccess")
public class Dictionary {
    private static final String DEFAULT_CONFIG_VALUE = "INVALID CONFIGURATION VALUE";
    @Load(key = "errors.messages", def = "&cNobody has messaged you recently.")
    public static String ERROR_NOBODY_HAS_MESSAGED;
    @Load(key = "errors.slap", def = "&cYou are unworthy of slapping people.")
    public static String ERROR_UNWORTHY_OF_SLAP;
    @Load(key = "errors.offline", def = "&cSorry, that player is offline.")
    public static String ERROR_PLAYER_OFFLINE;
    @Load(key = "errors.invalid", def = "&cInvalid arguments provided. Usage: {{ HELP }}")
    public static String ERROR_INVALID_ARGUMENTS;
    @Load(key = "errors.ignoreself", def = "&cYou can't ignore yourself!")
    public static String ERROR_IGNORE_SELF;
    @Load(key = "errors.ignoring", def = "&cYou can't send a message to someone you're ignoring!")
    public static String ERROR_IGNORING;
    @Load(key = "errors.sendfail", def = "&cUnable to send {{ PLAYER }} to {{ SERVER }}.")
    public static String ERROR_SENDFAIL;
    @Load(key = "format.send", def = "&aSending &e{{ PLAYER }} &ato server &e{{ SERVER }}")
    public static String FORMAT_SEND_PLAYER;
    @Load(key = "format.find", def = "&e{{ PLAYER }} &ais playing on &e{{ SERVER }}")
    public static String FORMAT_FIND_PLAYER;
    @Load(key = "format.join", def = "&8[&b+&8] &7{{ PLAYER }}")
    public static String FORMAT_JOIN;
    @Load(key = "format.quit", def = "&8[&a-&8] &7{{ PLAYER }}")
    public static String FORMAT_QUIT;
    @Load(key = "format.quit", def = "{{ PLAYER }} was kicked for {{ REASON }}")
    public static String FORMAT_KICK;
    @Load(key = "format.chat", def = "{{ PLAYER }}: {{ MESSAGE }}")
    public static String FORMAT_CHAT;
    @Load(key = "format.alert", def = "&8[&c!&8] &7{{ MESSAGE }}")
    public static String FORMAT_ALERT;
    @Load(key = "message.format.send", def = "&7[me » {{ BREAK }}&7{{ RECIPIENT }}{{ HOVER: On the {{ SERVER }} server. }}{{ BREAK }}&7] &f{{ MESSAGE }}")
    public static String MESSAGE_FORMAT_SEND;
    @Load(key = "message.format.receive", def = "&7[{{ BREAK }}&7{{ SENDER }}{{ HOVER: On the {{ SERVER }} server. }}{{ BREAK }}&7 » me] &f{{ MESSAGE }}")
    public static String MESSAGE_FORMAT_RECEIVE;
    @Load(key = "message.enabled", def = "&aMessaging is now enabled!")
    public static String MESSAGE_ENABLED;
    @Load(key = "message.disabled", def = "&cMessaging is now disabled!")
    public static String MESSAGE_DISABLED;
    @Load(key = "friend.header", def = "&2Current Friends:")
    public static String FRIEND_HEADER;
    @Load(key = "friend.body", def = "- {{ NAME }} ({{ SERVER }})")
    public static String FRIEND_BODY;
    @Load(key = "friend.new", def = "&aYou are now friends with {{ NAME }}!")
    public static String FRIEND_NEW;
    @Load(key = "friend.old", def = "&aYou are already friends with {{ NAME }}!")
    public static String FRIEND_OLD;
    @Load(key = "friend.remove", def = "&cYou are no longer friends with {{ NAME }}.")
    public static String FRIEND_REMOVE;
    @Load(key = "friend.removeerror", def = "&cYou can't remove a friend you don't have!")
    public static String CANNOT_REMOVE_FRIEND;
    @Load(key = "friend.outrequests.header", def = "&2Outgoing Friend Requests:")
    public static String OUTREQUESTS_HEADER;
    @Load(key = "friend.outrequests.body", def = "- {{ NAME }}")
    public static String OUTREQUESTS_BODY;
    @Load(key = "friend.outrequests.new", def = "&a{{ NAME }} has received your friend request.")
    public static String OUTREQUESTS_NEW;
    @Load(key = "friend.outrequests.old", def = "&cYou already requested to be friends with {{ NAME }}. Please wait for a response!")
    public static String OUTREQUESTS_OLD;
    @Load(key = "friend.outrequests.remove", def = "&cThe friend request to {{ NAME }} was cancelled.")
    public static String OUTREQUESTS_REMOVE;
    @Load(key = "friend.inrequests.header", def = "&2Incoming Friend Requests:")
    public static String INREQUESTS_HEADER;
    @Load(key = "friend.inrequests.body", def = "- {{ NAME }}{{ BREAK }}  &a[Y]{{ HOVER: Click to accept the request! }}{{ CLICK: /friend add {{ NAME }} }}{{ BREAK }} {{ BREAK }}&c[N]{{ HOVER: Click to deny the request. }}{{ CLICK: /friend remove {{ NAME }} }}")
    public static String INREQUESTS_BODY;
    @Load(key = "friend.inrequests.new", def = "&a{{ NAME }} would like to be your friend! Click one:{{ BREAK }}\n&a[Yes!]{{ HOVER: Click to accept the request! }}{{ CLICK: /friend add {{ NAME }} }}{{ BREAK }}     {{ BREAK }}&c[No]{{ HOVER: Click to deny the request. }}{{ CLICK: /friend remove {{ NAME }} }}")
    public static String INREQUESTS_NEW;
    @Load(key = "friend.inrequests.remove", def = "&cThe friend request from {{ NAME }} was cancelled.")
    public static String INREQUESTS_REMOVE;
    @Load(key = "list.header", def = "You are on {{ CURRENT }}\n&aServers:")
    public static String LIST_HEADER;
    @Load(key = "list.body", def = "&a- {{ SERVER }} ({{ DENSITY }}&a)")
    public static String LIST_BODY;
    @Load(key = "lookup.header", def = "&6Found {{ SIZE }} player(s):")
    public static String LOOKUP_HEADER;
    @Load(key = "lookup.body", def = "&f - {{ PLAYER }}")
    public static String LOOKUP_BODY;
    @Load(key = "spy.message", def = "&a({{ SERVER }}) &7[{{ SENDER }} » {{ RECIPIENT }}] &f{{{ MESSAGE }}}")
    public static String SPY_MESSAGE;
    @Load(key = "spy.enabled", def = "&aSpy has been enabled!")
    public static String SPY_ENABLED;
    @Load(key = "spy.disabled", def = "&cSpy has been disabled!")
    public static String SPY_DISABLED;
    @Load(key = "commandspy.command", def = "&7[{{ SENDER }}] &b{{ COMMAND }}")
    public static String CSPY_COMMAND;
    @Load(key = "commandspy.enabled", def = "&aCommand Spy has been enabled!")
    public static String CSPY_ENABLED;
    @Load(key = "commandspy.disabled", def = "&cCommand Spy has been disabled!")
    public static String CSPY_DISABLED;
    @Load(key = "hide.enabled", def = "&aYou are now hidden from all users!")
    public static String HIDE_ENABLED;
    @Load(key = "hide.disabled", def = "&cYou are no longer hidden!")
    public static String HIDE_DISABLED;
    @Load(key = "ignore.enabled", def = "&6Now ignoring &{ PLAYER }}.")
    public static String IGNORE_ENABLED;
    @Load(key = "ignore.disabled", def = "&6No longer ignoring {{ PLAYER }}.")
    public static String IGNORE_DISABLED;
    @Load(key = "mute.muted.enabled", def = "&cYou are now muted!")
    public static String MUTE_ENABLED;
    @Load(key = "mute.muted.disabled", def = "&aYou are no longer muted!")
    public static String MUTE_DISABLED;
    @Load(key = "mute.muted.error", def = "&cHey, you can't chat while muted!")
    public static String MUTE_ERROR;
    @Load(key = "mute.muter.enabled", def = "&c{{ PLAYER }} is now muted!")
    public static String MUTE_ENABLEDN;
    @Load(key = "mute.muter.disabled", def = "&a{{ PLAYER }} is no longer muted!")
    public static String MUTE_DISABLEDN;
    @Load(key = "mute.muter.error", def = "&7{{ PLAYER }} tried to chat while muted!")
    public static String MUTE_ERRORN;
    @Load(key = "mute.muter.exempt", def = "&cHey, you can't mute that player!")
    public static String MUTE_EXEMPT;
    @Load(key = "slap.slapper", def = "&aYou just slapped &e{{ SLAPPED }}&a. I bet that felt good, didn't it?")
    public static String SLAPPER_MSG;
    @Load(key = "slap.slapped", def = "&cYou were just slapped by &e{{ SLAPPER }}&c! Ouch! (/slap him back!)")
    public static String SLAPPED_MSG;
    @Load(key = "rulenotify.advertisement", def = "&7{{ PLAYER }} just advertised!")
    public static String NOTIFY_ADVERTISEMENT;
    @Load(key = "rulenotify.cursing", def = "&7{{ PLAYER }} just swore!")
    public static String NOTIFY_CURSING;
    @Load(key = "rulenotify.replace", def = "&7{{ PLAYER }} swore but was replaced!")
    public static String NOTIFY_REPLACE;
    @Load(key = "rulenotify.command", def = "&7{{ PLAYER }} swore, triggering a command!")
    public static String NOTIFY_COMMAND;
    @Load(key = "bannedwords.replace", def = "*")
    public static String BANNED_REPLACE;
    @Load(key = "staffchat.message", def = "&c[{{ SERVER }} - {{ SENDER }}] » &7{{ MESSAGE }}")
    public static String FORMAT_STAFF_CHAT;
    @Load(key = "staffchat.enabled", def = "&aYou are now chatting in staff chat!")
    public static String SCHAT_ENABLED;
    @Load(key = "staffchat.disabled", def = "&cYou are no longer chatting in staff chat!")
    public static String SCHAT_DISABLED;
    @Load(key = "chat.message", def = "&e{{ SERVER }} - {{ SENDER }} » &7{{ MESSAGE }}")
    public static String FORMAT_GCHAT;
    @Load(key = "chat.enabled", def = "&aYou are now chatting in global chat!")
    public static String GCHAT_ENABLED;
    @Load(key = "chat.disabled", def = "&cYou are no longer chatting in global chat!")
    public static String GCHAT_DISABLED;
    @Load(key = "warnings.similarity", def = "&cPlease do not spam other players!")
    public static String WARNING_LEVENSHTEIN_DISTANCE;
    @Load(key = "warnings.swearing", def = "&cPlease do not swear at other players!")
    public static String WARNING_HANDLE_CURSING;
    @Load(key = "warnings.advertising", def = "&cPlease do not advertise other servers!")
    public static String WARNINGS_ADVERTISING;
    @Load(key = "errors.fastrelog", def = "&cPlease wait before reconnecting!")
    public static String FAST_RELOG_KICK;
    private static SimpleDateFormat date;

    private static Calendar calendar;

    static {
        calendar = Calendar.getInstance();
        date = new SimpleDateFormat("H:mm:ss");
    }

    /**
     * Converts color codes to colors.
     *
     * @param str The string to convert.
     * @return The converted string.
     */
    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    /**
     * Combines all strings in an array to a space-delimited string.
     *
     * @param array The array to combine.
     * @return The combined array.
     */
    public static String combine(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String string : array) {
            builder.append(string);
            if (!string.equals(array[array.length - 1])) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    /**
     * Combines all strings except 1 in an array to a space-delimited string.
     *
     * @param omit  The position of a string to omit
     * @param array The array to combine.
     * @return The combined array.
     */
    public static String combine(int omit, String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String string : array) {
            if (!string.equals(array[omit])) {
                builder.append(string);
                if (!string.equals(array[array.length - 1])) {
                    builder.append(" ");
                }
            }
        }
        return builder.toString();
    }

    /**
     * Formats a string with global variables as well as
     * provided ones, including hover and click events.
     *
     * @param input The string to format.
     * @param color Whether to format colors.
     * @param hover Whether to format hovers.
     * @param click Whether to format clicks.
     * @param args  Arguments to format.
     * @return The formatted string.
     */
    public static TextComponent format(String input, boolean color, boolean hover, boolean click, String... args) {
        input = input.replace("{{ TIME }}", getTime());
        // Minor fix for people who suffer from encoding issues
        input = input.replace("{{ RAQUO }}", "»");

        if (args.length % 2 == 0) {
            for (int i = 0; i < args.length; i += 2) {
                if (args[i] == null || args[i + 1] == null) {
                    return null;
                }
                input = input.replace("{{ " + args[i].toUpperCase() + " }}", args[i + 1]);
            }
        }

        if (!input.contains("{{ BREAK }}"))
            input += "{{ BREAK }}";

        TextComponent finalText = new TextComponent("");

        for (String segment : input.split("\\{\\{ BREAK }}")) {

            HoverEvent hoverEvent = null;
            ClickEvent clickEvent = null;

            if (hover) {
                if (segment.contains("{{ HOVER: ")) {
                    String[] hoverTextStripped = segment.split("(\\{\\{ HOVER: )([^\\}]+)( \\}\\})");
                    String hoverText = segment;
                    for (String aHoverTextStripped : hoverTextStripped) {
                        hoverText = hoverText.replace(aHoverTextStripped, "");
                    }
                    hoverText = hoverText.replace("{{ HOVER: ", "").replace(" }}", "");
                    if (hoverText != null) {
                        hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color ? color(hoverText) : hoverText).retain(ComponentBuilder.FormatRetention.ALL).create());
                        segment = Joiner.on("").join(hoverTextStripped);
                    }
                }
            }

            if (click) {
                if (segment.contains("{{ CLICK: ")) {
                    String[] clickTextStripped = segment.split("(\\{\\{ CLICK: )([^\\}]+)( \\}\\})");
                    String clickText = segment;
                    for (String aClickTextStripped : clickTextStripped) {
                        clickText = clickText.replace(aClickTextStripped, "");
                    }
                    clickText = clickText.replace("{{ CLICK: ", "").replace(" }}", "");
                    if (clickText != null) {
                        clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickText);
                        segment = Joiner.on("").join(clickTextStripped);
                    }
                }
            }

            TextComponent textSegment = new TextComponent(TextComponent.fromLegacyText(color ? color(segment) : segment));
            textSegment.setHoverEvent(hoverEvent);
            textSegment.setClickEvent(clickEvent);

            finalText.addExtra(textSegment);
        }

        return finalText;
    }

    /**
     * Default format for plugin messages (enables all formatting).
     *
     * @param input String to format.
     * @param args  Arguments to format.
     * @return The formatted string.
     */
    public static TextComponent format(String input, String... args) {
        return format(input, true, true, true, args);
    }

    /**
     * Formats a message based on the player's permissions for color, hover, and click.
     *
     * @param input String to format
     * @param args  Arguments to format.
     * @return The formatted string.
     */
    public static TextComponent formatMsg(String input, String... args) {
        TextComponent finalText = new TextComponent("");

        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[3]);
        if (player != null) {
            int count = (input.length() - input.replace("{{ MESSAGE }}", "").length()) / 13;
            TextComponent message = format("{{ MESSAGE }}", player.hasPermission(Permissions.General.MESSAGE_COLOR), player.hasPermission(Permissions.General.MESSAGE_HOVER), player.hasPermission(Permissions.General.MESSAGE_CLICK), args);

            if (input.startsWith("{{ MESSAGE }}")) {
                count--;
                finalText.addExtra(message);
            }

            if (input.endsWith("{{ MESSAGE }}")) {
                count--;
            }

            for (String part : input.split("\\{\\{ MESSAGE }}")) {
                finalText.addExtra(format(part, args));
                if (count > 0) {
                    count--;
                    finalText.addExtra(message);
                }
            }

            if (input.endsWith("{{ MESSAGE }}")) {
                finalText.addExtra(message);
            }

            return finalText;

        }
        return format(color(input), args);
    }

    /**
     * Use reflection and the {@link Load} annotation to load values
     * from the configuration file without typing out all the keys
     *
     * @throws IllegalAccessException
     */
    synchronized static void load() throws IllegalAccessException {
        Configuration messages = BungeeEssentials.getInstance().getMessages();
        for (Field field : Dictionary.class.getDeclaredFields()) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) && Modifier.isPublic(mod) && field.isAnnotationPresent(Load.class)) {
                Load load = field.getAnnotation(Load.class);
                String value = messages.getString(load.key(), DEFAULT_CONFIG_VALUE);
                if (value.equals(DEFAULT_CONFIG_VALUE)) {
                    /*
                     * Why not just load the default value?
                     * That doesn't let the admin know that there's a problem,
                     * this is my personal preference and if you wish to edit
                     * it to use the default value immediately then please do.
                     *
                     * Once the admin is notified of the problem THEN we fall
                     * back to the default value specified anyway.
                     */
                    BungeeEssentials.getInstance().getLogger().log(Level.WARNING, "Your configuration is either outdated or invalid!");
                    BungeeEssentials.getInstance().getLogger().log(Level.WARNING, "Falling back to default value for key \"{0}\"", load.key());
                    value = load.def();
                }
                field.set(null, value);
            }
        }
    }

    static String getTime() {
        return date.format(calendar.getTime());
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface Load {
        String key();

        String def();
    }
}
