package jp.techacademy.son.escape;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taiso on 2018/02/15.
 */


public class sendFragment extends Fragment {


    private EditText companyNameEditText;
    private EditText blackNameEditText;
    private EditText contentEditText;
    private EditText caseEditText;
    private EditText dateEditText;
    private EditText refEditText;
    private Button sendButton;
    private FirebaseUser user;
    private String companyName;
    private String blackName;
    private String date;
    private String content;
    private String cases;
    private String ref;
    private String variable;
    private TextView backTextView;

    DatabaseReference databaseReference;
    DatabaseReference contentsPathRef;
    String key;

    public watchFragment fragmentWatch;
    private ArrayList<articleData> mArticleDataArrayList = new ArrayList<articleData>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_send,container,false);

        companyNameEditText = (EditText)v.findViewById(R.id.companyNameEditText);
        blackNameEditText = (EditText)v.findViewById(R.id.blackNameEditText);
        dateEditText = (EditText)v.findViewById(R.id.dateEditText);
        contentEditText = (EditText)v.findViewById(R.id.contentEditText);
        caseEditText = (EditText)v.findViewById(R.id.caseEditText);
        refEditText = (EditText)v.findViewById(R.id.refEditText);
        sendButton = (Button)v.findViewById(R.id.sendButton);
        backTextView = (TextView)v.findViewById(R.id.backTextView);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        contentsPathRef = databaseReference.child(Const.ContentsPATH);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            MainActivity activity = (MainActivity) getActivity();
            activity.intentLogin();
        }

        backTextView.setClickable(true);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchFragment fragmentWatch = new watchFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentWatch,watchFragment.TAG)
                        .commit();
            }
        });





        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                //realtimeDatabaseに送るよー
                Map<String, String> data = new HashMap<String, String>();

                String mUid = user.getUid();


                companyName = companyNameEditText.getText().toString();
                blackName = blackNameEditText.getText().toString();
                date = dateEditText.getText().toString();
                cases = caseEditText.getText().toString();
                ref = refEditText.getText().toString();
                content = contentEditText.getText().toString();

                if (blackName.length()==0){
                    blackName="未入力";
                }
                if (content.length()==0){
                    content="未入力";
                }

                if(companyName.length()!=0){
                    if (date.length()!=0){
                        if (cases.length()!=0){
                            if (ref.length()!=0){
                                //Firebaseにデータ作成、データのkey取得
                                key = contentsPathRef.push().getKey();

                                data.put("mUid", mUid);
                                data.put("date", date);
                                data.put("companyName", companyName);
                                data.put("blackName", blackName);
                                data.put("content",content);
                                data.put("case", cases);
                                data.put("ref",ref);
                                data.put("key",key);

                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(key, data);

                                contentsPathRef.updateChildren(childUpdates);


                                //送信完了のダイアログ表示
                                watchFragment fragmentWatch = new watchFragment();
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.container,fragmentWatch,watchFragment.TAG)
                                        .commit();
                            }
                        }
                    }
                }


            }
        });
    }


}
