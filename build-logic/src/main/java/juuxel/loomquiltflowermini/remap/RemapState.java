package juuxel.loomquiltflowermini.remap;

import org.gradle.api.attributes.Attribute;

public enum RemapState {
    UNTOUCHED, REMAPPED;

    public static final Attribute<RemapState> REMAP_STATE_ATTRIBUTE = Attribute.of("loom-quiltflower.remapState", RemapState.class);
}
