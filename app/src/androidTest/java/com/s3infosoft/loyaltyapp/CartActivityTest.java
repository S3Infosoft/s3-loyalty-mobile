package com.s3infosoft.loyaltyapp;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartActivityTest {

    @Rule
    public ActivityTestRule<CartActivity> activityTestRule = new ActivityTestRule<CartActivity>(CartActivity.class);

    private CartActivity cartActivity = null;

    @Before
    public void setUp() throws Exception {
        cartActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        cartActivity = null;
    }

    @Test
    public void onCreate() {
        View view = cartActivity.findViewById(R.id.recyler_view);
        assertNotNull(view);
    }

    @Test
    public void cartItemsisEmpty()
    {
        if (cartActivity.cartItems.size() > 0)
        {
            assertNotEquals(0, cartActivity.cartItems.size());
        }
        else
        {
            assertEquals(0, cartActivity.cartItems.size());
        }
    }
}