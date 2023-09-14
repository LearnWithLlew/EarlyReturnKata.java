package org.samples;


import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;
import org.lambda.utils.Range;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTests
{
  @Test
  public void testNormalJunit()
  {
    assertEquals(5, 5);
  }

  @Test
  void testNumbers() {
    _1_Numbers numbers = new _1_Numbers();
    Integer[] ns = Range.get(0,33);
    Integer[] js = ns;
    Double[] names = {3.0,18.0};
    CombinationApprovals.verifyAllCombinations(numbers::calculate,ns, js, names);
  }
}
