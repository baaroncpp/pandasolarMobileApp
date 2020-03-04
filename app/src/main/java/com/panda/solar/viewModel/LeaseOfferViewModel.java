package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.LeaseOfferDAO;
import java.util.List;
import retrofit2.Response;

public class LeaseOfferViewModel extends ViewModel {

    private LeaseOfferDAO leaseOfferDAO = PandaDAOFactory.getLeaseOfferDAO();

    public LiveData<List<LeaseOffer>> getAllLeaseOffers(){
        return leaseOfferDAO.getAllLeaseOffers();
    }

    public Response getLeaseResponse(){
        return leaseOfferDAO.getLeaseResponse();
    }

}
