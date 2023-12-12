package io.github.bioplethora.gui.container;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.gui.recipe.ReinforcingRecipe;
import io.github.bioplethora.registry.BPContainerTypes;
import io.github.bioplethora.registry.BPRecipes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ReinforcingTableContainer extends AbstractReinforcingContainer {

    private final Level level;
    @Nullable
    private ReinforcingRecipe selectedRecipe;

    public ReinforcingTableContainer(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public ReinforcingTableContainer(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(BPContainerTypes.REINFORCING_TABLE_CONTAINER.get(), pContainerId, pPlayerInventory, pAccess);
        this.level = pPlayerInventory.player.level;
    }

    protected boolean mayPickup(Player pPlayer, boolean pHasStack) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level);
    }

    protected ItemStack onTake(Player pPlayer, ItemStack pInputItem) {
        pInputItem.onCraftedBy(pPlayer.level, pPlayer, pInputItem.getCount());

        pPlayer.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 0.75F, 1.0F);
        pPlayer.playSound(SoundEvents.ANVIL_USE, 0.75F, 1.5F);
        if (!pPlayer.level.isClientSide()) {
            ((ServerLevel) pPlayer.level).sendParticles(ParticleTypes.FIREWORK, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), 55, 0.65, 0.65, 0.65, 0.01);
        }

        this.resultSlots.awardUsedRecipes(pPlayer);
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.shrinkStackInSlot(2);
        this.access.execute((world, pos) -> world.levelEvent(1044, pos, 0));
        return pInputItem;
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        itemstack.shrink(1);
        this.inputSlots.setItem(pIndex, itemstack);
    }

    public void createResult() {
        List<ReinforcingRecipe> list = this.level.getRecipeManager().getRecipesFor(BPRecipes.REINFORCING, this.inputSlots, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }
    }

    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }
}
