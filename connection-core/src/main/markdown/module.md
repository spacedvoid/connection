# Module connection-core

Contains implementation details for Connection.

# Package io.github.spacedvoid.connection.impl

Contains basic implementations for adapting Kotlin collections to each Connection types in [io.github.spacedvoid.connection].
Methods in the implementations simply delegate to the Kotlin collection(namely `kotlin`),
wrapping the collection argument with an [KotlinCollectionImpl][io.github.spacedvoid.connection.impl.kotlin.KotlinCollectionImpl] if needed.

Implementors may freely use the implementations in here (by inheritance or delegation) as a shortcut.

# Package io.github.spacedvoid.connection.impl.kotlin

Contains basic implementations for adapting Connection collections to Kotlin types.

Since Kotlin's collection kinds are narrower than Connection, most methods depend on the input Connection's dynamic kind (via `is`).

Unlike Connection type implementations, the classes here are **not** recommended for public use.

### Warning:

Do not assume mutability of collections from non-Kotlin collections, such as [KotlinSequencedCollectionImpl].
Treat them as if they were [java.util.Collection]; they are mutable if the source collection is mutable.
Mutable collections do **not** imply the source collection is mutable.

Also, not all Kotlin collection implementations have an expected inheritance tree.
For example, [KotlinListImpl] does not inherit from [KotlinSequencedCollectionImpl].
This is because the optional operations will be exposed even if they are clearly unsupported.
Do **not** check for instances against Kotlin collection implementations.

# Package io.github.spacedvoid.connection.utils

Contains some utilities for the implementations.
