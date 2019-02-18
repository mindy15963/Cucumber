package com.blakebr0.cucumber.event;

import com.blakebr0.cucumber.iface.ICustomBow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BowFovHandler {

	@SubscribeEvent
	public static void onFovChanged(FOVUpdateEvent event) {
		EntityPlayer entity = event.getEntity();
		if (entity == null) return;
		
		ItemStack stack = entity.getActiveItemStack();
		if (!stack.isEmpty()) {
			Item item = stack.getItem();
			if (item instanceof ICustomBow) {
				ICustomBow bow = (ICustomBow) item;
				float f = MathHelper.clamp((stack.getUseDuration() - entity.getItemInUseCount()) * bow.getDrawSpeedMulti(stack) / 20.0F, 0, 1.0F);
				event.setNewfov(event.getFov() - (f * f * 0.15F));
			}
		}
	}
}
