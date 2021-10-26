package juuxel.loomquiltflowermini;

import juuxel.loomquiltflowermini.impl.QuiltflowerDecompiler;
import net.fabricmc.loom.api.LoomGradleExtensionAPI;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class LqfMiniPlugin implements Plugin<Project> {
    private static final List<String> LOOMS = Arrays.asList("fabric-loom", "dev.architectury.loom");
    private boolean applied = false;

    @Override
    public void apply(@NotNull Project target) {
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
        });
    }
}
