package com.panda.solar.data.network.mocks;

/**
 * Created by macosx on 02/05/2019.
 */

public interface Callback<T> {

    void reply(T response);
}
