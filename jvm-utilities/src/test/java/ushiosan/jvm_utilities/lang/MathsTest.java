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
		
		System.out.println("lerpTest()");
		System.out.printf("Position: %f\nCounter: %d\n", position, counter);
		System.out.println();
	}
	
	@Test
	public void lerpPreciseTest() {
		double position = 5.0;
		long counter = 0;
		
		while (!Maths.isZero(position)) {
			position = Maths.lerpPrecise(position, 0.0, 0.125);
			counter++;
		}
		
		System.out.println("lerpPreciseTest()");
		System.out.printf("Position: %f\nCounter: %d\n", position, counter);
		System.out.println();
	}
	
	@Test
	public void distanceTest() {
		double x = Maths.distance(20.123, 12380.1289132);
		double y = Maths.distance(-1, 10);
		
		Assert.assertEquals("Invalid distance", 11.0, y, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("Invalid distance", 12360.0059132, x, Maths.DECIMAL_TOLERANCE);
		
		System.out.println("distanceTest()");
		System.out.printf("Distance between 20.123 and 12380.1289132: %f\n", x);
		System.out.printf("Distance between -1 and 10: %f\n", y);
		System.out.println();
	}
	
	@Test
	public void normalizeTest() {
		double value = 12.1278713812313;
		double normal = Maths.normalize(12, 13, value);
		
		Assert.assertEquals("Invalid result", 0.1278713812, normal, Maths.DECIMAL_TOLERANCE);
		
		System.out.println("normalizeTest()");
		System.out.printf("normalized value between 12 and 13 from 12.1278713812313: %f\n", normal);
		System.out.println();
	}
	
	@Test
	public void clampTest() {
		double value = 20.0;
		double clampValue = Maths.clamp(value, 0, 15.24);
		
		Assert.assertEquals("Invalid clamp value", 15.24, clampValue, Maths.DECIMAL_TOLERANCE);
		
		System.out.println("clampTest()");
		System.out.printf("clamp value %f between %f and %f: %f\n", value, 0.0, 15.24, clampValue);
		System.out.println();
	}
	
	@Test
	public void equalsTest() {
		double v1 = Math.PI;
		double v2 = 3.1415925;
		boolean result = Maths.equals(v1, v2);
		
		Assert.assertTrue("Invalid value equality", result);
		
		System.out.println("equalsTest()");
		System.out.printf("The numbers %.15f & %.15f are equals: %s\n", v1, v2, Maths.equals(v1, v2));
		System.out.println();
	}
	
	@Test
	public void isZeroTest() {
		double zero1 = 0.00000001213124;
		double zero2 = 0.00000121314524;
		
		Assert.assertTrue("Invalid zero", Maths.isZero(zero1));
		Assert.assertFalse("The value is zero", Maths.isZero(zero2));
		
		System.out.println("isZeroTest()");
		System.out.printf("Valid zero:   %.15f\n", zero1);
		System.out.printf("Invalid zero: %.15f\n", zero2);
		System.out.println();
	}
	
	@Test
	public void toDegreesTest() {
		// PI = 180 degrees
		double PI = Maths.toDegrees(Math.PI);
		// PI2 = 360 degrees
		double PI2 = Math.toDegrees(Math.PI * 2);
		
		Assert.assertEquals("PI is not valid", 180.0, PI, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("PI2 is not valid", 360.0, PI2, Maths.DECIMAL_TOLERANCE);
		
		System.out.println("toDegreesTest()");
		System.out.printf("PI to degrees: %.12f\n", PI);
		System.out.printf("PI2 to degrees: %.12f\n", PI2);
		System.out.println();
	}
	
	@Test
	public void toRadiansTest() {
		// 180 degrees = PI radians
		double middleCircle = Maths.toRadians(180.0);
		// 360 degrees = PI2 radians
		double completeCircle = Maths.toRadians(360.0);
		
		Assert.assertEquals("Middle circle is not valid", Math.PI, middleCircle, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("Complete circle is not valid", Math.PI * 2, completeCircle, Maths.DECIMAL_TOLERANCE);
		
		System.out.println("toRadiansTest()");
		System.out.printf("180 degrees: %.12f radians\n", middleCircle);
		System.out.printf("360 degrees: %.12f radians\n", completeCircle);
		System.out.println();
	}
	
	@Test
	public void percentageTest() {
		// 150% of 1000 = 1500
		double hundredFifty = Maths.percentage(1000.0, 150.0);
		// 22% of 27.72 = 6.0984
		double twentyTwo = Maths.percentage(27.72, 22.0);
		
		Assert.assertEquals("Invalid percentage", 1500.0, hundredFifty, Maths.DECIMAL_TOLERANCE);
		Assert.assertEquals("Invalid percentage", 6.0984, twentyTwo, Maths.DECIMAL_TOLERANCE);
		
		System.out.println("percentageTest()");
		System.out.printf("150%% of 1000: %.4f\n", hundredFifty);
		System.out.printf("22%% of 27.72: %.4f\n", twentyTwo);
		System.out.println();
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
		
		System.out.println("percentageValueTest()");
		System.out.printf("25%% to decimal: %f\n", twentyFive);
		System.out.printf("50%% to decimal: %f\n", fifty);
		System.out.printf("100%% to decimal: %f\n", hundred);
		System.out.printf("125%% to decimal: %f\n", hundredTwentyFive);
		System.out.println();
	}
	
}