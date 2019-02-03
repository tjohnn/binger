package ng.max.binger.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * Image view drawable matcher
 * From https://github.com/dbottillo/Blog/blob/espresso_match_imageview/app/src/androidTest/java/com/danielebottillo/blog/config/DrawableMatcher.java
 */
class DrawableMatcher(private val expectedId: Int): TypeSafeMatcher<View>(View::class.java){

    private var resourceName: String? = null
   companion object {
       private const val EMPTY = -1
       private const val ANY = -2
   }


    override fun matchesSafely(target: View): Boolean {
        if (target !is ImageView) {
            return false
        }
        if (expectedId == EMPTY) {
            return target.drawable == null
        }
        if (expectedId == ANY) {
            return target.drawable != null
        }
        val resources = target.getContext().getResources()
        val expectedDrawable = resources.getDrawable(expectedId)
        resourceName = resources.getResourceEntryName(expectedId)

        if (expectedDrawable == null) {
            return false
        }

        val bitmap = getBitmap(target.getDrawable())
        val otherBitmap = getBitmap(expectedDrawable!!)
        return bitmap.sameAs(otherBitmap)
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }
}