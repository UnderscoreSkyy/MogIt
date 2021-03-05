package underscore.skyy.mogit.common;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Rarity;
import underscore.skyy.mogit.common.blocks.TransmogrificationTableBlock;
import underscore.skyy.mogit.common.blocks.entities.TransmogrificationTableBlockEntity;
import underscore.skyy.mogit.common.items.LivingMatterItem;
import underscore.skyy.mogit.common.screens.TransmogrificationTableScreenHandler;

import static underscore.skyy.mogit.MogIt.ITEMGROUP;

public class MogItContent {

    private MogItContent() {
        // no-op
    }

    public static class Blocks {

        private Blocks() {
            // no-op
        }

        public static final Block TRANSMOGRIFICATION_TABLE;

        static {
            TRANSMOGRIFICATION_TABLE = new TransmogrificationTableBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.RED).requiresTool().strength(5.0f, 1200.0f));
        }
    }

    public static class BlockEntitiesTypes {
        private BlockEntitiesTypes() {
            // no-op
        }

        public static final BlockEntityType<TransmogrificationTableBlockEntity> TRANSMOGRIFICATION_TABLE;

        static {
            TRANSMOGRIFICATION_TABLE = BlockEntityType.Builder.create(TransmogrificationTableBlockEntity::new, Blocks.TRANSMOGRIFICATION_TABLE).build(null) ;
        }
    }

    public static class Items {
        private Items() {
            // no-op
        }

        private static final FabricItemSettings settings = new FabricItemSettings().group(ITEMGROUP);

        // Items
        public static final Item LIVING_MATTER;

        // BlockItem
        public static final BlockItem TRANSMOGRIFICATION_TABLE;

        static {
            LIVING_MATTER = new LivingMatterItem(settings.rarity(Rarity.RARE));
            TRANSMOGRIFICATION_TABLE = new BlockItem(Blocks.TRANSMOGRIFICATION_TABLE, settings.rarity(Rarity.EPIC).maxCount(1));
        }
    }

    public static class ScreenHandlerTypes {

        private ScreenHandlerTypes() {
            // no-op
        }

        @SuppressWarnings("PublicField")
        public static ScreenHandlerType<TransmogrificationTableScreenHandler> TRANSMOGRIFICATION_TABLE;

    }
}
