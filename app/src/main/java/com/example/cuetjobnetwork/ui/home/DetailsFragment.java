package com.example.cuetjobnetwork.ui.home;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cuetjobnetwork.R;
import com.example.cuetjobnetwork.model.DataController;
import com.example.cuetjobnetwork.model.JobPost;


public class DetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        //title_holder.setText(jobTitle);
        TextView job_title, posted_by, salary_tv, company_tv, skills, job_descrip, job_loc;
        job_title = view.findViewById(R.id.job_title_tv);
        posted_by = view.findViewById(R.id.posted_by_tv);
        salary_tv = view.findViewById(R.id.salary_tv);
        company_tv = view.findViewById(R.id.company_tv);
        skills = view.findViewById(R.id.skills_tv);
        job_descrip = view.findViewById(R.id.job_description_tv);
        job_loc = view.findViewById(R.id.job_location_tv);
        DataController controller = DataController.getInstance();

        JobPost current = controller.getCurrentJobpost();

       job_title.setText(current.getJobTitle());
        String temp = "posted by" + "<b> " + current.getPostedBy() + " </b>" + " on " + "<b>" + current.getDate() + "</b>";
        posted_by.setText(Html.fromHtml(temp));
        salary_tv.setText(current.getSalary()+ " BDT");
        company_tv.setText(current.getCompany());
        skills.setText(current.getJobSkill());
        job_descrip.setText(current.getJobDescrip());
        job_loc.setText(current.getJobLoc());



        return view;
    }
}