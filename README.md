# JVM Libraries

A series of libraries for the java virtual machine. These libraries seek to help the developer to use little code for boring
operations and repetitive code.

At the moment the libraries are in an early phase and functionality will be added over time. Below is a list of the available
libraries and their respective sections.

### Libraries

- [JVM Utilities](https://github.com/Ushiosan23/jvm_libs/tree/main/jvm_utilities)
- [Swing Utilities](https://github.com/Ushiosan23/jvm_libs/tree/main/swing_utilities)

### How work with them?

Each of the libraries has its own maven package to add to the projects you want. All you have to do is add the following
instructions to your project.

The artifacts mentioned above have the same names as the project directories to avoid confusion.

#### Maven Project

```xml

<dependencies>
	<dependency>
		<groupId>com.github.ushiosan23</groupId>
		<artifactId>library_name</artifactId>
		<version>x.x.x</version>
	</dependency>
</dependencies>
```

#### Gradle Project

- Groovy DSL

```groovy
dependencies {
	implementation "com.github.ushiosan23:library_name:x.x.x"
}
```

- Kotlin DSL

```kotlin
dependencies {
	implementation("com.github.ushiosan23:library_name:x.x.x")
}
```
