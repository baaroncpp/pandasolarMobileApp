package com.panda.solar.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.SaleActivity;
import com.panda.solar.utils.Constants;


public class AddNewPlaceFragment extends Fragment implements View.OnClickListener {

    private EditText registerNewPlaceEditText;
    private TextView messageTextView;
    private Button registerNewPlaceButton;
    private String PLACE_NAME;
    private String message;
    private String hint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            PLACE_NAME = getArguments().getString(Constants.PLACE_NAME);
            if(Constants.DISTRICT_PLACE.equals(PLACE_NAME)){
                message = getString(R.string.messaage_new_place_registration, "district");
                hint = getString(R.string.hint_new_place_registration, "District");
            }
            else if(Constants.COUNTY_PLACE.equals(PLACE_NAME)){
                message = getString(R.string.messaage_new_place_registration, "county");
                hint = getString(R.string.hint_new_place_registration, "County");
            }
            else if(Constants.SUB_COUNTY_PLACE.equals(PLACE_NAME)){
                message = getString(R.string.messaage_new_place_registration, "sub-county");
                hint = getString(R.string.hint_new_place_registration, "Sub-County");
            }
            else if(Constants.VILLAGE_PLACE.equals(PLACE_NAME)){
                message = getString(R.string.messaage_new_place_registration, "village");
                hint = getString(R.string.hint_new_place_registration, "Village");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_place, container, false);

        registerNewPlaceButton = (Button)view.findViewById(R.id.register_new_place_button);
        registerNewPlaceEditText = (EditText)view.findViewById(R.id.new_place_name);
        messageTextView = (TextView) view.findViewById(R.id.message);

        registerNewPlaceEditText.setHint(hint);
        messageTextView.setText(message);

        registerNewPlaceButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() != null && ((SaleActivity)getActivity()).getSupportActionBar() != null)
            ((SaleActivity) getActivity()).getSupportActionBar().setTitle(R.string.register_new_place);
    }

    @Override
    public void onClick(View v) {
        if(getActivity() != null){
            getActivity().getSupportFragmentManager().popBackStackImmediate(SaleCustomerDetailFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
