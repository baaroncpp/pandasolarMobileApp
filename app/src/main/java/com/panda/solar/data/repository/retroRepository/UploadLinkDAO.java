package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.FileResponse;
import com.panda.solar.Model.entities.UploadLinks;
import com.panda.solar.utils.ResponseCallBack;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface UploadLinkDAO {

    MutableLiveData<UploadLinks> getUploadLinks(ResponseCallBack callBack, String linkType, String id);

    MutableLiveData<FileResponse> uploadFile(ResponseCallBack callBack, String id, MultipartBody.Part fileBody, RequestBody uploadType);
}
