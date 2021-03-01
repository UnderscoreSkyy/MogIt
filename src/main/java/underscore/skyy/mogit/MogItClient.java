package underscore.skyy.mogit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import underscore.skyy.mogit.utils.MogItRegistry;

import static underscore.skyy.mogit.MogIt.MOD_NAME;


/**
 * Main client class of Mog It mod
 */
@Environment(EnvType.CLIENT)
public class MogItClient implements ClientModInitializer {

    private static final Logger LOGGER = LogManager.getLogger(MOD_NAME.concat(" | Client"));
    @Override
    public void onInitializeClient() {
        MogItRegistry.setupClient();
    }
}
