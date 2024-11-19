# Connection

A wrapper around the Kotlin collections API(`kotlin.collections`) for more utilities

## Importing

Import `io.github.spacedvoid.connection.*` for convenience.
For implicit conversions (such as  shortening `snapshot().toMutableList()` into `toMutableList()`, import `io.github.spacedvoid.connection.implicit.*` as well.

The IDE might require additional configurations to preserve the star imports;
for example, in IntelliJ IDEA, add the imports above to `Editor - Code Style - Kotlin - Imports - Packages to Use Imports with '*'`.

## What is this?

This is yet another a collection API based on Kotlin; all collection types from the Java Collections Framework are supported.
However, the collections are strictly separated, based on their mutability, into 4 types:

1. Immutable collections: The collection is neither mutable nor self-mutating.
2. View collections: The collection is not mutable, but is(or might be) self-mutating.
3. Remove-only collections: The collections is both mutable and self-mutating, but only remove operations (such as `remove(element: T)`) are available.
4. Mutable collections: The collection is both mutable and self-mutating.

3 is for entry sets of maps; they typically do not allow addition of new entries, but they can be used elsewhere, such as a single-use task list.

### Nothing is optional

There are no such thing as *optional operations*; use a mutable collection at your own will.
And as an implementor, you either implement all operations or make your own type.

### Explicit is good

All overloads with the same extension method name are available for all collection types(mostly).

However, for some operations (like copying or making a mutable collection based on another) require *snapshotting* the collection.
Likewise, there are no iterators for self-mutating collections; they cannot detect concurrent modifications consistently.
This can be shortened by using the *implicit operations* in `io.github.spacedvoid.connection.implicit`;
however, the use of this package is not recommended for consistency.

### Wrapping the Java Collections Framework

There are no *synchronized* or *[type-safe](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collections.html#checkedCollection(java.util.Collection,java.lang.Class))* collections here.
Since this is just a wrapper around the collections, you can just do `new CopyOnWriteArrayList().asConnection()` or `Collections.checkedList(new ArrayList()).asConnection()`.

### Converting back to pure Kotlin/Java

You can always convert a collection from here to a `kotlin.collections.Collection` or its subtypes.
There is always an unsafe way(`Collection.kotlin`) for your taste, but there also are safe alternatives(`asKotlin()`).
