package com.s3infosoft.loyaltyapp;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> activityTestRule = new ActivityTestRule<SplashActivity>(SplashActivity.class);

    private SplashActivity splashActivity = null;

    @Before
    public void setUp() throws Exception {
        splashActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        splashActivity = null;
    }

    @Test
    public void onCreate() {
        View view = splashActivity.findViewById(R.id.splash_layout);
        assertNotNull(view);
    }
}