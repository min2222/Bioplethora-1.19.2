package io.github.bioplethora.client;

import com.mojang.blaze3d.platform.ScreenManager;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.BPIdeFanBlock;
import io.github.bioplethora.blocks.BPLanternPlantBlock;
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
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.ItemModelsProperties;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BPClientRegister {

    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {

        Minecraft mcClient = Minecraft.getInstance();

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

        //Ecoharmless
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CUTTLEFISH.get(), CuttlefishEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ONOFISH.get(), OnofishRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TRIGGERFISH.get(), TriggerfishRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.SOUL_EURYDN.get(), EurydnEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.FIERY_EURYDN.get(), EurydnEntityRender::new);

        //Plethoneutral
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.PEAGUIN.get(), PeaguinEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.NANDBRI.get(), NandbriEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CAVERN_FLEIGNAR.get(), CavernFleignarEntityRender::new);

        //Dangerum
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHEM.get(), AlphemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GAUGALEM.get(), GaugalemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.DWARF_MOSSADILE.get(), DwarfMossadileEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TRAPJAW.get(), TrapjawEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TERRAITH.get(), TerraithEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.MYUTHINE.get(), MyuthineEntityRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WOODEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.STONE_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GOLDEN_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.IRON_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.DIAMOND_GRYLYNEN.get(), GrylynenEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.NETHERITE_GRYLYNEN.get(), GrylynenEntityRender::new);

        //Hellsent
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CREPHOXL.get(), CrephoxlEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.FROSTBITE_GOLEM.get(), FrostbiteGolemEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.SHACHATH.get(), ShachathEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.TELEMREYE.get(), TelemreyeEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.VOIDJAW.get(), VoidjawEntityRender::new);

        //Elderia
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALTYRUS.get(), AltyrusEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.MYLIOTHAN.get(), MyliothanEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHEM_KING.get(), AlphemKingEntityRender::new);

        //projectiles
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.MAGMA_BOMB.get(), (rendererManager) -> new SpriteRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WINDY_ESSENCE.get(), (rendererManager) -> new SpriteRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ABYSSAL_SCALES.get(), (rendererManager) -> new AbyssalScalesRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_CLUSTER.get(), FrostbiteMetalClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_ARROW.get(), FrostbiteMetalArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WINDBLAZE.get(), WindBlazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ULTIMATE_BELLOPHITE_CLUSTER.get(), UltimateFrostbiteMetalClusterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.WIND_ARROW.get(), WindArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CRYOBLAZE.get(), CryoblazeRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHANUM_OBLITERATOR_SPEAR.get(), AlphanumObliteratorSpearRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.VERMILION_BLADE_PROJECTILE.get(), (rendererManager) -> new VermilionBladeProjectileRender<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CRYEANUM_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.NETHERITE_OBSIDIAN_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ECHO_GAIDIUS.get(), (rendererManager) -> new GaidiusBaseRenderer<>(rendererManager, mcClient.getItemRenderer()));

        //others
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.CAERULWOOD_BOAT.get(), BPBoatRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ENIVILE_BOAT.get(), BPBoatRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.PRIMORDIAL_RING.get(), PrimordialRingEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALTYRUS_SUMMONING.get(), AltyrusSummoningRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BELLOPHITE_SHIELD_WAVE.get(), FrostbiteMetalShieldWaveRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.GRYLYNEN_CORE_BOMB.get(), GrylynenCoreBombRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BPEntities.ALPHANUM_SHARD.get(), AlphanumShardRender::new);

        RenderingRegistry.registerEntityRenderingHandler(BPEntities.BP_EFFECT.get(), BPEffectRender::new);

        ScreenManager.register(BPContainerTypes.REINFORCING_TABLE_CONTAINER.get(), ReinforcingTableScreen::new);

        BPKeybinds.register(event);
    }

    @SubscribeEvent
    public static void registerModels(final FMLClientSetupEvent event) {
        //FrostbiteMetal Shield
        ItemModelsProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemModelsProperties.register(BPItems.BELLOPHITE_SHIELD.get(), new ResourceLocation("charged"), (itemStack, clientLevel, entity) -> entity != null && ((FrostbiteMetalShieldItem) itemStack.getItem()).getCorePoints() == 3 ? 1.0F : 0.0F);

        //Infernal Quarterstaff
        ItemModelsProperties.register(BPItems.INFERNAL_QUARTERSTAFF.get(), new ResourceLocation("reverse"), (itemStack, clientLevel, entity) -> entity != null && InfernalQuarterstaffItem.isReversed(itemStack) ? 1.0F : 0.0F);

        //Arbitrary Ballista
        ItemModelsProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return ArbitraryBallistaItem.isCharged(itemStack) ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float) ArbitraryBallistaItem.getChargeDuration(itemStack);
            }
        });
        ItemModelsProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && !ArbitraryBallistaItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemModelsProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity) -> livingEntity != null && ArbitraryBallistaItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemModelsProperties.register(BPItems.ARBITRARY_BALLISTA.get(), new ResourceLocation("firework"), (itemStack, clientLevel, livingEntity) -> livingEntity != null && ArbitraryBallistaItem.isCharged(itemStack) && ArbitraryBallistaItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

        //Alphanum Obliterator
        ItemModelsProperties.register(BPItems.ALPHANUM_OBLITERATOR.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity) -> livingEntity != null && AlphanumObliteratorItem.isCharged(itemStack) ? 1.0F : 0.0F);

        //Grylynen Shields
        for (RegistryObject<Item> items : BPItems.ITEMS.getEntries()) {
            Item shields = items.get();
            if (shields instanceof GrylynenShieldBaseItem) {
                ItemModelsProperties.register(shields, new ResourceLocation("blocking"), (itemStack, clientLevel, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F);
            }
        }
    }
}
