package com.example.productivitygame.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.productivitygame.QuestsActivity
import com.example.productivitygame.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TaskUITest {
    private lateinit var db: AppDatabase
    private lateinit var taskDao: TaskDao

    @get:Rule
    val activityRule = ActivityScenarioRule(QuestsActivity::class.java)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = db.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //1: adding a new task and seeing it displayed
    @Test
    fun addAndDisplayTask() {
        val taskName = "New Quest"

        onView(withId(R.id.btnAddTask)).perform(click())

        onView(withId(R.id.TaskName)).perform(typeText(taskName))

        onView(withId(R.id.addButton)).perform(click())

        onView(withText(taskName)).check(matches(isDisplayed()))
    }

    //2: save a task with no name shows a validation error
    @Test
    fun saveEmptyTask() {
        onView(withId(R.id.btnAddTask)).perform(click())

        onView(withId(R.id.addButton)).perform(click())

        onView(withId(R.id.TaskName)).check(matches(isDisplayed()))
    }


    //3: completing a task removes it from the quests list
    @Test
    fun completeTask() {
        val taskName = "Completed task"

        onView(withId(R.id.btnAddTask)).perform(click())
        onView(withId(R.id.TaskName)).perform(typeText(taskName))
        onView(withId(R.id.addButton)).perform(click())

        onView(withText(taskName)).check(matches(isDisplayed()))

        onView(allOf(withId(R.id.finishBtn), hasSibling(withText(taskName))))
            .perform(click())

        onView(withText(taskName)).check(doesNotExist())
    }
}
