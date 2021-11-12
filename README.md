# loom-quiltflower-mini

A Loom addon that adds [Quiltflower](https://github.com/QuiltMC/Quiltflower) as a Loom decompiler
for Loom 0.10.

This is a separate plugin from the "real" loom-quiltflower because Loom 0.10 essentially removed
the decompiler API. There's only a small shell of the original API left which means I needed to make
an unconfigurable version of LQF to support 0.10.

## Usage

loom-quiltflower-mini is available in the Cotton maven repository
(see instructions on [the main repo](https://github.com/Juuxel/LoomQuiltflower/)).
If/when approved, it'll also be available on the Gradle plugin portal.

```kotlin
plugins {
    id("io.github.juuxel.loom-quiltflower-mini") version "1.1.0"
}
```

You can generate the sources by using the `genSourcesWithQuiltflower` task.

**New in 1.1!**

You can add Quiltflower to the runtime classpath with a single property
to use it for decompiling mixins:

```kotlin
quiltflower {
    addToRuntimeClasspath.set(true)
}
```

> You also need the system property `mixin.debug` or `mixin.debug.decompile` 
> in your run configurations.
