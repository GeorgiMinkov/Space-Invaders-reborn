package fmi.course.simmim.spaceinvaders;

import android.app.Activity;
import android.app.Instrumentation;
import android.provider.ContactsContract;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule =
            new ActivityTestRule<>(LoginActivity.class, true, false);

    @Test
    public void testSuccessfulLogin() {
        ContactsContract.Intents.init();
        intending(anyOf(hasComponent(LoginActivity.class.getName()), hasComponent(IntroActivity.class.getName())))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null));
        loginActivityRule.launchActivity(null);
        intended(hasComponent(LoginActivity.class.getName()), times(1));
        ContactsContract.Intents.release();
    }

}
