package io.github.bioplethora.client;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.BPIdeFanBlock;
import io.github.bioplethora.blocks.BPLanternPlantBlock;
import io.github.bioplethora.client.armor.model.NandbricArmorModel;
import io.github.bioplethora.client.armor.model.ReinforcedFleignariteArmorModel;
import io.github.bioplethora.client.armor.render.AquChestplateRender;
import io.github.bioplethora.client.block.render.FleignariteSplotchBlockRender;
import io.github.bioplethora.client.entity.render.AlphemEntityRender;
import io.github.bioplethora.client.entity.render.AlphemKingEntityRender;
import io.github.bioplethora.client.entity.render.AltyrusEntityRender;
import io.github.bioplethora.client.entity.render.BPBoatRender;
import io.github.bioplethora.client.entity.render.CavernFleignarEntityRender;
import io.github.bioplethora.client.entity.render.CrephoxlEntityRender;
import io.github.bioplethora.client.entity.render.CuttlefishEntityRender;
import io.github.bioplethora.client.entity.render.DwarfMossadileEntityRender;
import io.github.bioplethora.client.entity.render.EurydnEntityRender;
import io.github.bioplethora.client.entity.render.FrostbiteGolemEntityRender;
import io.github.bioplethora.client.entity.render.GaugalemEntityRender;
import io.github.bioplethora.client.entity.render.GrylynenEntityRender;
import io.github.bioplethora.client.entity.render.MyliothanEntityRender;
import io.github.bioplethora.client.entity.render.MyuthineEntityRender;
import io.github.bioplethora.client.entity.render.NandbriEntityRender;
import io.github.bioplethora.client.entity.render.OnofishRenderer;
import io.github.bioplethora.client.entity.render.PeaguinEntityRender;
import io.github.bioplethora.client.entity.render.ShachathEntityRender;
import io.github.bioplethora.client.entity.render.TelemreyeEntityRender;
import io.github.bioplethora.client.entity.render.TerraithEntityRender;
import io.github.bioplethora.client.entity.render.TrapjawEntityRender;
import io.github.bioplethora.client.entity.render.TriggerfishRenderer;
import io.github.bioplethora.client.entity.render.VoidjawEntityRender;
import io.github.bioplethora.client.entity.render.others.AlphanumShardRender;
import io.github.bioplethora.client.entity.render.others.AltyrusSummoningRender;
import io.github.bioplethora.client.entity.render.others.BPEffectRender;
import io.github.bioplethora.client.entity.render.others.FrostbiteMetalShieldWaveRender;
import io.github.bioplethora.client.entity.render.others.GrylynenCoreBombRender;
import io.github.bioplethora.client.entity.render.others.PrimordialRingEntityRender;
import io.github.bioplethora.client.entity.render.projectile.AbyssalScalesRenderer;
import io.github.bioplethora.client.entity.render.projectile.AlphanumObliteratorSpearRender;
import io.github.bioplethora.client.entity.render.projectile.CryoblazeRender;
import io.github.bioplethora.client.entity.render.projectile.FrostbiteMetalArrowRender;
import io.github.bioplethora.client.entity.render.projectile.FrostbiteMetalClusterRender;
import io.github.bioplethora.client.entity.render.projectile.GaidiusBaseRenderer;
import io.github.bioplethora.client.entity.render.projectile.UltimateFrostbiteMetalClusterRender;
import io.github.bioplethora.client.entity.render.projectile.VermilionBladeProjectileRender;
import io.github.bioplethora.client.entity.render.projectile.WindArrowRender;
import io.github.bioplethora.client.entity.render.projectile.WindBlazeRender;
import io.github.bioplethora.gui.screen.ReinforcingTableScreen;
import io.github.bioplethora.item.armor.AquChestplateItem;
import io.github.bioplethora.item.weapons.AlphanumObliteratorItem;
import io.github.bioplethora.item.weapons.ArbitraryBallistaItem;
import io.github.bioplethora.item.weapons.FrostbiteMetalShieldItem;
import io.github.bioplethora.item.weapons.GrylynenShieldBaseItem;
import io.github.bioplethora.item.weapons.InfernalQuarterstaffItem;
import io.github.bioplethora.network.keybindings.BPKeybinds;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPContainerTypes;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import io.github.bioplethora.registry.BPTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BPClientRegister {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Minecraft mcClient = Minecraft.getInstance();
        
        //Ecoharmless
        event.registerEntityRenderer(BPEntities.CUTTLEFISH.get(), CuttlefishEntityRender::new);
        event.registerEntityRenderer(BPEntities.ONOFISH.get(), OnofishRenderer::new);
        event.registerEntityRenderer(BPEntities.TRIGGERFISH.get(), TriggerfishRenderer::new);

        event.registerEntityRenderer(BPEntities.SOUL_EURYDN.get(), EurydnEntityRender::new);
        event.registerEntityRenderer(BPEntities.FIERY_EURYDN.get(), EurydnEntityRender::new);

        //Plethoneutral
        event.registerEntityRenderer(BPEntities.PEAGUIN.get(), PeaguinEntityRender::new);
        event.registerEntityRenderer(BPEntities.NANDBRI.get(), NandbriEntityRender::new);
        event.registerEntityRenderer(BPEntities.CAVERN_FLEIGNAR.get(), CavernFleignarEntityRender::new);

        //Dangerum
        event.registerEntityRenderer(BPEntities.ALPHEM.get(), AlphemEntityRender::new);
        event.registerEntityRenderer(BPEntities.GAUGALEM.get(), GaugalemEntityRender::new);
        event.registerEntityRenderer(BPEntities.DWARF_MOSSADILE.get(), DwarfMossadileEntityRender::new);
        event.registerEntityRenderer(BPEntities.TRAPJAW.get(), TrapjawEntityRender::new);
        event.registerEntityRenderer(BPEntities.TERRAITH.get(), TerraithEntityRender::new);
        event.registerEntityRenderer(BPEntities.MYUTHINE.get(), MyuthineEntityRender::new);

        event.registerEntityRenderer(BPEntities.WOODEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        event.registerEntityRenderer(BPEntities.STONE_GRYLYNEN.get(), GrylynenEntityRender::new);
        event.registerEntityRenderer(BPEntities.GOLDEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        event.registerEntityRenderer(BPEntities.IRON_GRYLYNEN.get(), GrylynenEntityRender::new);
        event.registerEntityRenderer(BPEntities.DIAMOND_GRYLYNEN.get(), GrylynenEntityRender::new);
        event.registerEntityRenderer(BPEntities.NETHERITE_GRYLYNEN.get(), GrylynenEntityRender::new);

        //Hellsent
        event.registerEntityRenderer(BPEntities.CREPHOXL.get(), CrephoxlEntityRender::new);
        event.registerEntityRenderer(BPEntities.FROSTBITE_GOLEM.get(), FrostbiteGolemEntityRender::new);
        event.registerEntityRenderer(BPEntities.SHACHATH.get(), ShachathEntityRender::new);
        event.registerEntityRenderer(BPEntities.TELEMREYE.get(), TelemreyeEntityRender::new);
        event.registerEntityRenderer(BPEntities.VOIDJAW.get(), VoidjawEntityRender::new);

        //Elderia
        event.registerEntityRenderer(BPEntities.ALTYRUS.get(), AltyrusEntityRender::new);
        event.registerEntityRenderer(BPEntities.MYLIOTHAN.get(), MyliothanEntityRender::new);
        event.registerEntityRenderer(BPEntities.ALPHEM_KING.get(), AlphemKingEntityRender::new);

        //projectiles
        event.registerEntityRenderer(BPEntities.MAGMA_BOMB.get(), (rendererManager) -> new ThrownItemRenderer<>(rendererManager));
        event.registerEntityRenderer(BPEntities.WINDY_ESSENCE.get(), (rendererManager) -> new ThrownItemRenderer<>(rendererManager));
        event.registerEntityRenderer(BPEntities.ABYSSAL_SCALES.get(), (rendererManager) -> new AbyssalScalesRenderer<>(rendererManager, mcClient.getItemRenderer()));
        event.registerEntityRenderer(BPEntities.BELLOPHITE_CLUSTER.get(), FrostbiteMetalClusterRender::new);
        event.registerEntityRenderer(BPEntities.BELLOPHITE_ARROW.get(), FrostbiteMetalArrowRender::new);
        event.registerEntityRenderer(BPEntities.WINDBLAZE.get(), WindBlazeRender::new);
        event.registerEntityRenderer(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), UltimateFrostbiteMetalClusterRender::new);
        event.registerEntityRenderer(BPEntities.WIND_ARROW.get(), WindArrowRender::new);
        event.registerEntityRenderer(BPEntities.CRYOBLAZE.get(), CryoblazeRender::new);
        event.registerEntityRenderer(BPEntities.ALPHANUM_OBLITERATOR_SPEAR.get(), AlphanumObliteratorSpearRender::new);

        event.registerEntityRenderer(BPEntities.VERMILION_BLADE_PROJECTILE.get(), (rendererManager) -> new VermilionBladeProjectileRender<>(rendererManager, mcClient.getItemRenderer()));
        event.registerEntityRenderer(BPEntities.CRYEANUM_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));
        event.registerEntityRenderer(BPEntities.NETHERITE_OBSIDIAN_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));
        event.registerEntityRenderer(BPEntities.ECHO_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));

        //others
        event.registerEntityRenderer(BPEntities.CAERULWOOD_BOAT.get(), BPBoatRender::new);
        event.registerEntityRenderer(BPEntities.ENIVILE_BOAT.get(), BPBoatRender::new);

        event.registerEntityRenderer(BPEntities.PRIMORDIAL_RING.get(), PrimordialRingEntityRender::new);
        event.registerEntityRenderer(BPEntities.ALTYRUS_SUMMONING.get(), AltyrusSummoningRender::new);
        event.registerEntityRenderer(BPEntities.BELLOPHITE_SHIELD_WAVE.get(), FrostbiteMetalShieldWaveRender::new);
        event.registerEntityRenderer(BPEntities.GRYLYNEN_CORE_BOMB.get(), GrylynenCoreBombRender::new);
        event.registerEntityRenderer(BPEntities.ALPHANUM_SHARD.get(), AlphanumShardRender::new);

        event.registerEntityRenderer(BPEntities.BP_EFFECT.get(), BPEffectRender::new);
    }
    
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
    	event.registerLayerDefinition(NandbricArmorModel.LAYER_LOCATION, NandbricArmorModel::createBodyLayer);
    	event.registerLayerDefinition(ReinforcedFleignariteArmorModel.LAYER_LOCATION, ReinforcedFleignariteArmorModel::createBodyLayer);
    }
    
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {

        // Tile Entity
        BlockEntityRenderers.register(BPTileEntities.FLEIGNARITE_SPLOTCH.get(), FleignariteSplotchBlockRender::new);
        BlockEntityRenderers.register(BPTileEntities.BP_SIGN.get(), SignRenderer::new);

        // Automatic Entry
        for (RegistryObject<Block> block : BPBlocks.BLOCKS.getEntries()) {
            if (
                    block.get() instanceof DoorBlock || block.get() instanceof TrapDoorBlock ||
                    block.get() instanceof LeavesBlock || block.get() instanceof SignBlock ||
                    block.get() instanceof SaplingBlock || block.get() instanceof BPLanternPlantBlock ||
                    block.get() instanceof BPIdeFanBlock || block.get() instanceof GrowingPlantBodyBlock ||
                    block.get() instanceof GrowingPlantHeadBlock || block.get() instanceof BushBlock
            ) {
            	ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.cutout());
            }
        }

        ItemBlockRenderTypes.setRenderLayer(BPBlocks.REINFORCING_TABLE.get(), RenderType.cutout());

        // Plants
        ItemBlockRenderTypes.setRenderLayer(BPBlocks.FLEIGNARITE_REMAINS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BPBlocks.FLEIGNARITE_VINES_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BPBlocks.FLEIGNARITE_VINES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BPBlocks.FLEIGNARITE_SPLOTCH.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BPBlocks.KYRIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BPBlocks.KYRIA_BELINE.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BPBlocks.SOUL_SPROUTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BPBlocks.SOUL_TALL_GRASS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BPBlocks.LAVA_SPIRE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BPBlocks.WARPED_DANCER.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BPBlocks.IRION_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BPBlocks.IRION_TALL_GRASS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BPBlocks.AZURLIA.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BPBlocks.ARTAIRIUS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BPBlocks.FROSTEM.get(), RenderType.cutout());

        // Armor
        GeoArmorRenderer.registerArmorRenderer(AquChestplateItem.class, () -> new AquChestplateRender());

        MenuScreens.register(BPContainerTypes.REINFORCING_TABLE_CONTAINER.get(), ReinforcingTableScreen::new);

        BPKeybinds.register(event);
    }

    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        //FrostbiteMetal Shield
        ItemProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, entity, p_174633_) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("charged"), (itemStack, clientLevel, entity, p_174633_) -> entity != null && ((FrostbiteMetalShieldItem) itemStack.getItem()).getCorePoints() == 3 ? 1.0F : 0.0F);

        //Infernal Quarterstaff
        ItemProperties.register(BPItems.INFERNAL_QUARTERSTAFF.get(), new ResourceLocation("reverse"), (itemStack, clientLevel, entity, p_174633_) -> entity != null && InfernalQuarterstaffItem.isReversed(itemStack) ? 1.0F : 0.0F);

        //Arbitrary Ballista
        ItemProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, p_174633_) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return ArbitraryBallistaItem.isCharged(itemStack) ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float) ArbitraryBallistaItem.getChargeDuration(itemStack);
            }
        });
        ItemProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, p_174633_) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && !ArbitraryBallistaItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, p_174633_) -> livingEntity != null && ArbitraryBallistaItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("firework"), (itemStack, clientLevel, livingEntity, p_174633_) -> livingEntity != null && ArbitraryBallistaItem.isCharged(itemStack) && ArbitraryBallistaItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

        //Alphanum Obliterator
        ItemProperties.register(BPItems.ALPHANUM_OBLITERATOR.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, p_174633_) -> livingEntity != null && AlphanumObliteratorItem.isCharged(itemStack) ? 1.0F : 0.0F);

        //Grylynen Shields
        for (RegistryObject<Item> items : BPItems.ITEMS.getEntries()) {
            Item shields = items.get();
            if (shields instanceof GrylynenShieldBaseItem) {
                ItemProperties.register(shields, new ResourceLocation("blocking"), (itemStack, clientLevel, entity, p_174633_) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
            }
        }
    }
}
