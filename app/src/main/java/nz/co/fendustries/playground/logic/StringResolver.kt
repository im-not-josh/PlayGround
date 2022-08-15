package nz.co.fendustries.playground.logic

import android.content.Context
import nz.co.fendustries.playground.R

class StringResolver {
    fun getUpdatedString(context: Context, now: Boolean): String {
        return if (now) {
            context.getString(R.string.updated_now)
        } else {
            context.getString(R.string.updated_ages_ago)
        }
    }
}