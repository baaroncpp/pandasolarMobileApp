package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.ProductDAO;
import java.util.List;

public class ProductViewModel extends ViewModel {

    private MutableLiveData<List<Product>> products;
    private ProductDAO productDAO = PandaDAOFactory.getProductDAO();

    public LiveData<List<Product>> getProducts(){
        return productDAO.getAllProducts();
    }
}
