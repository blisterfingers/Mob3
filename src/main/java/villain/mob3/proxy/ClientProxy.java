package villain.mob3.proxy;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import villain.mob3.Mob3;
import villain.mob3.ModBlocks;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		
		// Add domain to OBJ loader
		OBJLoader.INSTANCE.addDomain(Mob3.MODID);
		
		// Init Models
		ModBlocks.initModels();
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		
		// Init item models
		ModBlocks.initItemModels();
	}
}
