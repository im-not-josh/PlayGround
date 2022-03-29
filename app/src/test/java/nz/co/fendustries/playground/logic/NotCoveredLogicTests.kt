package nz.co.fendustries.playground.logic

import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Remove the RunWith RobolectricTestRunner below and Pitest will include this
// class in Mutation Coverage again
@RunWith(RobolectricTestRunner::class)
class NotCoveredLogicTests {

    @Test
    fun testSubtractSomeNumbers() {
        assertEquals(10, NotCoveredLogic().subtractSomeNumbers(15, 5))
    }
}