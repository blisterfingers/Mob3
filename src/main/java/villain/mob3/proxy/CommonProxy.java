package villain.mob3.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import villain.mob3.Mob3;
import villain.mob3.ModBlocks;
import villain.mob3.client.GuiHandler;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		// Init blocks
		ModBlocks.init();
	}
	
	public void init(FMLInitializationEvent e){
		// GUI
		NetworkRegistry.INSTANCE.registerGuiHandler(Mob3.instance, new GuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent e){
		
	}
}