package io.github.bioplethora.gui.container;



import javax.annotation.Nullable;

import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractReinforcingContainer extends AbstractContainerMenu {

    protected final ResultContainer resultSlots = new ResultContainer();
    protected final Container inputSlots = new SimpleContainer(3) {
        public void setChanged() {
            super.setChanged();
            AbstractReinforcingContainer.this.slotsChanged(this);
        }
    };
    protected final ContainerLevelAccess access;
    protected final Player player;

    protected abstract boolean mayPickup(Player pPlayer, boolean pHasStack);

    protected abstract ItemStack onTake(Player pPlayer, ItemStack pInputItem);

    public AbstractReinforcingContainer(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(pType, pContainerId);
        this.access = pAccess;
        this.player = pPlayerInventory.player;

        addSlot(new Slot(this.inputSlots, 0, 44, 49 - 32));
        addSlot(new Slot(this.inputSlots, 1, 44, 67 - 32));

        addSlot(new Slot(this.inputSlots, 2, 44, 85 - 32));

        addSlot(new Slot(this.resultSlots, 3, 127, 37) {
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            public boolean mayPickup(Player pPlayer) {
                return AbstractReinforcingContainer.this.mayPickup(pPlayer, this.hasItem());
            }

            public void onTake(Player pPlayer, ItemStack pStack) {
                AbstractReinforcingContainer.this.onTake(pPlayer, pStack);
            }
        });

        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(pPlayerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for(int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(pPlayerInventory, col, 8 + col * 18, 142));
        }
    }

    public abstract void createResult();

    public void slotsChanged(Container pInventory) {
        super.slotsChanged(pInventory);
        if (pInventory == this.inputSlots) {
            this.createResult();
        }

    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((world, pos) -> this.clearContainer(pPlayer, this.inputSlots));
    }

    public boolean stillValid(Player pPlayer) {
        return stillValid(access, player, BPBlocks.REINFORCING_TABLE.get());
    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot sourceSlot = slots.get(index);

        if (sourceSlot != null && sourceSlot.hasItem()) {
            ItemStack itemstack1 = sourceSlot.getItem();
            itemstack = itemstack1.copy();
            if (index == 3) {
                if (!this.moveItemStackTo(itemstack1, 4, 39, true)) {
                    return ItemStack.EMPTY;
                }

                sourceSlot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0 && index != 1 && index != 2) {
                if (index >= 4 && index < 39) {
                    int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
                    if (!this.moveItemStackTo(itemstack1, i, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                sourceSlot.set(ItemStack.EMPTY);
            } else {
                sourceSlot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            sourceSlot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}