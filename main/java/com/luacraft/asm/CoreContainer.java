package com.luacraft.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.luacraft.LuaCraft;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class CoreContainer extends DummyModContainer {
	public CoreContainer() {
		super(new ModMetadata());

		ModMetadata metaData = super.getMetadata();
		metaData.authorList = Arrays.asList("LuaStoned", "BlackAwps", "iRzilla", "Skooch");
		metaData.description = "CoreMod for LuaCraft.";
		metaData.modId = "luacraftcore";
		metaData.version = LuaCraft.VERSION;
		metaData.name = "LuaCraft Core";
		metaData.url = "http://luacraft.com";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}