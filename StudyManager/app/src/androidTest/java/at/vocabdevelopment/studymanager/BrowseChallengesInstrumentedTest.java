package at.vocabdevelopment.studymanager;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class BrowseChallengesInstrumentedTest {

    @Rule
    public ActivityTestRule<BrowseChallenges> mActivityRule = new ActivityTestRule<>(BrowseChallenges.class);

    @Test
    public void testSelectChallenge() throws Exception {
        onView(withId(R.id.buttonSelectChallenge)).perform(click());
    }

    @Test
    public void testAddChallengeRedirect() throws Exception{
        onView(withId(R.id.buttonAddChallenge)).perform(click());

        onView(withId(R.id.editTextChallengeName)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonDeleteChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewQuestions)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSaveChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonDeleteQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditQuestion)).check(matches(isDisplayed()));
    }
}

