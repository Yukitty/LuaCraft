package com.luacraft.library.client;

import java.util.List;

import com.luacraft.LuaCraft;
import com.luacraft.LuaCraftState;
import com.luacraft.library.LuaLibUtil;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ChatComponentText;

public class LuaLibGame {
	private static Minecraft client = LuaCraft.getForgeClient().getClient();
	private static NetworkManager net = LuaCraft.getForgeClient().getClientToServerNetworkManager();

	/**
	 * @author Matt
	 * @library game
	 * @function MaxPlayers
	 * @info Get the number of available player slots
	 * @arguments nil
	 * @return [[Number]]:slots
	 */

	public static JavaFunction MaxPlayers = new JavaFunction() {
		public int invoke(LuaState l) {
			if (client.thePlayer == null)
				return 0;

			l.pushNumber(client.thePlayer.sendQueue.currentServerMaxPlayers);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library game
	 * @function HostAddress
	 * @info Get the ip:port of the current connected server
	 * @arguments nil
	 * @return [[String]]:IP
	 */

	public static JavaFunction HostAddress = new JavaFunction() {
		public int invoke(LuaState l) {
			l.pushString(net.channel().remoteAddress().toString());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library game
	 * @function PlayerInfo
	 * @info Return a table of information for every player on the server
	 * @arguments nil
	 * @return [[Table]]:info
	 */

	public static JavaFunction PlayerInfo = new JavaFunction() {
		public int invoke(LuaState l) {
			if (client.thePlayer == null)
				return 0;
			
			NetHandlerPlayClient net = client.thePlayer.sendQueue;
			List<GuiPlayerInfo> playerInfo = net.playerInfoList;
			l.newTable();
			int i = 1;
			for (GuiPlayerInfo info: playerInfo)
			{
				l.pushInteger(i++);
				l.newTable();
				l.pushString(info.name);
				l.setField(-2, "name");
				l.pushInteger(info.responseTime);
				l.setField(-2, "ping");
				l.setTable(-3);
			}
			
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library game
	 * @function HasFocus
	 * @info Returns if the game is in focus
	 * @arguments nil
	 * @return [[Boolean]]:focus
	 */

	public static JavaFunction HasFocus = new JavaFunction() {
		public int invoke(LuaState l) {
			l.pushBoolean(client.inGameHasFocus);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library game
	 * @function Say
	 * @info Force yourself to say something
	 * @arguments [[String]]:message
	 * @return nil
	 */

	public static JavaFunction Say = new JavaFunction() {
		public int invoke(LuaState l) {
			if (client.thePlayer == null)
				return 0;

			client.thePlayer.sendChatMessage(l.checkString(1));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @library game
	 * @function ChatPrint
	 * @info Print a string of text to the chatbox
	 * @arguments [[String]]:msg, [[Number]]:color, ...
	 * @return nil
	 */

	public static JavaFunction ChatPrint = new JavaFunction() {
		public int invoke(LuaState l) {
			if (client.thePlayer == null)
				return 0;

			String chatMsg = LuaLibUtil.toChat(l, 1);
			client.thePlayer.addChatMessage(new ChatComponentText(chatMsg));
			return 0;
		}
	};

	public static void Init(final LuaCraftState l) {
		l.newTable();
		{
			l.pushJavaFunction(MaxPlayers);
			l.setField(-2, "MaxPlayers");
			l.pushJavaFunction(HostAddress);
			l.setField(-2, "HostAddress");
			l.pushJavaFunction(PlayerInfo);
			l.setField(-2, "PlayerInfo");
			l.pushJavaFunction(HasFocus);
			l.setField(-2, "HasFocus");
			l.pushJavaFunction(Say);
			l.setField(-2, "Say");
			l.pushJavaFunction(ChatPrint);
			l.setField(-2, "ChatPrint");
		}
		l.setGlobal("game");
	}
}
