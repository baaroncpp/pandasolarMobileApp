package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.Constants;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleRepository implements SaleDAO{

    private static SaleRepository instance;
    private Response saleResponse;
    private static String errorMessage = "";
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();

    public static SaleRepository getInstance(){
        if(instance == null){
            instance = new SaleRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<LeaseSale> makeLeaseSale(LeaseSaleModel leaseSaleModel) {

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
                    errorMessage = Constants.BAD_REQUEST;
                }else{
                    resultData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LeaseSale> call, Throwable t) {
                errorMessage = t.getMessage();
                Log.e("MAKE_LEASESALE",t.getMessage());
            }
        });
        return resultData;
    }

    @Override
    public MutableLiveData<Sale> makeDirectSale(DirectSaleModel directSaleModel) {

        final MutableLiveData<Sale> data = new MutableLiveData<>();
        Call<Sale> sale = pandaCoreAPI.makeDirectSale(directSaleModel);

        sale.enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                saleResponse = response;
                if(!response.isSuccessful()){
                    errorMessage = Constants.BAD_REQUEST;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                errorMessage = t.getMessage();
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<List<Sale>> getSalesByAgent(String id, int page, int size, String sortby, String sortorder) {

        final MutableLiveData<List<Sale>> resultData = new MutableLiveData<>();
        Call<List<Sale>> sales = pandaCoreAPI.getAgentSales(id, page, size, sortby, sortorder);

        sales.enqueue(new Callback<List<Sale>>() {
            @Override
            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                if(!response.isSuccessful()){

                }else{
                    resultData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Sale>> call, Throwable t) {
                errorMessage = t.getMessage();
                Log.e("AGENT_SALES",t.getMessage());
            }
        });
        return resultData;
    }

    @Override
    public MutableLiveData<List<Sale>> getAllSales(int page, int size, String sortby, String sortorder) {

        final MutableLiveData<List<Sale>> resultData = new MutableLiveData<>();
        Call<List<Sale>> sales = pandaCoreAPI.getAllSales(page, size, sortby, sortorder);

        sales.enqueue(new Callback<List<Sale>>() {
            @Override
            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                if(!response.isSuccessful()){

                }else{
                    resultData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Sale>> call, Throwable t) {
                errorMessage = t.getMessage();
                Log.e("ALL_SALES",t.getMessage());
            }
        });
        return resultData;
    }

    @Override
    public Response getResponse() {
        return saleResponse;
    }
}
