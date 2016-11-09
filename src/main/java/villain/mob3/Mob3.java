package villain.mob3;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import villain.mob3.client.Mob3Tab;
import villain.mob3.proxy.CommonProxy;

@Mod(modid = Mob3.MODID, version = Mob3.VERSION)
public class Mob3 {

	public static final String MODID = "mob3";
	public static final String MODNAME = "Mob\u00B3";
	public static final String VERSION = "0.0.1";
	
	@SidedProxy(serverSide = "villain.mob3.proxy.CommonProxy", clientSide = "villain.mob3.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance(MODID)
	public static Mob3 instance;
	
	public static Logger logger;
	
	public static final Mob3Tab creativeTab = new Mob3Tab();
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e){
		logger = e.getModLog();
		proxy.preInit(e);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e){
		proxy.init(e);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e){
		proxy.postInit(e);
	}
}
