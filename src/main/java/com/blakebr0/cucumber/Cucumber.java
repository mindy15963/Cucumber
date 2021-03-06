package com.blakebr0.cucumber;

import com.blakebr0.cucumber.command.ModCommands;
import com.blakebr0.cucumber.config.ModConfigs;
import com.blakebr0.cucumber.crafting.ModRecipeSerializers;
import com.blakebr0.cucumber.crafting.TagMapper;
import com.blakebr0.cucumber.event.BowFovHandler;
import com.blakebr0.cucumber.event.TagTooltipHandler;
import com.blakebr0.cucumber.network.NetworkHandler;
import com.blakebr0.cucumber.render.GlowingTextRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cucumber.MOD_ID)
public class Cucumber {
	public static final String NAME = "Cucumber Library";
	public static final String MOD_ID = "cucumber";

	public Cucumber() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.register(this);
		bus.register(new ModRecipeSerializers());

		MinecraftForge.EVENT_BUS.register(this);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModConfigs.CLIENT);
	}

	@SubscribeEvent
	public void onCommonSetup(FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(() -> {
			NetworkHandler.onCommonSetup();
			TagMapper.reloadTagMappings();
		});
	}

 	@SubscribeEvent
	public void onClientSetup(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new GlowingTextRenderer());
		MinecraftForge.EVENT_BUS.register(new BowFovHandler());
		MinecraftForge.EVENT_BUS.register(new TagTooltipHandler());
	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		ModCommands.onServerStarting(event.getCommandDispatcher());
	}
}
