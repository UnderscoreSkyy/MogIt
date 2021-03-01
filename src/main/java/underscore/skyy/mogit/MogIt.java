package underscore.skyy.mogit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import underscore.skyy.mogit.common.MogItContent;
import underscore.skyy.mogit.utils.MogItRegistry;

/**
 * Main class for Mog It mod.
 */
public class MogIt implements ModInitializer {

    // Global Constants
    public static final String MOD_ID = "mog_it";
    public static final String MOD_NAME = "Mog it";
    public static MogIt INSTANCE;

    public static ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "item_group"),
            () -> new ItemStack(MogItContent.Items.LIVING_MATTER));

    private static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        INSTANCE = this;

        MogItRegistry.setup();
        LOGGER.info(MOD_NAME.concat(" have been initialized."));
        LOGGER.info("\"I foresee a mutually beneficial transaction.\"");
    }
}
