package juuxel.loomquiltflowermini.impl;

import juuxel.loomquiltflowermini.api.QuiltflowerExtension;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

public class QuiltflowerExtensionImpl implements QuiltflowerExtension {
    private final Property<Boolean> addToRuntimeClasspath;

    @Inject
    public QuiltflowerExtensionImpl(Project project) {
        this.addToRuntimeClasspath = project.getObjects().property(Boolean.class).convention(false);
    }

    @Override
    public Property<Boolean> getAddToRuntimeClasspath() {
        return addToRuntimeClasspath;
    }
}
