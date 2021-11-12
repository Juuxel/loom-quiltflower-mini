package juuxel.loomquiltflowermini;

import juuxel.loomquiltflowermini.api.QuiltflowerExtension;
import juuxel.loomquiltflowermini.impl.Constants;
import juuxel.loomquiltflowermini.impl.QuiltflowerDecompiler;
import juuxel.loomquiltflowermini.impl.QuiltflowerExtensionImpl;
import net.fabricmc.loom.api.LoomGradleExtensionAPI;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ModuleDependency;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LqfMiniPlugin implements Plugin<Project> {
    private static final List<String> LOOMS = Arrays.asList("fabric-loom", "dev.architectury.loom");
    private boolean applied = false;

    @Override
    public void apply(@NotNull Project target) {
        var extension = target.getExtensions().create(QuiltflowerExtension.class, "quiltflower", QuiltflowerExtensionImpl.class, target);

        for (String loomId : LOOMS) {
            target.getPluginManager().withPlugin(loomId, plugin -> {
                ((LoomGradleExtensionAPI) target.getExtensions().getByName("loom")).addDecompiler(new QuiltflowerDecompiler());
                applied = true;
            });
        }

        target.afterEvaluate(p -> {
            if (!applied) {
                throw new GradleException("loom-quiltflower requires Loom! (One of " + LOOMS + ")");
            }

            extension.getAddToRuntimeClasspath().finalizeValue();

            if (extension.getAddToRuntimeClasspath().get()) {
                p.getRepositories().maven(repository -> {
                    repository.setName("Quilt releases (loom-quiltflower)");
                    repository.setUrl(Constants.QUILT_MAVEN);
                });

                if (p.getConfigurations().findByName("localRuntime") == null) {
                    throw new GradleException("Using quiltflower.addToRuntimeClasspath requires a newer version of Loom with 'localRuntime' support!");
                } else {
                    Dependency dep = p.getDependencies().add("localRuntime", Constants.QUILTFLOWER_DEPENDENCY);

                    // TODO: Remove this when updating QF
                    ((ModuleDependency) dep).exclude(Map.of("group", "org.jetbrains.kotlin"));
                }
            }
        });
    }
}
