package io.github.bioplethora.item;

import javax.annotation.Nonnull;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

public class BiopediaItem extends Item {

    public BiopediaItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerIn, InteractionHand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);

        if (ModList.get().isLoaded("patchouli")) {
            if (!world.isClientSide()) {

                if (playerIn instanceof ServerPlayer) {
                    PatchouliAPI.get().openBookGUI((ServerPlayer) playerIn, ForgeRegistries.ITEMS.getKey(this));
                }
            }
            return InteractionResultHolder.success(stack);
        } else {
            playerIn.displayClientMessage(Component.translatable("message.bioplethora.biopedia.patchouli_notif")
                            .withStyle(ChatFormatting.WHITE)
                            .withStyle(ChatFormatting.BOLD)
                            .withStyle(ChatFormatting.ITALIC),
                    true
            );
            return InteractionResultHolder.pass(stack);
        }
    }
}
