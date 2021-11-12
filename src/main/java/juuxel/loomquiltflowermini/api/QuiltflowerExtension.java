package juuxel.loomquiltflowermini.api;

import org.gradle.api.provider.Property;

/**
 * The {@code quiltflower} extension.
 */
public interface QuiltflowerExtension {
    /**
     * If true, Quiltflower will be added to the runtime classpath.
     * This is useful for debugging mixins with the {@code mixin.debug.decompile} system property.
     *
     * <p>The Quiltflower version will be the same as in the loom-quiltflower-mini version you're using.
     *
     * @return the property
     * @since 1.1.0
     */
    Property<Boolean> getAddToRuntimeClasspath();
}
