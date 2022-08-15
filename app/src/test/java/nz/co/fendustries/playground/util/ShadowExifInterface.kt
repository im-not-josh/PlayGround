package nz.co.fendustries.playground.util

import androidx.exifinterface.media.ExifInterface
import org.robolectric.annotation.Implements

@Implements(value = ExifInterface::class, callThroughByDefault = false)
class ShadowExifInterface