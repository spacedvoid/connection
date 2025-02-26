# Connection

Another collections API for more utilities

## What is this?

This is yet another a collection API based on Kotlin.
However, the collections are strictly separated into 4 kinds, based on their mutability:

1. Immutable collections: The collection is neither mutable nor self-mutating.
2. View collections: The collection is not mutable, but is(or might be) self-mutating.
3. Remove-only collections: The collections is both mutable and self-mutating, but only removal operations (such as `remove(element: T)`) are available.
4. Mutable collections: The collection is both mutable and self-mutating.

3 is for entry collections of maps; they typically do not allow addition of new entries, only removals.

There are no *optional operations*; use a mutable collection at your own will.

## Git structure

This library uses the [Tip & Tail Model](https://openjdk.org/jeps/14) experimentally.
As specified in the [description](https://openjdk.org/jeps/14#Backport-as-little-as-possible) of the model, 
the specific rules of the model are modified, listed below.

The `main` branch is for new features, bug fixes, and all other changes.
This is the *Tip* branch; no releases come from this branch.

For every new major or minor version release, a new branch is created from the tip branch with the format `v<version>`.
These branches are the *Tail* branches; all releases must come from tail branches.
Releases must have an associated commit tagged with the release version number, containing content equal to the release.
All releases must be unique; to improve searching specific versions, there must be no duplicate release tags with the same version.

Changing the contents of a tail branch is restricted.
New features, API/implementation changes, minor bug fixes that change the API/implementation behavior are prohibited.
Only critical bug fixes that impact security or API usage(such as infinite loops or specification violations) are allowed.
Therefore, releases from a tail branch must not increment the major or minor version number.

Development releases(also known as pre-releases) are not in part of these rules.
As development releases are not really meant for production usages, 
they can also be released on the tip branch, without creating the associated tail branch.
However, the tag rule still applies; a non-duplicate tag named with the version number must be associated with a commit.

## Importing

The main package for the API is `io.github.spacedvoid.connection`.
Although you can import everything one by one, since there are a lot of extension methods here,
just star-importing the package might be preferable.

The IDE might require additional configurations to preserve the star imports.
In IntelliJ IDEA, add the imports above to `Editor - Code Style - Kotlin - Imports - Packages to Use Imports with '*'`.
Make sure to uncheck `With Subpackages`, which contain implementations or unsafe features that you might not want to use.

If the names of the collections are resolved to Kotlin's collections even if the star import was added,
you might need to manually add imports for such types.
This is because [star imports have lower priority than default imports](https://youtrack.jetbrains.com/issue/KT-4374).
See https://youtrack.jetbrains.com/issue/KT-40839 for the report of this behavior.

## Upcoming features (or just a todo list)

- [ ] Implementations using `java.lang.reflect.Proxy`
- [ ] Simpler ways to express intervals for `subList` and `subSet`
- [x] `equals` and `hashCode` implementations
- [ ] `Spliterator` support
- [ ] Special collections, such as stack, queue, and deque
- [ ] Operator and utility methods
- [ ] Completely migrate away from `kotlin.collections`, making this independent with the Java Collections Framework

## License

The source code is licensed with [MPL-2.0](LICENSE), *compatible* with secondary licenses.
