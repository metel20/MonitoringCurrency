package com.metel20.presentation.core.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : MaterialButton(context, attrs, defStyleAttr), ChangeVisibility {

    override fun onSaveInstanceState(): Parcelable {
        return super.onSaveInstanceState().let {
            val state = VisibilitySavedState(it)
            state.save(this)
            state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoreState = state as VisibilitySavedState?
        super.onRestoreInstanceState(restoreState?.superState)
        restoreState?.restore(this)
    }

    override fun show() {
        visibility = VISIBLE
    }

    override fun hide() {
        visibility = GONE
    }
}