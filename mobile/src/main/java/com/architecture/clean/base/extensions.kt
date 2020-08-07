package com.architecture.clean.base

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.architecture.clean.di.component.ApplicationComponent
import com.architecture.clean.view.customview.SpacingDecoration
import com.architecture.clean.view.customview.WrapGridLayoutManager
import com.seva.practicalview.customview.WrapLinearLayoutManager
import kotlin.math.roundToInt


internal fun FragmentManager.addFragment(
    containerViewId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false
) {
    this.beginTransaction()
        .add(containerViewId, fragment)
        .apply { if (addToBackStack) addToBackStack(null) }
        .commit()
}


internal fun FragmentManager.replaceFragment(
    containerViewId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false
) {
    this.beginTransaction()
        .replace(containerViewId, fragment)
        .apply { if (addToBackStack) addToBackStack(null) }
        .commit()
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layout, this, attachToRoot)


fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, action: (t: T) -> Unit) {
    liveData?.observe(this, Observer { t -> action(t) })
}

internal fun RecyclerView.linearLayout(
    context: Context,
    spacing: Int = 0,
    noRightSpacingForFirstItem: Boolean = false,
    spanCount: Int? = 1,
    @RecyclerView.Orientation orientation: Int? = RecyclerView.VERTICAL,
    reverseLayout: Boolean? = false
) {

    layoutManager = WrapLinearLayoutManager(context, orientation!!, reverseLayout!!)
    setHasFixedSize(true)
    addItemDecoration(
        SpacingDecoration(
            spanCount = spanCount!!,
            spacing = spacing,
            noRightSpacingForFirstItem = noRightSpacingForFirstItem,
            includeEdge = true
        )
    )
}


internal fun RecyclerView.gridLayout(
    context: Context,
    spacing: Int = 0,
    noRightSpacingForFirstItem: Boolean = false,
    spanCount: Int = 3,
    @RecyclerView.Orientation orientation: Int? = RecyclerView.VERTICAL,
    reverseLayout: Boolean? = false
) {

    layoutManager = WrapGridLayoutManager(context, spanCount, orientation!!, reverseLayout!!)
    setHasFixedSize(true)
    addItemDecoration(
        SpacingDecoration(
            spanCount = spanCount!!,
            spacing = spacing,
            noRightSpacingForFirstItem = noRightSpacingForFirstItem,
            includeEdge = true
        )
    )
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

    })

}

fun ViewPager.onPageScrolled(onPageScrolled: (position: Int,
                                              positionOffset: Float,
                                              positionOffsetPixels: Int) -> Unit) {

    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolled.invoke(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {}

    })

}

internal fun <T : ViewModel?> provideViewModel(
    @NonNull owner: ViewModelStoreOwner,
    @NonNull modelClass: Class<T>,
    provideViewModel: () -> T
) : T {
    return ViewModelProvider(owner, object : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return provideViewModel.invoke() as T
        }
    }).get(modelClass)
}

fun Context.getApplicationComponent(): ApplicationComponent =
    (applicationContext as? ProjectApplication)
        ?.getApplicationComponent()
        ?: throw IllegalStateException("application class must implement AppComponentProvider")

fun View.getApplicationComponent(): ApplicationComponent =
    context.getApplicationComponent()

fun Fragment.getApplicationComponent(): ApplicationComponent =
    requireContext().getApplicationComponent()

fun adjustAlpha(@ColorInt color: Int, factor: Float) : Int {
    val alpha = (Color.alpha(color) * factor).roundToInt()
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)

    return Color.argb(alpha, red, green, blue)
}

fun Context.showToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
    gravity: Int = Gravity.TOP
) {
    Toast.makeText(this, message, duration).apply {
        setGravity(gravity, 0, 0)
        show()
    }
}

internal fun View.gone() {
    this.visibility = View.GONE
}

internal fun View.hide() {
    this.visibility = View.INVISIBLE
}

internal fun View.show() {
    this.visibility = View.VISIBLE
}