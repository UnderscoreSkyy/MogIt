package underscore.skyy.mogit.utils;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import underscore.skyy.mogit.common.MogItContent;

import static underscore.skyy.mogit.MogIt.MOD_ID;

public final class MogItRegistry {

    public static void setup() {
        registerBlocks();
        registerItems();
    }

    private static void registerBlocks() {
        register(MogItContent.Blocks.TRANSMOGRIFICATION_TABLE, "transmogrification_table", MogItContent.Items.TRANSMOGRIFICATION_TABLE);
    }

    private static void registerItems() {
        register(MogItContent.Items.LIVING_MATTER, "living_matter");
    }

    private static void register(Item item, String name) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
    }

    private static void register(Block block, String name, BlockItem blockItem) {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
        if(blockItem != null) { register(blockItem, name); }
    }

}
