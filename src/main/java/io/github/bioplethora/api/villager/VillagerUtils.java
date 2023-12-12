package io.github.bioplethora.api.villager;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;

public class VillagerUtils {

    public static final int NOVICE = 1, APPRENTICE = 2, JOURNEYMAN = 3, EXPERT = 4, MASTER = 5;

    public static void addVillagerTrades(VillagerTradesEvent event, int level, VillagerTrades.ItemListing... trades) {
        for (VillagerTrades.ItemListing trade : trades) event.getTrades().get(level).add(trade);
    }

    public static void addVillagerTrades(VillagerTradesEvent event, int level, VillagerProfession profession, VillagerTrades.ItemListing... trades) {
        if (event.getType() == profession) addVillagerTrades(event, level, trades);
    }
}
