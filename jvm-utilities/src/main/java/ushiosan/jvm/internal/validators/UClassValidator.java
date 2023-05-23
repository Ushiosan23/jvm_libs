package ushiosan.jvm.internal.validators;

public abstract class UClassValidator {
	
	/* -----------------------------------------------------
	 * Constants
	 * ----------------------------------------------------- */
	
	/**
	 * Java wrapped primitive types
	 */
	protected static final Class<?>[] PRIMITIVE_WRAPPED_CLASSES = new Class[]{
		Boolean.class,
		Character.class,
		Byte.class,
		Short.class,
		Integer.class,
		Long.class,
		Float.class,
		Double.class,
		Void.class};
	
	/**
	 * Java primitive array types
	 */
	protected static final Class<?>[] PRIMITIVE_ARRAY_CLASSES = new Class[]{
		boolean[].class,
		char[].class,
		byte[].class,
		short[].class,
		int[].class,
		long[].class,
		float[].class,
		double[].class};
	
	/**
	 * Reference to the wrapper class for primitive types in arrays.
	 */
	protected static final Class<?>[] PRIMITIVE_ARRAY_INDIVIDUAL = new Class[]{
		boolean.class,
		char.class,
		byte.class,
		short.class,
		int.class,
		long.class,
		float.class,
		double.class};
	
	/**
	 * classes that are not valid to obtain values of both properties and methods
	 */
	protected static final Class<?>[] INVALID_GET_TYPES = new Class[]{
		void.class,
		Void.class};
	
}
