package ushiosan.jvm_utilities.lang;

import org.junit.Assert;
import org.junit.Test;

public class MathsTest {

	@Test
	public void lerpTest() {
		double position = 5.0;
		long counter = 0;

		while (!Maths.isZero(position)) {
			position = Maths.lerp(position, 0.0, 0.125);
			counter++;
		}

		System.out.println("\nLinear interpolation");
		System.out.printf("Position: %f\nCounter: %d\n", position, counter);
	}

	@Test
	public void lerpPreciseTest() {
		double position = 5.0;
		long counter = 0;

		while (!Maths.isZero(position)) {
			position = Maths.lerpPrecise(position, 0.0, 0.125);
			counter++;
		}

		System.out.println("\nLinear interpolation precise");
		System.out.printf("Position: %f\nCounter: %d\n", position, counter);
	}

	@Test
	public void distanceTest() {
		double x1 = 20.123;
		double x2 = 12380.1289132;

		double x = Maths.distance(x1, x2);
		System.out.printf("\nDistance: %f\n", x);
	}

	@Test
	public void normalizeTest() {
		double value = 12.1278713812313;
		double normal = Maths.normalize(12, 13, value);

		System.out.printf("\nNormal: %f\n", normal);
	}

	@Test
	public void clampTest() {
		double value = 20.0;

		System.out.printf("\nNormal: %f\n", value);
	}

	@Test
	public void testEqualsTest() {
		double v1 = Math.PI;
		double v2 = 3.1415925;
		boolean result = Maths.equals(v1, v2);

		Assert.assertTrue("Invalid value equality", result);
		System.out.printf("\nThe numbers %.15f & %.15f are equals: %s\n", v1, v2, Maths.equals(v1, v2));
	}

	@Test
	public void isZeroTest() {
		double zero1 = 0.00000001213124;
		double zero2 = 0.00000121314524;

		Assert.assertTrue("Invalid zero", Maths.isZero(zero1));
		Assert.assertFalse("The value is zero", Maths.isZero(zero2));

		System.out.printf("\nValid zero:   %.15f\n", zero1);
		System.out.printf("Invalid zero: %.15f\n", zero2);
	}

	@Test
	public void toDegreesTest() {
		// PI = 180 degrees
		double PI = Maths.toDegrees(Math.PI);
		// PI2 = 360 degrees
		double PI2 = Math.toDegrees(Math.PI * 2);

		Assert.assertEquals("PI is not valid", 180.0, PI, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("PI2 is not valid", 360.0, PI2, Maths.DECIMAL_TOLERANCE);

		System.out.printf("\nPI to degrees: %.12f\n", PI);
		System.out.printf("PI2 to degrees: %.12f\n", PI2);
	}

	@Test
	public void toRadiansTest() {
		// 180 degrees = PI radians
		double middleCircle = Maths.toRadians(180.0);
		// 360 degrees = PI2 radians
		double completeCircle = Maths.toRadians(360.0);

		Assert.assertEquals("Middle circle is not valid", Math.PI, middleCircle, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("Complete circle is not valid", Math.PI * 2, completeCircle, Maths.DECIMAL_TOLERANCE);

		System.out.printf("\n180 degrees: %.12f radians\n", middleCircle);
		System.out.printf("360 degrees: %.12f radians\n", completeCircle);
	}

	@Test
	public void percentageTest() {
		// 150% of 1000 = 1500
		double hundredFifty = Maths.percentage(1000.0, 150.0);
		// 22% of 27.72 = 6.0984
		double twentyTwo = Maths.percentage(27.72, 22.0);

		Assert.assertEquals("Invalid percentage", 1500.0, hundredFifty, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("Invalid percentage", 6.0984, twentyTwo, Maths.DECIMAL_TOLERANCE);

		System.out.printf("\n150%% of 1000: %.4f\n", hundredFifty);
		System.out.printf("22%% of 27.72: %.4f\n", twentyTwo);
	}

	@Test
	public void percentageValueTest() {
		// 25% to decimal -> 0.25
		double twentyFive = Maths.percentageValue(25);
		// 50% to decimal -> 0.50
		double fifty = Maths.percentageValue(50);
		// 100% to decimal -> 1.0
		double hundred = Maths.percentageValue(100);
		// 125% to decimal -> 1.25
		double hundredTwentyFive = Maths.percentageValue(125);

		Assert.assertEquals("Invalid decimal percentage", 0.25, twentyFive, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("Invalid decimal percentage", 0.50, fifty, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("Invalid decimal percentage", 1.0, hundred, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("Invalid decimal percentage", 1.25, hundredTwentyFive, Maths.DECIMAL_TOLERANCE);

		System.out.printf("\n25%% to decimal: %f\n", twentyFive);
		System.out.printf("50%% to decimal: %f\n", fifty);
		System.out.printf("100%% to decimal: %f\n", hundred);
		System.out.printf("125%% to decimal: %f\n", hundredTwentyFive);
	}

}