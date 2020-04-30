package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;
import android.os.AsyncTask;

import com.panda.solar.Model.entities.FileResponse;
import com.panda.solar.Model.entities.UploadLinks;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.UploadLinkDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

public class UploadLinkViewModel extends ViewModel {

    private UploadLinkDAO uploadLinkDAO = PandaDAOFactory.getUploadLinkDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponse = new MutableLiveData<>();
    private MutableLiveData<FileResponse> fileResponseMutableLiveData = new MutableLiveData<>();

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

    public LiveData<FileResponse> uploadFile(Uri uri, String uploadType, String id){

        UploadToServer uploadToServer = new UploadToServer(uri, uploadType, id);
        uploadToServer.execute();

        return fileResponseMutableLiveData;
    }

    public LiveData<NetworkResponse> getNetworkResponse(){return networkResponse;}

    public LiveData<String> getResponseMessage(){return responseMessage;}

    private class UploadToServer extends AsyncTask<Void, Integer, MutableLiveData<FileResponse>> {

        private Uri uri;
        private String uploadType;
        private String id;

        public UploadToServer(Uri uri, String uploadType, String id){
            super();
            uri = uri;
            uploadType = uploadType;
            id = id;
        }

        @Override
        protected MutableLiveData<FileResponse> doInBackground(Void... v) {

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
            }, id, uri, uploadType);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(MutableLiveData<FileResponse> fileResponseLiveData) {
            super.onPostExecute(fileResponseLiveData);
            fileResponseMutableLiveData = fileResponseLiveData;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
