# loom-quiltflower-mini

A Loom addon that adds [Quiltflower](https://github.com/QuiltMC/Quiltflower) as a Loom decompiler
for Loom 0.10.

This is a separate plugin from the "real" loom-quiltflower because Loom 0.10 essentially removed
the decompiler API as it is used in loom-quiltflower. This plugin has less
configuration options but should still be completely fine for most users.

## Getting started

1. Add the Cotton maven repository to settings.gradle:
```diff
  pluginManagement {
      repositories {
          maven {
              name = 'Fabric'
              url = 'https://maven.fabricmc.net/'
          }
+         maven {
+             name = 'Cotton'
+             url = 'https://server.bbkr.space/artifactory/libs-release/'
+         }
          gradlePluginPortal()
      }
  }
```

2. Add loom-quiltflower to your plugins:
```diff
  plugins {
      id 'fabric-loom' version '0.10-SNAPSHOT'
+     id 'io.github.juuxel.loom-quiltflower-mini' version '1.2.1'
      id 'maven-publish'
  }
```

3. Instead of `genSources`, you can now use `genSourcesWithQuiltflower`.

## Configuration

### Runtime classpath

> Added in LQF*m* 1.1.0.

You can add Quiltflower to the runtime classpath with a single property
to use it for decompiling mixins:

```kotlin
quiltflower {
    addToRuntimeClasspath.set(true)
}
```

> You also need the system property `mixin.debug` or `mixin.debug.decompile` 
> in your run configurations.

### Decompiler options

The options passed to Quiltflower can be configured using
their three-letter IDs.

```groovy
genSourcesWithQuiltflower {
    // fake option: don't try at home
    options.put('abc', '123')
}
```

There are some Quiltflower-specific flags that are listed below.
Note that 1 means true/enabled and 0 means false/disabed.

| ID | Description | Default |
|----|-------------|----------|
| `isl` | Collapse single-line lambdas | 1 |
| `jvn` | Use JAD-style local variable naming from ForgeFlower | 0 |
| `pam` | Pattern matching support | 1 |
| `tlf` | Experimental fix for interactions between `try` and loops | 1 |

