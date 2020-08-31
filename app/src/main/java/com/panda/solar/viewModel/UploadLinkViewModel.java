package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.FileResponse;
import com.panda.solar.Model.entities.UploadLinks;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.UploadLinkDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadLinkViewModel extends ViewModel {

    private UploadLinkDAO uploadLinkDAO = PandaDAOFactory.getUploadLinkDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponse = new MutableLiveData<>();

    public LiveData<UploadLinks> getUploadLinks(String linkType, String id){
        return uploadLinkDAO.getUploadLinks(new ResponseCallBack() {
            @Override
            public void onSuccess() {
                responseMessage.postValue(Constants.SUCCESS_RESPONSE);
            }

            @Override
            public void onFailure() {
                responseMessage.postValue(Constants.FAILURE_RESPONSE);
            }

            @Override
            public void onError(NetworkResponse response) {
                networkResponse.postValue(response);
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        }, linkType, id);
    }

    public LiveData<FileResponse> uploadFile(String id, MultipartBody.Part fileBody, RequestBody uploadType){

        return uploadLinkDAO.uploadFile(new ResponseCallBack() {
            @Override
            public void onSuccess() {
                responseMessage.postValue(Constants.SUCCESS_RESPONSE);
            }

            @Override
            public void onFailure() {
                responseMessage.postValue(Constants.FAILURE_RESPONSE);
            }

            @Override
            public void onError(NetworkResponse response) {
                responseMessage.postValue(Constants.ERROR_RESPONSE);
                networkResponse.postValue(response);
            }
        }, id, fileBody, uploadType);
    }

    public LiveData<NetworkResponse> getNetworkResponse(){return networkResponse;}

    public LiveData<String> getResponseMessage(){return responseMessage;}

}
