package tfar.explodingmobs.mixin;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.animal.Cow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.explodingmobs.CowPowerLayer;

@Mixin(CowRenderer.class)
abstract class CowRendererMixin extends MobRenderer<Cow, CowModel<Cow>> {

    public CowRendererMixin(EntityRendererProvider.Context context, CowModel<Cow> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
    private void addPowerLayer(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.addLayer(new CowPowerLayer(this, context.getModelSet()));
    }
}
