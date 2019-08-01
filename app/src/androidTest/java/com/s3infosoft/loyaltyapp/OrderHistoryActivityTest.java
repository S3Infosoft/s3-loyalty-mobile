package com.s3infosoft.loyaltyapp;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderHistoryActivityTest {

    @Rule
    public ActivityTestRule<OrderHistoryActivity> activityTestRule = new ActivityTestRule<OrderHistoryActivity>(OrderHistoryActivity.class);

    private OrderHistoryActivity orderHistoryActivity = null;

    @Before
    public void setUp() throws Exception {
        orderHistoryActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        orderHistoryActivity = null;
    }

    @Test
    public void onCreate() {
        View view = orderHistoryActivity.findViewById(R.id.progressBar);
        assertNotNull(view);
    }

    @Test
    public void getEmptyOrderHistory()
    {
        assertEquals(0, orderHistoryActivity.orders.size());
    }
}