package com.s3infosoft.loyaltyapp;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.s3infosoft.loyaltyapp.model.CartItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedeemActivityTest {

    @Rule
    public ActivityTestRule<RedeemActivity> activityTestRule = new ActivityTestRule<RedeemActivity>(RedeemActivity.class);

    private RedeemActivity redeemActivity = null;

    @Before
    public void setUp() throws Exception {
        redeemActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        redeemActivity = null;
    }

    @Test
    public void onCreate() {
        View view = redeemActivity.findViewById(R.id.name);
        assertNotNull(view);
    }

    @Test
    public void IteminCart()
    {
        if (redeemActivity.databaseHandler.addItem(new CartItem()))
        {
            assertTrue(true);
        }
        else
        {
            assertFalse(false);
        }
    }
}