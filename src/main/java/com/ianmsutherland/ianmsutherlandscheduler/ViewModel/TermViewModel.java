package com.ianmsutherland.ianmsutherlandscheduler.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ianmsutherland.ianmsutherlandscheduler.Database.Repository;
import com.ianmsutherland.ianmsutherlandscheduler.Entities.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Term>> mAllTerms;
    private Term mTerm;
    int termId;

    public TermViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        mAllTerms = repository.getAllTerms();
        mTerm = repository.getTerm(termId);
    }

    public Term getTerm(int termId) {
        return mTerm;
    }

    public LiveData<List<Term>> getAllTerms() {
        return mAllTerms;
    }
}
