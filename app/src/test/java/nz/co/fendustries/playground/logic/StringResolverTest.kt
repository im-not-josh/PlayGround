package nz.co.fendustries.playground.logic

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StringResolverTest {
    @Test
    fun checkUpdatedNowStringNoe() {
        assertEquals("Updated just now", StringResolver().getUpdatedString(ApplicationProvider.getApplicationContext(), true))
    }

    @Test
    fun checkUpdatedNowStringAges() {
        assertEquals("Updated ages ago", StringResolver().getUpdatedString(ApplicationProvider.getApplicationContext(), false))
    }
}