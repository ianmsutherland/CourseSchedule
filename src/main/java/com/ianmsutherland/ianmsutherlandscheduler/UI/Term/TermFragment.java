package com.ianmsutherland.ianmsutherlandscheduler.UI.Term;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.R;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;
import com.ianmsutherland.ianmsutherlandscheduler.ViewModel.TermViewModel;

import java.util.List;

public class TermFragment extends Fragment {
    private TermViewModel mTermViewModel;

    private String TAG = "SchedulerDatabaseLog";
    private Repository repository;

    private Term currentTerm;
    private int mId;
    private TermAdapter adapter;

    // create an interface that allows observer to use
    public interface OnTermSelectedListener {
        void onTermSelected(int termId);
    }

    private OnTermSelectedListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTermSelectedListener) {
            mListener = (OnTermSelectedListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                + " must implement OnTermSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_term, container, false);

        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        repository = new Repository(getActivity().getApplication());

        repository.getAllTerms(); // this is really just to setup the database if one isn't on the device

        RecyclerView recyclerView = view.findViewById(R.id.term_recycler_view);
        adapter = new TermAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTermViewModel.getAllTerms().observe(getViewLifecycleOwner(), new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.setTerms(terms);
            }
        });



        return view;
    }


    // when any list item is clicked
    private View.OnClickListener itemClickListener = new View.OnClickListener() {

        // send an intent to the Courses Activity
        @Override
        public void onClick(View view) {

            // grab the Term Id from the list's tag
            String termId = (String) view.getTag();

            // call this objects onTermSelected method
            mListener.onTermSelected((Integer.parseInt(termId)));
        }
    };


}
