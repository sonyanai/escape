package jp.techacademy.son.escape;

/**
 * Created by taiso on 2018/02/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class thisFragment extends Fragment {

    public static final String TAG = "thisFragment";

    TextView companyTextView;
    TextView blackNameTextView;
    TextView caseTextView;
    TextView dateTextView;
    TextView refTextView;
    TextView contentTextView;
    TextView backText;
    String mUid;
    String date;
    String companyName;
    String blackName;
    String content;
    String cases;
    String ref;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_this,container,false);

        backText = (TextView)v.findViewById(R.id.backText);
        companyTextView = (TextView)v.findViewById(R.id.companyNameTextView);
        blackNameTextView = (TextView)v.findViewById(R.id.blackNameTextView);
        caseTextView = (TextView)v.findViewById(R.id.caseTextView);
        dateTextView = (TextView)v.findViewById(R.id.dateTextView);
        refTextView = (TextView)v.findViewById(R.id.refTextView);
        contentTextView = (TextView)v.findViewById(R.id.contentTextView);



        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        mUid = bundle.getString("mUid");
        date = bundle.getString("date");
        companyName = bundle.getString("companyName");
        blackName = bundle.getString("blackName");
        content = bundle.getString("content");
        cases = bundle.getString("cases");
        ref = bundle.getString("ref");
        key = bundle.getString("key");


        companyTextView.setText("会社名" + companyName);
        blackNameTextView.setText("社長名" + blackName);
        caseTextView.setText("事例\n" + cases);
        dateTextView.setText("発生年 " + date);
        refTextView.setText("参照元\n" + ref);
        contentTextView.setText("事案概要\n" + content);


        backText.setClickable(true);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchFragment fragmentWatch = new watchFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentWatch, watchFragment.TAG)
                        .commit();
            }
        });

    }
}
