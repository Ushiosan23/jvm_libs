package ushiosan.jvm.internal.print;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ushiosan.jvm.UClass;
import ushiosan.jvm.UObject;
import ushiosan.jvm.collections.UArray;
import ushiosan.jvm.collections.USet;
import ushiosan.jvm.content.UPair;
import ushiosan.jvm.function.UFun;
import ushiosan.jvm.internal.print.components.*;
import ushiosan.jvm.print.UToStringComponent;
import ushiosan.jvm.print.UToStringManager;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public final class UToStringManagerImpl implements UToStringManager {
	
	/* -----------------------------------------------------
	 * Properties
	 * ----------------------------------------------------- */
	
	/**
	 * Constant that contains the functions for the conversion of special cases.
	 */
	private static final UPair<UFun.UFun1<Boolean, Object>, UFun.UFun2<String, Object, Boolean>>[] SPECIAL_CASES = UArray.make(
		// Checks that the object is a valid null value and
		// returns its text representation
		UPair.make(Objects::isNull, (it, ignore) -> "<null>"),
		// Primitive formats like Boolean types have reserved words that may not be identified when read.
		// That is why they are encapsulated inside "<>" to determine that it is a type and not a word within a text.
		UPair.make(it -> UObject.canCast(it, Boolean.class), (it, ignore) -> String.format("<%s>", it)),
		// Checks that the object is a valid object or primitive
		// and returns its text representation. The only thing that
		// changes is the Char type, which is represented by single quotes.
		UPair.make(UClass::isPrimitive,
				   (it, ignore) -> String.format(UObject.canCast(it, Character.class) ? "'%s'" : "%s", it)),
		// Since the Object class is the base of every object in Java. It is also
		// what causes it to be possible to cast to any type (even if that action
		// is invalidated), and it is for this reason that it is considered a
		// special case and only a generic text representation is generated.
		UPair.make(it -> it.getClass() == Object.class, UGeneralComponent.getInstance()::toString));
	
	/**
	 * Object instance
	 */
	private volatile static UToStringManagerImpl INSTANCE;
	/**
	 * Default components that cannot be removed or modified.
	 */
	private final Class<? extends UToStringComponent>[] noEditableComponents;
	/**
	 * All items registered within the instance
	 */
	private final Set<Class<? extends UToStringComponent>> registeredTypes;
	/**
	 * All print components
	 */
	private UToStringComponent[] components;
	
	/* -----------------------------------------------------
	 * Constructors
	 * ----------------------------------------------------- */
	
	/**
	 * This class cannot be instantiated
	 */
	private UToStringManagerImpl() {
		// Initialize properties
		components = UArray.make(
			UCollectionComponent.getInstance(),
			UEntryComponent.getInstance(),
			UArrayComponent.getInstance(),
			UThrowableComponent.getInstance(),
			UGeneralComponent.getInstance());
		noEditableComponents = UObject.cast(UClass.toVarargTypes((Object[]) components));
		registeredTypes = USet.makeMutable(noEditableComponents);
	}
	
	/* -----------------------------------------------------
	 * Methods
	 * ----------------------------------------------------- */
	
	/**
	 * Generates an instance of the specified class and saves it so that only one
	 * instance exists throughout the entire program. If the JVM deletes such an
	 * instance, it is recreated.
	 *
	 * @return the object instance
	 */
	public static synchronized @NotNull UToStringManager getInstance() {
		if (UObject.isNull(INSTANCE)) {
			INSTANCE = new UToStringManagerImpl();
		}
		return INSTANCE;
	}
	
	/**
	 * Method used to filter a component. Validates that the component has the specific
	 * class to convert it into plain text.
	 *
	 * @param object    the object to convert
	 * @param component the component instance
	 * @return {@code true} if the component can process the object or {@code false} otherwise
	 */
	private static boolean checkComponent(@NotNull Object object, @NotNull UToStringComponent component) {
		// When the components are named as null.
		// It means that it has the ability to handle any type of data,
		// but this only applies when it comes to arrays and not unique data.
		if (component.arraysOnly() && component.supportedElements() == null) return true;
		
		// It is done as follows because the same value has to be
		// returned two or more times.
		// With tags, we avoid making duplicate callbacks.
		// The only drawback is that the code can be less clear.
		check:
		{
			if (component.supportedElements() == null) break check;
			// Iterate all supported classes
			for (var supportClass : component.supportedElements()) {
				if (UObject.canCastNotNull(object, supportClass)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Generates a text with the representation of the object.
	 * Very similar to what the {@link Object#toString()} method does, but it ensures
	 * that all objects have an easily identifiable representation.
	 *
	 * @param object  the object that you want to get the text representation
	 * @param verbose option used to determine if the output will be long or simple
	 * @return object string representation
	 */
	@SuppressWarnings("DataFlowIssue")
	public @NotNull String toString(@Nullable Object object, boolean verbose) {
		// Check the special cases
		for (var conversion : SPECIAL_CASES) {
			if (conversion.first.invoke(object)) {
				return conversion.second.invoke(object, verbose);
			}
		}
		
		// The compiler will determine that there is a possibility that
		// an exception to type "NullPointerException" will be thrown.
		// But it is not like that, because it was previously validated in special cases.
		Class<?> cls = object.getClass();
		var component = Arrays.stream(components)
			.filter(it -> it.arraysOnly() == cls.isArray())
			.filter(it -> checkComponent(object, it))
			.findFirst();
		
		// Check if component exists
		return component.map(instanceCmp -> instanceCmp.toString(object, verbose))
			// As a last alternative, we use the "#toString" method by default,
			// but in reality this case is only an alternative because it
			// should never be executed.
			.orElseGet(object::toString);
	}
	
	/* -----------------------------------------------------
	 * Static methods
	 * ----------------------------------------------------- */
	
	/**
	 * registers a new component to the handler instance
	 *
	 * @param component the new component to register
	 */
	@Override
	public synchronized void registerComponent(@NotNull UToStringComponent component) {
		UObject.requireNotNull(component, "component");
		// Check if component a not editable component or already exists
		Class<?> componentCls = component.getClass();
		if (UArray.contains(noEditableComponents, componentCls) ||
			registeredTypes.contains(componentCls)) {
			return;
		}
		
		// We make the changes to the array.
		// The last element of the array is considered fixed and should
		// never change because it is an essential element with generic functionality.
		UToStringComponent lastComponent = UArray.unsafeLastElement(components);
		UToStringComponent[] tmpComponents = new UToStringComponent[components.length + 1];
		
		// Copy the base elements
		System.arraycopy(components, 0, tmpComponents, 0, components.length - 1);
		tmpComponents[components.length - 1] = component;
		tmpComponents[tmpComponents.length - 1] = lastComponent;
		
		// Replace the component array and add new restrictions
		components = tmpComponents;
		registeredTypes.add(component.getClass());
	}
	
	/* -----------------------------------------------------
	 * Internal methods
	 * ----------------------------------------------------- */
	
	/**
	 * Removes a component from the component list
	 *
	 * @param cls The class of the component you want to remove
	 */
	@Override
	public synchronized void removeComponent(@NotNull Class<? extends UToStringComponent> cls) {
		UObject.requireNotNull(cls, "cls");
		// Check if component is a not editable component or not exists
		if (UArray.contains(noEditableComponents, cls) || !registeredTypes.contains(cls)) {
			return;
		}
		// Remove component instance
		Class<?>[] componentClasses = UArray.transform(components, Object::getClass, Class[]::new);
		int indexComponent = UArray.indexOf(componentClasses, cls);
		
		// We make the changes to the array.
		// The last element of the array is considered fixed and should
		// never change because it is an essential element with generic functionality.
		UToStringComponent lastComponent = UArray.unsafeLastElement(components);
		UToStringComponent[] tmpComponents = new UToStringComponent[components.length - 1];
		
		// Copy the base elements
		System.arraycopy(components, 0, tmpComponents, 0, indexComponent);
		if (indexComponent < tmpComponents.length) {
			System.arraycopy(components, indexComponent + 1, tmpComponents, indexComponent,
							 tmpComponents.length - indexComponent);
		}
		tmpComponents[tmpComponents.length - 1] = lastComponent;
		
		// Replace the component array and add new restrictions
		components = tmpComponents;
		registeredTypes.remove(cls);
	}
	
}
