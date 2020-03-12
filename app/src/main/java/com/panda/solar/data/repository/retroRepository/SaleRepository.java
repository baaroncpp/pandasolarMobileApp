package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleList;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleRepository implements SaleDAO{

    private static SaleRepository instance;
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();

    public static SaleRepository getInstance(){
        if(instance == null){
            instance = new SaleRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<LeaseSale> makeLeaseSale(final ResponseCallBack callBack, LeaseSaleModel leaseSaleModel) {

        final MutableLiveData<LeaseSale> resultData = new MutableLiveData<>();
        Call<LeaseSale> leaseSale = pandaCoreAPI.makeLeaseSale(leaseSaleModel.getLeaseoffer(),
                                                               leaseSaleModel.getAgentid(),
                                                               leaseSaleModel.getCustomerid(),
                                                               leaseSaleModel.getCordlat(),
                                                               leaseSaleModel.getCordlong(),
                                                               leaseSaleModel.getDeviceserial());

        leaseSale.enqueue(new Callback<LeaseSale>() {
            @Override
            public void onResponse(Call<LeaseSale> call, Response<LeaseSale> response) {
                if(!response.isSuccessful()){
                    callBack.onError();
                    return;
                }
                callBack.onSuccess();
                resultData.postValue(response.body());

            }

            @Override
            public void onFailure(Call<LeaseSale> call, Throwable t) {
                callBack.onFailure();
                Log.e("MAKE_LEASESALE",t.getMessage());
                return;
            }
        });
        return resultData;
    }

    @Override
    public MutableLiveData<Sale> makeDirectSale(final ResponseCallBack callBack, DirectSaleModel directSaleModel) {

        final MutableLiveData<Sale> data = new MutableLiveData<>();
        Call<Sale> sale = pandaCoreAPI.makeDirectSale(directSaleModel);

        sale.enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                if(!response.isSuccessful()){
                    callBack.onError();
                    return;
                }
                callBack.onSuccess();
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<List<SaleModel>> getSalesByAgent(final ResponseCallBack callBack, String id, int page, int size, String sortby, String sortorder) {

        final MutableLiveData<List<SaleModel>> resultData = new MutableLiveData<>();
        Call<List<SaleModel>> sales = pandaCoreAPI.getAgentSales(id, page, size, sortby, sortorder);

        sales.enqueue(new Callback<List<SaleModel>>() {
            @Override
            public void onResponse(Call<List<SaleModel>> call, Response<List<SaleModel>> response) {
                if(!response.isSuccessful()){
                    callBack.onError();
                    return;
                }
                callBack.onSuccess();
                resultData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<SaleModel>> call, Throwable t) {
                callBack.onFailure();
                Log.e("AGENT_SALES",t.getMessage());
            }
        });
        return resultData;
    }

    @Override
    public MutableLiveData<List<SaleModel>> getAllSales(final ResponseCallBack callBack, int page, int size, String sortby, String sortorder) {

        final MutableLiveData<List<SaleModel>> resultData = new MutableLiveData<>();
        Call<List<SaleModel>> sales = pandaCoreAPI.getAllSales(page, size, sortby, sortorder);

        sales.enqueue(new Callback<List<SaleModel>>() {
            @Override
            public void onResponse(Call<List<SaleModel>> call, Response<List<SaleModel>> response) {
                if(!response.isSuccessful()){
                    callBack.onError();
                    Log.e("ALL_SALES","failed");
                    return;
                }
                callBack.onSuccess();
                resultData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<SaleModel>> call, Throwable t) {
                callBack.onFailure();
                Log.e("ALL_SALES",t.getMessage());
                return;
            }
        });
        return resultData;
    }

    @Override
    public MutableLiveData<Sale> approveSale(final ResponseCallBack callBack, String itemId, String approveStatus, String reviewDescription) {

        final MutableLiveData<Sale> dataResult = new MutableLiveData<>();
        Call<Sale> call = pandaCoreAPI.approveSale(itemId, approveStatus, reviewDescription);

        call.enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                if(!response.isSuccessful()){
                    callBack.onError();
                    return;
                }
                dataResult.postValue(response.body());
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }
}
