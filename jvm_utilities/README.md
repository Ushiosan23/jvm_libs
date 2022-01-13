# JVM Utilities

Utilities for the java virtual machine. It only contains utilities to avoid repetitive code and does not contain functionality
from any specific software or library.

[![javadoc](https://javadoc.io/badge2/com.github.ushiosan23/jvm_utilities/jvm__utilities.svg?logo=java)](https://javadoc.io/doc/com.github.ushiosan23/jvm_utilities)

It also adds a new functionalities listed below:

- Base
	- Extra functionality for existing objects such as numbers, strings, mathematics, etc.
- System
	- Functionality was added to detect the platform where the virtual machine is running
	- It also adds functionality for the platform architecture
- Collections
	- Collections such as matrices and maps are easier to create, through generator functions.
- IO
	- Work with recursive file listing is simplified.
	- The use of the "java.nio" API is encouraged instead of the "java.io" API.
	- Obtain information from files in a simpler way.

## How to use it

#### Maven project

```xml

<dependencies>
	<dependency>
		<groupId>com.github.ushiosan23</groupId>
		<artifactId>jvm_utilities</artifactId>
		<version>x.x.x</version>
	</dependency>
</dependencies>
```

#### Gradle project

- Groovy DSL

```groovy
dependencies {
	implementation "com.github.ushiosan23:jvm_utilities:x.x.x"
}
```

- Kotlin DSL

```kotlin
dependencies {
	implementation("com.github.ushiosan23:jvm_utilities:x.x.x")
}
```
