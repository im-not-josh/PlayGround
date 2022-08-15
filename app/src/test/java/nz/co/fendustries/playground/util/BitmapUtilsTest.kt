package nz.co.fendustries.playground.util

import android.content.Context
import android.graphics.Bitmap
import androidx.test.core.app.ApplicationProvider
import nz.co.fendustries.playground.BuildConfig
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [31], shadows = [ShadowExifInterface::class])
class BitmapUtilsTest {

    @Test
    fun `Given file path, when passed to downSampleBitmapFromFilePath, then validate the returned bitmap`() {
        // Given
        val path = ApplicationProvider.getApplicationContext<Context>().cacheDir.toString() + "dcim/abcd"

        // When
        val result = BitmapUtils.downSampleBitmapFromFilePath(path)

        // Then
        Assert.assertTrue(result is Bitmap)
        Assert.assertEquals(result.width, result.height)
    }

    @Test
    fun `Given file path, when passed to decodeBitmapAndScale, then returns bitmap with changed scale`() {
        // Given
        val path = ApplicationProvider.getApplicationContext<Context>().cacheDir.toString() + "dcim/abcd"
        val bitmapImage = BitmapUtils.downSampleBitmapFromFilePath(path)
        val beforeWidth = bitmapImage.width

        // When
        val result = BitmapUtils.decodeBitmapAndScale(path, ApplicationProvider.getApplicationContext())

        // Then
        Assert.assertTrue(result is Bitmap)
        Assert.assertNotEquals(beforeWidth, result.width)
        Assert.assertEquals(result.height, result.width)
    }

    @Test
    fun `given a double value, when passed to roundTwoDecimals, then returns round value with two decimals`() {
        // Given
        val value = 123455.98978
        // When
        val result = BitmapUtils.roundTwoDecimals(value)
        // Then
        Assert.assertEquals(123455.99, result, 0.01)
    }

    @Test
    fun `Given bitmap image of width 100, when passed to scaleToFitWidth with new width of 200, then returns new bitmap with changes width`() {
        // Given
        val path = ApplicationProvider.getApplicationContext<Context>().cacheDir.toString() + "dcim/abcd"
        val bitmapImage = BitmapUtils.downSampleBitmapFromFilePath(path)
        val beforeWidth = bitmapImage.width

        // When
        val result = BitmapUtils.scaleToFitWidth(bitmapImage, 200)

        // Then
        Assert.assertNotEquals(beforeWidth, result.width)
    }
}