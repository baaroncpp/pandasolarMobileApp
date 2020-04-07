package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.UploadLinks;
import com.panda.solar.utils.ResponseCallBack;

public interface UploadLinkDAO {

    MutableLiveData<UploadLinks> getUploadLinks(ResponseCallBack callBack, String linkType, String id);
}
