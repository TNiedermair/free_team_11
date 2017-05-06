package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
public class NewChallengeInstrumentedTest {

    private String challengeName = "Challenge Name";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example 1";
    private String exampleAnswer1 = "Question Answer 1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example 2";
    private String exampleAnswer2 = "Question Answer 2";

    private String newChallengeName = "Challenge Name New";

    @Rule
    public ActivityTestRule<NewChallenge> mActivityRule = new ActivityTestRule<>(NewChallenge.class, false, false);

    public void setupIntentDataChallengeFull() {
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        data.putExtra("challenge", challenge);
        mActivityRule.launchActivity(data);
    }

    public void setupIntentDataEmptyChallenge() {
        Intent data = new Intent();
        Challenge challenge = new Challenge("", new ArrayList<Question>());
        data.putExtra("challenge", challenge);
        mActivityRule.launchActivity(data);
    }

    @Test
    public void testLoadChallengeName() throws Exception{
        setupIntentDataChallengeFull();
        onView(withId(R.id.editTextChallengeName)).check(matches(withText(challengeName)));
    }

    @Test
    public void testLoadChallengeQuestions() throws Exception{
        setupIntentDataChallengeFull();

        onView(withId(R.id.listViewQuestions)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(0).check(matches(withText(exampleQuestionName1)));
        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(1).check(matches(withText(exampleQuestionName2)));
    }

    @Test
    public void testLoadChallengeDataMissing() throws Exception{
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        data.putExtra("challenge_wrong", challenge);
        mActivityRule.launchActivity(data);

        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    @Test
    public void testMissingIntentExtra() throws Exception{
        mActivityRule.launchActivity(new Intent());

        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    @Test
    public void testAddQuestion() throws Exception{
        setupIntentDataEmptyChallenge();
        onView(withId(R.id.buttonAddQuestion)).perform(click());

        onView(withId(R.id.editTextQuestionName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextAnswer)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSaveQuestion)).check(matches(isDisplayed()));
    }

    @Test
    public void testToastSaveChallengeWithoutName() throws Exception{
        setupIntentDataEmptyChallenge();
        onView(withId(R.id.editTextChallengeName)).perform(typeText(" "));
        onView(withId(R.id.buttonSaveChallenge)).perform(click());
        onView(withText(R.string.toast_empty_challenge_name))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    @Test
    public void testToastSaveChallengeWithoutName2() throws Exception{
        setupIntentDataChallengeFull();
        onView(withId(R.id.editTextChallengeName)).perform(clearText(), typeText(" "));
        onView(withId(R.id.buttonSaveChallenge)).perform(click());
        onView(withText(R.string.toast_empty_challenge_name))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    @Test
    public void testToastSaveChallengeWithoutQuestions() throws Exception{
        setupIntentDataEmptyChallenge();
        onView(withId(R.id.editTextChallengeName)).perform(typeText("New Challenge"));
        onView(withId(R.id.buttonSaveChallenge)).perform(click());
        onView(withText(R.string.toast_one_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    @Test
    public void testSelectQuestion() throws Exception{
        setupIntentDataChallengeFull();

        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(0).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 0);
    }

    @Test
    public void testSelectQuestionError() throws Exception{
        setupIntentDataChallengeFull();

        assertEquals(mActivityRule.getActivity().selectedQuestionPos, -1);
    }

    @Test
    public void testEditQuestion() throws Exception{
        setupIntentDataChallengeFull();

        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.buttonEditQuestion)).perform(click());

        onView(withId(R.id.editTextQuestionNameEdit)).check(matches(withText(exampleQuestionName2)));
        onView(withId(R.id.editTextQuestionEdit)).check(matches(withText(exampleQuestion2)));
        onView(withId(R.id.editTextAnswerEdit)).check(matches(withText(exampleAnswer2)));
        onView(withId(R.id.buttonSaveQuestionEdit)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditQuestionError() throws Exception{
        setupIntentDataChallengeFull();

        assertEquals(mActivityRule.getActivity().selectedQuestionPos, -1);

        onView(withId(R.id.buttonEditQuestion)).perform(click());

        onView(withText(R.string.toast_select_a_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2000);
    }

    @Test
    public void testChangeChallengeNameAndEditQuestion() throws Exception{
        setupIntentDataChallengeFull();

        onView(withId(R.id.editTextChallengeName)).perform(clearText(), typeText(newChallengeName));

        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.buttonEditQuestion)).perform(click());

        onView(withId(R.id.editTextQuestionNameEdit)).check(matches(withText(exampleQuestionName2)));
        onView(withId(R.id.editTextQuestionEdit)).check(matches(withText(exampleQuestion2)));
        onView(withId(R.id.editTextAnswerEdit)).check(matches(withText(exampleAnswer2)));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onView(withId(R.id.editTextChallengeName)).check(matches(withText(newChallengeName)));
    }

    //TODO: still to implement....

    @Test
    public void testDeleteChallenge() throws Exception{
        setupIntentDataEmptyChallenge();
        onView(withId(R.id.buttonDeleteChallenge)).perform(click());
    }

    @Test
    public void testDeleteQuestion() throws Exception{
        setupIntentDataEmptyChallenge();
        onView(withId(R.id.buttonDeleteQuestion)).perform(click());
    }

    @Test
    public void testToggleStatus() throws Exception{
        setupIntentDataEmptyChallenge();
        onView(withId(R.id.toggleButtonQuestionStatus)).perform(click());
    }
}
