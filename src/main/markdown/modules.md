# Connection: quick how-to guide

Connection is not currently published to Maven, so you'll need to build the artifacts manually.

First, add Connection to your dependency list as:

```kotlin
dependencies {
	compileOnly("io.github.spacedvoid:connection-api:0.1.0")
}
```

If you need to run the application, add another dependency to the core jar:

```kotlin
dependencies {
	compileOnly("io.github.spacedvoid:connection-api:0.1.0")
	runtimeOnly("io.github.spacedvoid:connection-core:0.1.0")
}
```

Or, although *not recommended*, you can just depend on the core jar itself:

```kotlin
dependencies {
	implementation("io.github.spacedvoid:connection-core:0.1.0")
}
```

Then, add the following import to your IDE's _Packages to use import with *_ setting:

```kotlin
import io.github.spacedvoid.connection.*
```

Finally, just continue typing as usual;
just note that `List`, `Set`, and `Map` are purely immutable, and cannot be assigned from `MutableList`, `MutableSet`, or `MutableMap` instances.
Use collection types that end with `View` to assign both immutable and mutable collections, such as `ListView`, `SetView`, and `MapView`.

If you're from Java, you can still use sequenced and navigable collections.

For a more deep-dive into what Connection offers,
start with our <a href="./connection-collections/io.github.spacedvoid.connection/index.html">collection types and kinds</a>.

For compatibility with Kotlin's collection types, Connection has extensions to create adapters between the types.<br />
If the collection will be used as an immutable collection, use `.asConnection()`.<br />
If the collection will be used as a mutable collection, use `.asMutableConnection()`.<br />
If you don't know, or you'll just query the elements from the collection, use `.asViewConnection()`.<br />
Adapters for Connection to Kotlin's collections are always `.asKotlin()`.

For more information about these adapters and extension methods,
see our <a href="./connection-api/io.github.spacedvoid.connection/index.html">dedicated page</a>.

---

Because most collection type names clash with Kotlin's collection types,
you'll often need to manually select Connection's type from the autocompletion list.
This is because [star imports have lower priority than default imports](https://youtrack.jetbrains.com/issue/KT-4374);
see https://youtrack.jetbrains.com/issue/KT-40839 for the report of this behavior.

You can add the following types to your IDE's _Exclude from auto-import and completion_ list:

```
kotlin.collections.Collection
kotlin.collections.MutableCollection
kotlin.collections.List
kotlin.collections.MutableList
kotlin.collections.Set
kotlin.collections.MutableSet
kotlin.collections.Map
kotlin.collections.MutableMap
```

<details>
<summary>Spoiler: For Intellij IDEA users</summary>
This setting won't work because of <a href="https://youtrack.jetbrains.com/issue/KTIJ-12918">https://youtrack.jetbrains.com/issue/KTIJ-12918</a>:
sadly, you'll need to manually select from the autocompletion list each time you write the type.
</details>
