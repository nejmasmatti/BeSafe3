package fr.projet.besafe;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.projet.besafe.MyMapActivity;
import fr.projet.besafe.R;

@RunWith(AndroidJUnit4.class)
public class MyMapActivityTest {

    private static final LatLng PARIS_20 = new LatLng(48.867530, 2.398032);

    @Rule
    public ActivityScenarioRule<MyMapActivity> activityRule =
            new ActivityScenarioRule<>(MyMapActivity.class);

    @Test
    public void testMapNotNull() {
        onView(withId(R.id.mapFrance)).check(matches(isDisplayed()));
    }

    @Test
    public void testMapDefaultLocation() {
        onView(withId(R.id.mapFrance)).check(matches(withMapLocation(PARIS_20)));
    }

    @Test
    public void testMapCustomLocation() {
        LatLng customLocation = new LatLng(48.860611, 2.337644);
        activityRule.getScenario().onActivity(activity -> activity.showMapAtLocation(customLocation));
        onView(withId(R.id.mapFrance)).check(matches(withMapLocation(customLocation)));
    }
    private static Matcher<View> withMapLocation(final LatLng expectedLocation) {
        return new TypeSafeMatcher<View>() {

            @Override
            protected boolean matchesSafely(View view) {
                if (view instanceof MapView) {
                    MapView mapView = (MapView) view;
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng currentLocation = googleMap.getCameraPosition().target;
                            if (currentLocation.equals(expectedLocation)) {
                                // Current location matches expected location
                                // Call onViewFound() to signal that the view has been found
                                onViewFound();
                            }
                        }
                    });
                    // Return false here, since the actual matching is done in onMapReady()
                    return false;
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with map location: " + expectedLocation.toString());
            }
        };
    }

    private static void onViewFound() {
    }


}
