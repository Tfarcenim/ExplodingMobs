package tfar.explodingmobs.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tfar.explodingmobs.LayerDefinitions2;
import tfar.explodingmobs.ModelLayers2;

import java.util.Map;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {

    @Inject(method = "createRoots",at = @At(value = "INVOKE",target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"),locals = LocalCapture.CAPTURE_FAILHARD)
    private static void preScale(CallbackInfoReturnable<Map<ModelLayerLocation, LayerDefinition>> cir, ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder) {
        builder.put(ModelLayers2.COW_POWER, LayerDefinitions2.createCowBodyLayer(new CubeDeformation(2)));
    }
}
