package com.s3infosoft.loyaltyapp;

import android.util.Log;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.s3infosoft.loyaltyapp.model.Product;
import com.s3infosoft.loyaltyapp.model.SpecialDeal;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LandingActivityTest {

    @Rule
    public ActivityTestRule<LandingActivity> activityTestRule = new ActivityTestRule<LandingActivity>(LandingActivity.class);

    private LandingActivity landingActivity = null;

    @Before
    public void setUp() throws Exception {
        landingActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        landingActivity = null;
    }

    @Test
    public void onCreate() {
        View view = landingActivity.findViewById(R.id.drawer_layout);

        assertNotNull(view);
    }

    @Test
    public void initialProductsTest()
    {
        List<Product> products = landingActivity.products;
        assertEquals(0, products.size());
    }

    @Test
    public void initialSpecialDealTest()
    {
        List<SpecialDeal> specialDeals = landingActivity.specialDeals;
        assertEquals(0, specialDeals.size());
    }
}