package AAM.Core;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import AAM.API.PageItemList;
import AAM.API.StackList;
import AAM.Client.Gui.Base.ObjTypes;
import AAM.Common.Aura.AuraRegistry;
import AAM.Common.Blocks.Building.ModBlocks;
import AAM.Common.Dungeon.DungeonProvider;
import AAM.Common.Entity.ModEntities;
import AAM.Common.Event.ClientRenderHelper;
import AAM.Common.Event.PlayerBlockEvent;
import AAM.Common.Event.PlayerDataEventHandler;
import AAM.Common.Event.PotionEventHandler;
import AAM.Common.Event.SoulEvent;
import AAM.Common.Event.WorldTracker;
import AAM.Common.Items.ModItems;
import AAM.Common.Potions.Ingridients;
import AAM.Common.Potions.ModPotions;
import AAM.Common.Recipes.Recipes;
import AAM.Common.Skills.ModSkills;
import AAM.Common.Tiles.ModTiles;
import AAM.Common.Transmutations.EnergyProvider;
import AAM.Common.Transmutations.ModCircles;
import AAM.Common.WorldGen.PlantWorldGen;
import AAM.Network.ClientProxy;
import AAM.Network.CommonProxy;
import AAM.Network.Packages.AlchemicalDispatcher;
import AAM.Utils.Structures;
import AAM.Utils.TypeUtils;
import DummyCore.Core.Core;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.EntryBase;
import amerifrance.guideapi.api.util.BookBuilder;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.categories.CategoryResourceLocation;
import amerifrance.guideapi.pages.PageFurnaceRecipe;
import amerifrance.guideapi.pages.PageIRecipe;
import amerifrance.guideapi.pages.PageText;
import amerifrance.guideapi.pages.reciperenderers.ShapedOreRecipeRenderer;
import amerifrance.guideapi.pages.reciperenderers.ShapelessOreRecipeRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = AAMCore.modid, version = AAMCore.version, name = AAMCore.name)
public class AAMCore
{
	public static final String modid = "aam";
	public static final String name = "Ancient Art of Magic";
	public static final String version = "0.2a";

	@Instance(AAMCore.modid)
	public static AAMCore instance;

	@SidedProxy(serverSide = "AAM.Network.CommonProxy", clientSide = "AAM.Network.ClientProxy")
	public static CommonProxy proxy;

	public static AAMConfig cfg;
	public static String cfgDir;

	public static KeyBinding soul = new KeyBinding("aam.soul", Keyboard.KEY_G, "key.categories.inventory");
	public static KeyBinding member = new KeyBinding("aam.addmember", Keyboard.KEY_H, "key.categories.inventory");
	public static KeyBinding arts = new KeyBinding("aam.arts", Keyboard.KEY_J, "key.categories.inventory");
	public static int dungdimid = 15;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		cfgDir = event.getModConfigurationDirectory().getAbsolutePath();
		File cfgFile = new File(cfgDir, name + ".cfg");
		Configuration cfgf = new Configuration(cfgFile);
		cfg = new AAMConfig(cfgf);
		cfg.load(cfgf);
		TypeUtils.initStr();
		AlchemicalDispatcher.registerPackets();

		ClientRegistry.registerKeyBinding(soul);
		ClientRegistry.registerKeyBinding(member);
		ClientRegistry.registerKeyBinding(arts);

		Core.registerModAbsolute(AAMCore.class, name, event.getModConfigurationDirectory().getAbsolutePath(), cfg, false);
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(new SoulEvent());
		MinecraftForge.EVENT_BUS.register(new PotionEventHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerDataEventHandler());
		MinecraftForge.EVENT_BUS.register(new ClientRenderHelper());
		MinecraftForge.EVENT_BUS.register(new PlayerBlockEvent());
		FMLCommonHandler.instance().bus().register(new WorldTracker());

		ModTiles.load();
		ModBlocks.load();
		ModSkills.load();
		ModEntities.load();
		ModPotions.load();
		EnergyProvider.load();

		Structures.load();

		Ingridients.load();
		AuraRegistry.load();
		ObjTypes.load();
		Recipes.load();

		DimensionManager.registerProviderType(dungdimid, DungeonProvider.class, false);
		DimensionManager.registerDimension(dungdimid, dungdimid);

		GameRegistry.registerWorldGenerator(new PlantWorldGen(), 0);
		// GameRegistry.registerWorldGenerator(new DungGenerator(), 0);
		ClientProxy.registerRenders();

		buildBook();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		if (instance == null)
			instance = this;

		ModCircles.load();

		ModItems.load();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	public static Book Enchir;

