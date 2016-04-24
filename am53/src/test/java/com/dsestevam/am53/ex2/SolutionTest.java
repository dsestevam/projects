package com.dsestevam.am53.ex2;

import org.junit.Assert;
import org.junit.Test;


public class SolutionTest {

	@Test
	public void testCandy() {
		Solution solution = new SolutionImpl();
		int[] candies = {8, 6, 9, 6};
		Assert.assertEquals(7, solution.candy(candies));
	}
}
