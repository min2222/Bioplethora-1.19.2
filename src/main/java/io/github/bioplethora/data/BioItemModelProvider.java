package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraBlocks;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Collection;

public class BioItemModelProvider extends ItemModelProvider {

    public BioItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Bioplethora.MOD_ID, existingFileHelper);
    }

    /**
     * Automatically generates item and block item models without existing model files.
     */
    @Override
    public void registerModels() {
        this.defaultItem(BioplethoraItems.ITEMS.getEntries());
        this.defaultBlock(BioplethoraBlocks.BLOCK_ITEMS.getEntries());
    }

    @Nonnull
    @Override
    public String getName() {
        return Bioplethora.MOD_NAME + " Item models";
    }

    public ModelFile.ExistingModelFile getMcLoc(String mcModel) {
        return getExistingFile(mcLoc(mcModel));
    }

    /**
     * If Item is ToolItem or SwordItem, minecraft/handheld model will be generated for that item.
     * Otherwise, minecraft/generated model will be generated for that item.
     */
    public void defaultItem(Collection<RegistryObject<Item>> items) {

        for (RegistryObject<Item> item : items) {
            String name = item.getId().getPath();
            Item getItem = item.get();
            ResourceLocation datagenLoc = new ResourceLocation(Bioplethora.MOD_ID, "item/" + name);

            ModelFile.ExistingModelFile modelType = getItem instanceof ToolItem || getItem instanceof SwordItem ?
                    getMcLoc("item/handheld") : getMcLoc("item/generated");

            if (!existingFileHelper.exists(datagenLoc, TEXTURE) || existingFileHelper.exists(datagenLoc, MODEL))
                continue;

            this.getBuilder(name).parent(modelType).texture("layer0", ITEM_FOLDER + "/" + name);
            Bioplethora.LOGGER.info("Generate Item Successful: " + item.getId());
        }
    }

    public void defaultBlock(Collection<RegistryObject<Item>> blockItems) {
        for (RegistryObject<Item> blockItem : blockItems) {
            String name = blockItem.getId().getPath();
            ResourceLocation itemLoc = new ResourceLocation(Bioplethora.MOD_ID, "item/" + name);
            ResourceLocation blockLoc = new ResourceLocation(Bioplethora.MOD_ID, "block/" + name);

            if (!existingFileHelper.exists(blockLoc, MODEL) || existingFileHelper.exists(itemLoc, MODEL))
                continue;

            this.withExistingParent(name, blockLoc);
            Bioplethora.LOGGER.info("Generate Block Item Successful: " + blockItem.getId());
        }
    }
}
