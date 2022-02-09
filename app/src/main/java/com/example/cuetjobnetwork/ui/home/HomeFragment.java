package com.example.cuetjobnetwork.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuetjobnetwork.R;
import com.example.cuetjobnetwork.databinding.FragmentHomeBinding;
import com.example.cuetjobnetwork.model.DataController;
import com.example.cuetjobnetwork.model.JobInterface;
import com.example.cuetjobnetwork.model.JobPost;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment implements JobInterface {


    RecyclerView recyclerView;
    SearchView searchView;
    MyAdapter adapter;
    ExtendedFloatingActionButton fab;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_recfragment, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recview);
        searchView = (SearchView) view.findViewById(R.id.search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // importing data
        FirebaseRecyclerOptions<JobPost> options =
                new FirebaseRecyclerOptions.Builder<JobPost>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("jobposts"), JobPost.class)
                        .build();


        adapter = new MyAdapter(options,this);
        recyclerView.setAdapter(adapter);


        //search.............................................
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }



            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    private void processSearch(String query) {
        //custom query
        String queryLower = query.toLowerCase();
        String queryUpper = query.toUpperCase();
        FirebaseRecyclerOptions<JobPost> options1 = new FirebaseRecyclerOptions.Builder<JobPost>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("jobposts")
                        .orderByChild("jobTitle")
                        .startAt(query).endAt(query+"\uf8ff"), JobPost.class)
                .build();
        adapter = new MyAdapter(options1,this);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onJobClick(JobPost post) {
        DataController controller ;
        controller= DataController.getInstance();
        controller.setCurrentJobpost(post);
        Navigation.findNavController(view).navigate(R.id.details_fragment);
    }
}