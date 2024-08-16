package io.github.bioplethora.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.container.AbstractReinforcingContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReinforcingTableScreen extends AbstractContainerScreen<AbstractReinforcingContainer> implements ContainerListener {
    private final ResourceLocation GUI = new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/reinforcing_table.png");

    public ReinforcingTableScreen(AbstractReinforcingContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    protected void subInit() {
    }

    protected void init() {
        super.init();
        this.subInit();
        this.menu.addSlotListener(this);
    }

    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
    }

    @Override
    public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI);
        int i = this.leftPos;
        int j = this.topPos;
        matrixStack.blit(GUI, i, j, 0, 0, this.getXSize(), this.getYSize());

        matrixStack.blit(GUI, i + 59, j + 20, 0, this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);

        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem() || this.menu.getSlot(2).hasItem()) && !this.menu.getSlot(3).hasItem()) {
        	matrixStack.blit(GUI, i + 99, j + 45, this.imageWidth, 0, 28, 21);
        }
    }

	@Override
	public void slotChanged(AbstractContainerMenu pContainerToSend, int pDataSlotIndex, ItemStack pStack) {
		
	}

	@Override
	public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
		
	}
}
