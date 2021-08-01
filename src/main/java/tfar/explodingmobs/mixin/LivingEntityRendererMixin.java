package tfar.explodingmobs.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.explodingmobs.MobDuck;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {

    @Inject(method = "render",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
    private void preScale(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (!(livingEntity instanceof Creeper) && livingEntity instanceof Mob) {
            float m = ((MobDuck)livingEntity).$getSwelling(f);
            float h = 1.0F + Mth.sin(m * 100.0F) * m * 0.01F;
            m = Mth.clamp(m, 0.0F, 1.0F);
            m *= m;
            m *= m;
            float n = (1.0F + m * 0.4F) * h;
            float height = (1.0F + m * 0.1F) / h;
            poseStack.scale(n, height, n);
        }
    }
}
