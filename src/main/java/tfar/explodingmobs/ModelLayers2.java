package tfar.explodingmobs;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import tfar.explodingmobs.mixin.ModelLayersAccess;

public class ModelLayers2 {

    public static final ModelLayerLocation COW_POWER = register("cow","power");

    private static ModelLayerLocation register(String string) {
        return register(string, "main");
    }

    private static ModelLayerLocation register(String string, String string2) {
        ModelLayerLocation modelLayerLocation = createLocation(string, string2);
        if (!ModelLayersAccess.getALL_MODELS().add(modelLayerLocation)) {
            throw new IllegalStateException("Duplicate registration for " + modelLayerLocation);
        } else {
            return modelLayerLocation;
        }
    }

    private static ModelLayerLocation createLocation(String string, String string2) {
        return new ModelLayerLocation(new ResourceLocation(ExplodingMobs.MODID, string), string2);
    }

}
