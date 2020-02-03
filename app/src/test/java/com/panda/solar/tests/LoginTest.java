package com.panda.solar.tests;

import com.google.gson.JsonObject;
import com.panda.solar.data.network.NetworkCallback;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.RetrofitHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Created by macosx on 02/05/2019.
 */

public class LoginTest  {

    @InjectMocks
    private RetrofitHelper retrofitHelper ;



    @Captor
    private ArgumentCaptor<NetworkCallback> callbackCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBadCredentials() {

        JsonObject object = new JsonObject();
        object.addProperty("username", "username");
        object.addProperty("password", "password");

        retrofitHelper.login(object, new NetworkCallback() {
            @Override
            public void onCallback(NetworkResponse networkResponse) {
                Assert.assertEquals(400, networkResponse.getCode());
                Assert.assertEquals("An error occurred", networkResponse.getBody());
            }
        });


//        ActionHandler actionHandler = new ActionHandler(retrofitHelper);
//        actionHandler.doAction();
//        verify(retrofitHelper, times(1)).login(callbackCaptor.capture());
//
//
//        MockedNetworkCallback callback = callbackCaptor.getValue();
//        NetworkResponse response = new NetworkResponse();
//        callback.onCallback(response);
//
//        System.out.print(response.getBody());

//        RetrofitHelper retrofitHelper = new RetrofitHelper();
//        retrofitHelper.login(networkCallback);
//
//        verify(networkCallback, times(1)).onCallback(any(NetworkResponse.class));
//
//        MockedNetworkCallback callback = callbackCaptor.getValue();
//        NetworkResponse networkResponse = new NetworkResponse();
//
//        callback.onCallback(networkResponse);
//        System.out.println(networkResponse.getCode());

//        NetworkCallback callback = networkCallback;
//        NetworkResponse response = new NetworkResponse();
//        callback.onCallback(response);
//
//        String expectedMessage = "Successful data response";
//        String s = response.getBody();
//        assertEquals(expectedMessage, s);

    }

    @Test
    public void testCorrectCreds(){

        JsonObject object = new JsonObject();
        object.addProperty("username", "256777110054");
        object.addProperty("password", "starboy");

        retrofitHelper.login(object, new NetworkCallback() {
            @Override
            public void onCallback(NetworkResponse networkResponse) {
                Assert.assertEquals(200, networkResponse.getCode());
            }
        });
    }
}
