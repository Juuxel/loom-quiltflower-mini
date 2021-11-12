package juuxel.loomquiltflowermini.remap;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.attributes.HasConfigurableAttributes;

import java.util.Map;

public class LqfMiniLogicExtension {
    private final Project project;

    public LqfMiniLogicExtension(Project project) {
        this.project = project;
    }

    public Dependency quiltflower() {
        Dependency dep = project.getDependencies().create("org.quiltmc:quiltflower:" + project.getRootProject().property("quiltflower-version"));
        ((HasConfigurableAttributes<?>) dep).attributes(attributes -> attributes.attribute(RemapState.REMAP_STATE_ATTRIBUTE, RemapState.REMAPPED));
        ((ModuleDependency) dep).exclude(Map.of("group", "org.jetbrains.kotlin"));
        return dep;
    }
}
