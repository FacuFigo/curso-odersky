package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = Set((x: Int) => x > 0 && x < 300)
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }

  }

  test("intersect contains all elements that are both in sets") {
    new TestSets {
      val s9 = singletonSet(9)
      val s6 = singletonSet(9)
      val intersectedSet = intersect(x => x > 0, s9)

      assert(contains(intersectedSet, 9), "Should have only '9' element")
      assert(!contains(intersectedSet, 5))
    }
  }

  test("diff") {
    new TestSets {
      val diffSet = diff(s1, s3)
      assert(contains(diffSet, 1))
      assert(!contains(diffSet, 3))
    }
  }

  test("filter") {
    new TestSets {
      val filterSet = filter(s3, x => x > 0)
      assert(contains(filterSet, 3))
      assert(!contains(filterSet, 2))
    }
  }

  test("forall") {
    new TestSets {
      val forallSet = (1 to (100, 2)).toSet
      val forallSet2 = (1 to 100).toSet
      val imparesCon10 = Set (1, 3, 5 ,7, 10)
      assert(forall(forallSet, x => x % 2 == 1))
      assert(!forall(forallSet2, x => x < 0))
      assert(!forall(imparesCon10, x => x % 2 ==1))
    }
  }

  test("exists") {
    new TestSets {
      val testSet = (-50 to 3).toSet
      val testSet2 = (-100 to 0).toSet
      assert(exists(testSet, x => x > 0))
      assert(!exists(testSet2, x => x > 0))
    }
  }

  test("map") {
    new TestSets {
      val testSet = (1 to(100, 2)).toSet
      val f = (x:Int) => x + 1

      printSet(map(testSet,f))
      assert(forall(map(testSet, f), x => x % 2 == 0))
    }
  }
}
