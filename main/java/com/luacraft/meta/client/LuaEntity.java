package com.luacraft.meta.client;

import com.luacraft.LuaCraft;
import com.luacraft.LuaCraftState;
import com.luacraft.classes.Vector;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class LuaEntity {
	private static Minecraft client = LuaCraft.getForgeClient().getClient();

	/**
	 * @author Gregor
	 * @function GetPos
	 * @info Returns the entity position
	 * @arguments nil
	 * @return [[Vector]]:pos
	 */

	public static JavaFunction GetPos = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			double posX = self.lastTickPosX + (self.posX - self.lastTickPosX) * client.timer.renderPartialTicks;
			double posY = self.lastTickPosY + (self.posY - self.lastTickPosY) * client.timer.renderPartialTicks;
			double posZ = self.lastTickPosZ + (self.posZ - self.lastTickPosZ) * client.timer.renderPartialTicks;

			Vector pos = new Vector(posX, posZ, posY);
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function GetEyePos
	 * @info Returns the entitys eye position
	 * @arguments nil
	 * @return [[Vector]]:pos
	 */

	public static JavaFunction GetEyePos = new JavaFunction() {
		public int invoke(LuaState l) {
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			double posX = self.lastTickPosX + (self.posX - self.lastTickPosX) * client.timer.renderPartialTicks;
			double posY = self.lastTickPosY + (self.posY - self.lastTickPosY) * client.timer.renderPartialTicks;
			double posZ = self.lastTickPosZ + (self.posZ - self.lastTickPosZ) * client.timer.renderPartialTicks;

			Vector pos = new Vector(posX, posZ, posY + self.getEyeHeight());
			pos.push(l);
			return 1;
		}
	};

	public static void Init(final LuaCraftState l) {
		l.newMetatable("Entity");
		{
			l.pushJavaFunction(GetPos);
			l.setField(-2, "GetPos");
			l.pushJavaFunction(GetEyePos);
			l.setField(-2, "GetEyePos");
		}
		l.pop(1);
	}
}
