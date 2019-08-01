package com.s3infosoft.loyaltyapp;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class PurchaseActivityTest {

    @Rule
    public ActivityTestRule<PurchaseActivity> activityTestRule = new ActivityTestRule<PurchaseActivity>(PurchaseActivity.class);

    private PurchaseActivity purchaseActivity = null;

    @Before
    public void setUp() throws Exception {
        purchaseActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        purchaseActivity = null;
    }

    @Test
    public void onCreate() {
        View view = purchaseActivity.findViewById(R.id.amount);
        assertNotNull(view);
    }
}