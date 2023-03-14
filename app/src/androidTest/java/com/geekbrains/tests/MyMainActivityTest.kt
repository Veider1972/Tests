package com.geekbrains.tests

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class MyMainActivityTest {

    private val uiDevice = UiDevice.getInstance(getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val packageName = context.packageName

    private val TIMEOUT = 10000L

    @Before
    fun setup() {

        uiDevice.pressHome()

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        Assert.assertNotNull(intent)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        context.startActivity(intent)

        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_MainActivityIsStarted() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)

        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        Assert.assertNotNull(searchButton)
    }

    @Test
    fun test_MainActivityEditWork() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "Butterfly"
        Assert.assertNotNull(uiDevice.findObject(By.text("Butterfly")))
    }

    @Test
    fun test_MainActivityGetResult() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "Butterfly"
        Espresso.onView(ViewMatchers.withId(R.id.searchButton)).perform(ViewActions.click())
        val totalCountTextView = uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            TIMEOUT
        )
        Assert.assertEquals("Number of results: 4919", totalCountTextView.text.toString())
        uiDevice.waitForIdle(2000L)
        val scroller = UiScrollable(UiSelector().className(RecyclerView::class.java))
        val info: UiObject = scroller.getChildByText(
            UiSelector().className(TextView::class.java),
            "Chocolate1999/hexo-blog-lionkk"
        )
        Assert.assertNotNull(info)



    }


}