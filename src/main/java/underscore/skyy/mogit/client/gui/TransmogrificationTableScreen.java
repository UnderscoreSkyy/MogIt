package underscore.skyy.mogit.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import underscore.skyy.mogit.MogIt;

public class TransmogrificationTableScreen extends HandledScreen<ScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(MogIt.MOD_ID, "textures/gui/transmogrification_table.png");

    public TransmogrificationTableScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        // Setup Screen
        this.backgroundWidth = 276;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(TEXTURE);
        int x = this.x;
        int y = this.y;

        drawTexture(matrices, x, y, getZOffset(),0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 512);
        drawPlayer(x + 62, y + 135, 57);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = 5;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
    }

    private void drawPlayer(int x, int y, int size){

        if(this.client != null && this.client.player != null) {
            LivingEntity entity = this.client.player;

            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)x, (float)y, 1050.0F);
            RenderSystem.scalef(1.0F, 1.0F, -1.0F);

            MatrixStack matrixStack = new MatrixStack();
            matrixStack.translate(0.0D, 0.0D, 1000.0D);
            matrixStack.scale((float)size, (float)size, (float)size);

            Quaternion quaternion = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
            Quaternion quaternion2 = Vector3f.POSITIVE_X.getDegreesQuaternion(0.0F);
            quaternion.hamiltonProduct(quaternion2);
            matrixStack.multiply(quaternion);

            float h = entity.bodyYaw;
            float i = entity.yaw;
            float j = entity.pitch;
            float k = entity.prevHeadYaw;
            float l = entity.headYaw;

            entity.bodyYaw = 180.0F;
            entity.yaw = 180.0F;
            entity.pitch = -5.0F;
            entity.headYaw = entity.yaw;
            entity.prevHeadYaw = entity.yaw;

            EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
            quaternion2.conjugate();
            entityRenderDispatcher.setRotation(quaternion2);
            entityRenderDispatcher.setRenderShadows(false);

            VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
            RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack, immediate, 15728880));
            immediate.draw();
            entityRenderDispatcher.setRenderShadows(true);
            entity.bodyYaw = h;
            entity.yaw = i;
            entity.pitch = j;
            entity.prevHeadYaw = k;
            entity.headYaw = l;
            RenderSystem.popMatrix();
        }
    }
}
