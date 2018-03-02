package jp.techacademy.son.escape;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by taiso on 2018/03/02.
 */

public class rewriteFragment extends Fragment {

    public static final String TAG = "rewriteFragment";

    EditText companyEditText;
    EditText blackNameEditText;
    EditText caseEditText;
    EditText dateEditText;
    EditText refEditText;
    EditText contentEditText;
    TextView returnTextView;
    TextView okTextView;
    String mUid;
    String date;
    String companyName;
    String blackName;
    String content;
    String cases;
    String ref;
    String key;
    String reDate;
    String reCompanyName;
    String reBlackName;
    String reContent;
    String reCases;
    String reRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_rewrite,container,false);

        returnTextView = (TextView)v.findViewById(R.id.returnTextView);
        okTextView = (TextView)v.findViewById(R.id.okTextView);
        companyEditText = (EditText)v.findViewById(R.id.companyNameEditText);
        blackNameEditText = (EditText)v.findViewById(R.id.blackNameEditText);
        caseEditText = (EditText)v.findViewById(R.id.caseEditText);
        dateEditText = (EditText)v.findViewById(R.id.dateEditText);
        refEditText = (EditText)v.findViewById(R.id.refEditText);
        contentEditText = (EditText)v.findViewById(R.id.contentEditText);



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


        companyEditText.setText(companyName);
        blackNameEditText.setText(blackName);
        caseEditText.setText(cases);
        dateEditText.setText(date);
        refEditText.setText(ref);
        contentEditText.setText(content);


        returnTextView.setClickable(true);
        returnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("mUid", mUid);
                bundle.putString("date", date);
                bundle.putString("companyName", companyName);
                bundle.putString("blackName", blackName);
                bundle.putString("content", content);
                bundle.putString("cases", cases);
                bundle.putString("ref", ref);
                bundle.putString("key", key);


                thisFragment fragmentThis = new thisFragment();
                fragmentThis.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentThis,thisFragment.TAG)
                        .commit();
            }
        });





        okTextView.setClickable(true);
        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reDate = dateEditText.getText().toString();
                reCompanyName = companyEditText.getText().toString();
                reBlackName = blackNameEditText.getText().toString();
                reContent = contentEditText.getText().toString();
                reCases = caseEditText.getText().toString();





                Bundle bundle = new Bundle();
                bundle.putString("mUid", mUid);
                bundle.putString("date", reDate);
                bundle.putString("companyName", reCompanyName);
                bundle.putString("blackName", reBlackName);
                bundle.putString("content", reContent);
                bundle.putString("cases", reCases);
                bundle.putString("ref", reRef);
                bundle.putString("key", key);



                //データの再登録，完了時にスナップバーなどで通知する




                thisFragment fragmentThis = new thisFragment();
                fragmentThis.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentThis,thisFragment.TAG)
                        .commit();


            }
        });

    }
}
