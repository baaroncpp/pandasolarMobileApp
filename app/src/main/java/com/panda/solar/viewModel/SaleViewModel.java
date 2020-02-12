package com.panda.solar.viewModel;

import android.arch.lifecycle.ViewModel;

import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.SaleDAO;

public class SaleViewModel extends ViewModel {

    SaleDAO saleDAO = PandaDAOFactory.getSaleDAO();


}
