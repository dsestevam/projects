package com.dsestevam.am53.ex1;

import org.junit.Assert;
import org.junit.Test;

import com.dsestevam.am53.ex1.Solution;
import com.dsestevam.am53.ex1.SolutionImpl;


public class SolutionTest {

	@Test
	public void testIsUnique() {
		Solution solution = new SolutionImpl();
		Assert.assertTrue(solution.isUnique("abcde"));
		Assert.assertFalse(solution.isUnique("abcbe"));
	}
}
