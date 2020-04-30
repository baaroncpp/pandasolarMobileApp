package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;

import com.panda.solar.Model.entities.FileResponse;
import com.panda.solar.Model.entities.UploadLinks;
import com.panda.solar.utils.ResponseCallBack;

public interface UploadLinkDAO {

    MutableLiveData<UploadLinks> getUploadLinks(ResponseCallBack callBack, String linkType, String id);

    MutableLiveData<FileResponse> uploadFile(ResponseCallBack callBack, String id, Uri uri, String uploadType);
}
