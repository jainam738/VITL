package com.appleto.Vitl.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.appleto.Vitl.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeInnerDetailsFragment extends Fragment implements View.OnClickListener {

    private Context context;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    ImageView iv_back;

    String itemid, itemname, itemimg, itemdiscountprice, itemdescription, address1, address2, unit;

    public HomeInnerDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_inner_details, container, false);


        itemid = getArguments().getString("itemid");
        itemname = getArguments().getString("itemname");
        itemimg = getArguments().getString("itemimage");
        itemdiscountprice = getArguments().getString("discountprice");
        itemdescription = getArguments().getString("description");
        address1 = getArguments().getString("address1");
        address2 = getArguments().getString("address2");
        unit = getArguments().getString("unitname");

        iv_back = view.findViewById(R.id.iv_back);
        // tv_buy = view.findViewById(R.id.tv_buy);

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);

        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        DescriptionFragment descriptionFragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("itemid", itemid);
        bundle.putString("itemname", itemname);
        bundle.putString("itemimage", itemimg);
        bundle.putString("discountprice", itemdiscountprice);
        bundle.putString("description", itemdescription);
        bundle.putString("address1", address1);
        bundle.putString("address2", address2);
        bundle.putString("unit", unit);
        descriptionFragment.setArguments(bundle);

        pagerAdapter.addFragment(descriptionFragment, "DESCRIPTION");
        pagerAdapter.addFragment(new DirectionFragment(), "DIRECTIONS");

        ReviewsFragment reviewsFragment = new ReviewsFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("itemid", itemid);
        bundle1.putString("itemimage", itemimg);
        reviewsFragment.setArguments(bundle);

        pagerAdapter.addFragment(reviewsFragment, "REVIEWS");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        iv_back.setOnClickListener(this);
        // tv_buy.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        if (view == iv_back) {

            getActivity().getSupportFragmentManager().popBackStack();

        }


    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        Bundle bundle;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            //  this.bundle=bundle;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
