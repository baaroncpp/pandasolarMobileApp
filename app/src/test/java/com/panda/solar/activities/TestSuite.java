package com.panda.solar.activities;

import com.panda.solar.tests.LoginTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class
})
public class TestSuite {


}