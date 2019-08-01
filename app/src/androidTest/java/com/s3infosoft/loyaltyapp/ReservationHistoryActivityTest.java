package com.s3infosoft.loyaltyapp;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReservationHistoryActivityTest {

    @Rule
    public ActivityTestRule<ReservationHistoryActivity> activityTestRule = new ActivityTestRule<ReservationHistoryActivity>(ReservationHistoryActivity.class);

    private ReservationHistoryActivity reservationHistoryActivity = null;

    @Before
    public void setUp() throws Exception {
        reservationHistoryActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        reservationHistoryActivity = null;
    }

    @Test
    public void onCreate() {
        View view = reservationHistoryActivity.findViewById(R.id.progressBar);
        assertNotNull(view);
    }

    @Test
    public void getEmptyReservationHistory()
    {
        assertEquals(0, reservationHistoryActivity.orders.size());
    }
}