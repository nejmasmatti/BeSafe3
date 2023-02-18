package fr.projet.besafe;

import static android.service.autofill.Validators.not;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.service.autofill.Validator;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.projet.besafe.ConnexionActivity;

@RunWith(AndroidJUnit4.class)
public class ConnexionActivityTest {

    @Rule
    public ActivityScenarioRule<ConnexionActivity> activityRule =
            new ActivityScenarioRule<>(ConnexionActivity.class);

    @Test
    public void testConnexionButton() {

        onView(withId(R.id.edtlogin)).perform(typeText("test@example.com"));
        onView(withId(R.id.edtpass)).perform(typeText("password"));

        onView(withId(R.id.btnconn)).perform(click());

        ActivityScenario<ConnexionActivity> scenario = activityRule.getScenario();
        scenario.onActivity(activity -> {
            onView(withId(R.id.progress_dialog))
                    .inRoot(withDecorView((Matcher<View>) not((Validator) activity.getWindow().getDecorView())))
                    .check(matches(isDisplayed()));
        });
    }
}
