package nz.co.fendustries.playground.logic

import org.junit.Assert.assertEquals
import org.junit.Test

class CorrectlyCoveredLogicTest {

    @Test
    fun testAddSomeNumbers() {
        assertEquals(10, CorrectlyCoveredLogic().addSomeNumbers(5, 5))
    }
}