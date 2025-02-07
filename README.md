# Connection

Another collections API for more utilities

## What is this?

This is yet another a collection API based on Kotlin.
However, the collections are strictly separated into 4 kinds, based on their mutability:

1. Immutable collections: The collection is neither mutable nor self-mutating.
2. View collections: The collection is not mutable, but is(or might be) self-mutating.
3. Remove-only collections: The collections is both mutable and self-mutating, but only removal operations (such as `remove(element: T)`) are available.
4. Mutable collections: The collection is both mutable and self-mutating.

3 is for entry collections of maps; they typically do not allow addition of new entries, but only removals.

There are no such thing as *optional operations*; use a mutable collection at your own will.
And as an implementor, you either implement all operations or make your own type.

## Importing

The main package for the api is `io.github.spacedvoid.connection`.
Although you can import everything one by one, since there are a lot of extension methods here,
just star-importing the package might be preferable.

The IDE might require additional configurations to preserve the star imports.
In IntelliJ IDEA, add the imports above to `Editor - Code Style - Kotlin - Imports - Packages to Use Imports with '*'`.
Make sure to uncheck `With Subpackages`, which contain implementations or unsafe features that you might not want to use.

## Upcoming features (or just a todo list)

- [ ] Implementations using `java.lang.reflect.Proxy`
- [ ] Simpler ways to express intervals for `subList` and `subSet`
- [ ] `equals` and `hashCode` implementations
- [ ] `Spliterator` support
- [ ] Special collections, such as stack, queue, and deque
- [ ] Operator and utility methods
- [ ] Completely migrate away from `kotlin.collections`, making this independent with the Java Collections Framework

## License

The source code is licensed with [MPL-2.0](LICENSE), *compatible* with secondary licenses.
