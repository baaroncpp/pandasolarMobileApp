package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.FileResponse;
import com.panda.solar.Model.entities.UploadLinks;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.ResponseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadLinkReposotory implements UploadLinkDAO {

    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    private static UploadLinkReposotory instance;
    private NetworkResponse netResponse = new NetworkResponse();

    public static UploadLinkReposotory getInstance(){
        if(instance == null){
            return new UploadLinkReposotory();
        }else{
            return instance;
        }
    }

    @Override
    public MutableLiveData<UploadLinks> getUploadLinks(final ResponseCallBack callBack, String linkType, String id) {

        final MutableLiveData<UploadLinks> dataResult = new MutableLiveData<>();
        Call<UploadLinks> call = pandaCoreAPI.getUploadLinks(linkType, id);

        call.enqueue(new Callback<UploadLinks>() {
            @Override
            public void onResponse(Call<UploadLinks> call, Response<UploadLinks> response) {
                if(!response.isSuccessful()){
                    netResponse.setCode(response.code());
                    try {
                        netResponse.setBody(new JSONObject(response.errorBody().string()).getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callBack.onError(netResponse);
                }
                callBack.onSuccess();
                dataResult.postValue(response.body());
            }

            @Override
            public void onFailure(Call<UploadLinks> call, Throwable t) {
                callBack.onFailure();
            }
        });

        return dataResult;
    }

    @Override
    public MutableLiveData<FileResponse> uploadFile(final ResponseCallBack callBack, String id, MultipartBody.Part fileBody, RequestBody uploadType) {

        final MutableLiveData<FileResponse> dataResult = new MutableLiveData<>();
        Call<FileResponse> call = pandaCoreAPI.uploadCustomerFile(id, fileBody, uploadType);

        call.enqueue(new Callback<FileResponse>() {
            @Override
            public void onResponse(Call<FileResponse> call, Response<FileResponse> response) {
                if(!response.isSuccessful()){
                    try {
                        netResponse.setBody(new JSONObject(response.errorBody().string()).getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                callBack.onSuccess();
                dataResult.postValue(response.body());
            }

            @Override
            public void onFailure(Call<FileResponse> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }
}