	public static void buildBook()
	{
		ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();

		// Potions
		List<EntryAbstract> entries = new ArrayList<EntryAbstract>();
		ArrayList<IPage> pbase = new ArrayList<IPage>();
		pbase.add(new PageText("aam.p1.txt1", 0));
		pbase.add(new PageText("aam.p1.txt2", 0));

		pbase.add(new PageIRecipe(Recipes.cauldron, new ShapedOreRecipeRenderer(Recipes.cauldron)));
		pbase.add(new PageIRecipe(Recipes.sandy_glass, new ShapelessOreRecipeRenderer(Recipes.sandy_glass)));
		pbase.add(new PageFurnaceRecipe(ModBlocks.SandyGlass));
		pbase.add(new PageIRecipe(Recipes.phials, new ShapedOreRecipeRenderer(Recipes.phials)));
		entries.add(new EntryBase(pbase, "aam.p1.name"));

		ArrayList<IPage> plants = new ArrayList<IPage>();
		plants.add(new PageText("aam.pw.txt1", 0));
		plants.add(new PageText("aam.pw.txt2", 0));

		StackList pl = new StackList();
		pl.add(new ItemStack[] { new ItemStack(ModItems.Berry, 1, 0), new ItemStack(ModItems.Berry, 1, 1), new ItemStack(ModItems.Berry, 1, 2), new ItemStack(ModItems.Berry, 1, 3) }, "Berries can be found on berry bushes over the world");
		pl.add(new ItemStack(ModBlocks.BerryBush, 1, 0), "Bushes grows in forests and fields");
		pl.add(new ItemStack(ModBlocks.ModSaplings[0], 1, 0), "Wormwood must be created by alchemy");
		pl.add(new ItemStack(ModBlocks.ShadowveilPlant, 1, 0), "Shadowveil can be found in the the world");
		pl.add(new ItemStack(ModBlocks.ShroomPlant, 1, 0), "Shroom can be found in the the world");

		plants.add(new PageItemList("aam.itemlist", pl));

		entries.add(new EntryBase(plants, "aam.plants.name"));

		for (int k = 0; k < 8; k++)
		{
			ArrayList<IPage> potion = new ArrayList<IPage>();
			potion.add(new PageText("aam.p" + (k + 2) + ".txt1", 0));
			StackList sl = new StackList();

			int fsize = ModPotions.pots[k].ingridients.size() > 7 ? 7 : ModPotions.pots[k].ingridients.size();

			for (int i = 0; i < fsize; i++)
			{
				ItemStack is = new ItemStack(ModPotions.pots[k].ingridients.get(i).item, 1, ModPotions.pots[k].ingridients.get(i).meta);
				sl.add(is, is.getDisplayName());
			}
			potion.add(new PageItemList("aam.itemlist", sl));
			if (ModPotions.pots[k].ingridients.size() > 7)
			{
				sl = new StackList();
				for (int i = 7; i < ModPotions.pots[k].ingridients.size(); i++)
				{
					ItemStack is = new ItemStack(ModPotions.pots[k].ingridients.get(i).item, 1, ModPotions.pots[k].ingridients.get(i).meta);
					sl.add(is, is.getDisplayName());
				}
				potion.add(new PageItemList("aam.itemlist", sl));

			}

			entries.add(new EntryBase(potion, "aam.p" + (k + 2) + ".name"));
		}
		categories.add(new CategoryResourceLocation(entries, "aam.magic.name", new ResourceLocation("aam", "textures/book/magic.png")));

		// Alchemy
		entries = new ArrayList<EntryAbstract>();
		ArrayList<IPage> a1 = new ArrayList<IPage>();
		a1.add(new PageText("aam.a1.txt1", 0));
		entries.add(new EntryBase(a1, "aam.a1.name"));
		a1.add(new PageText("aam.a2.txt1", 0));
		entries.add(new EntryBase(a1, "aam.a2.name"));
		categories.add(new CategoryResourceLocation(entries, "aam.alchemy.name", new ResourceLocation("aam", "textures/items/tools/clock_enchanced.png")));

		// Soul
		entries = new ArrayList<EntryAbstract>();
		ArrayList<IPage> s1 = new ArrayList<IPage>();
		s1.add(new PageText("aam.s1.txt1", 0));
		entries.add(new EntryBase(s1, "aam.s1.name"));
		s1.add(new PageText("aam.s2.txt1", 0));
		entries.add(new EntryBase(a1, "aam.s2.name"));
		categories.add(new CategoryItemStack(entries, "aam.soul.name", new ItemStack(ModItems.SoulSword)));

		BookBuilder builder = new BookBuilder();
		builder.setOutlineTexture(new ResourceLocation("aam", "textures/book/outline.png"));
		builder.setPageTexture(new ResourceLocation("aam", "textures/book/page.png"));
		builder.setItemTexture("aam:book");
		builder.setCategories(categories);
		builder.setUnlocBookTitle("aam.enchir.title");
		builder.setUnlocDisplayName("aam.enchir.name");
		builder.setAuthor("by Ancient Lord");
		builder.setUnlocWelcomeMessage("Hello");
		builder.setSpawnWithBook(true);
		builder.setBookColor(Color.red);
		Enchir = builder.build();
		GuideRegistry.registerBook(Enchir);
	}

}