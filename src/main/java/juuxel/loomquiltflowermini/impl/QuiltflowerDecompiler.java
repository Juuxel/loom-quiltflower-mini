package juuxel.loomquiltflowermini.impl;

import juuxel.loomquiltflowermini.impl.bridge.QfResultSaver;
import juuxel.loomquiltflowermini.impl.bridge.QfThreadIdLogger;
import juuxel.loomquiltflowermini.impl.bridge.QfTinyJavadocProvider;
import juuxel.loomquiltflowermini.impl.relocated.quiltflower.main.Fernflower;
import juuxel.loomquiltflowermini.impl.relocated.quiltflower.main.extern.IFernflowerPreferences;
import juuxel.loomquiltflowermini.impl.relocated.quiltflowerapi.IFabricJavadocProvider;
import net.fabricmc.loom.api.decompilers.DecompilationMetadata;
import net.fabricmc.loom.api.decompilers.LoomDecompiler;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class QuiltflowerDecompiler implements LoomDecompiler {
    public QuiltflowerDecompiler() {
    }

    @Override
    public String name() {
        return "Quiltflower";
    }

    @Override
    public void decompile(Path compiledJar, Path sourcesDestination, Path linemapDestination, DecompilationMetadata metaData) {
        Map<String, Object> options = new HashMap<>();
        options.put(IFernflowerPreferences.INDENT_STRING, " ".repeat(4)); // space supremacy!
        options.put(IFernflowerPreferences.DECOMPILE_GENERIC_SIGNATURES, "1");
        options.put(IFernflowerPreferences.BYTECODE_SOURCE_MAPPING, "1");
        options.put(IFernflowerPreferences.REMOVE_SYNTHETIC, "1");
        options.put(IFernflowerPreferences.LOG_LEVEL, "trace");
        options.put(IFernflowerPreferences.THREADS, Integer.toString(metaData.numberOfThreads()));
        options.put(IFabricJavadocProvider.PROPERTY_NAME, new QfTinyJavadocProvider(metaData.javaDocs().toFile()));

        // Experimental QF preferences
        options.put(IFernflowerPreferences.PATTERN_MATCHING, "1");
        options.put(IFernflowerPreferences.EXPERIMENTAL_TRY_LOOP_FIX, "1");
        options.putAll(ReflectionUtil.<Map<String, String>>maybeGetFieldOrRecordComponent(metaData, "options").orElse(Map.of()));

        Fernflower ff = new Fernflower(Zips::getBytes, new QfResultSaver(sourcesDestination::toFile, linemapDestination::toFile), options, new QfThreadIdLogger());

        for (Path library : metaData.libraries()) {
            ff.addLibrary(library.toFile());
        }

        ff.addSource(compiledJar.toFile());
        ff.decompileContext();
    }
}
