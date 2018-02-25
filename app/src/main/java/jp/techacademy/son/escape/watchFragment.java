package jp.techacademy.son.escape;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by taiso on 2018/02/15.
 */


public class watchFragment extends Fragment {

    public static final String TAG = "watchFragment";

    private AdView sAdView;


    private Button searchButton;
    private EditText searchEditText;
    private ImageButton closeButton;
    //内容とか入っているリスト
    public ArrayList<articleData> mArticleDataArrayList;
    public ArrayList<articleData> bArticleDataArrayList;
    private ArticleDataArrayListAdapter mAdapter;
    private ListView mListView;
    thisFragment fragmentThis;
    FirebaseUser user;
    DatabaseReference databaseReference;
    DatabaseReference contentsPathRef;
    //検索ワード
    String cord;




    //mEventListenerの設定と初期化
    ChildEventListener mEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();
            final String mUid = (String) map.get("mUid");
            final String date = (String) map.get("date");
            final String companyName = (String) map.get("companyName");
            final String blackName = (String) map.get("blackName");
            final String content = (String) map.get("content");
            final String cases = (String) map.get("case");
            final String ref = (String) map.get("ref");
            final String key = (String) map.get("key");
            articleData post = new articleData(mUid, date, companyName, blackName, content, cases, ref, key);
            mArticleDataArrayList.add(post);

            mAdapter.setArticleDataArrayList(mArticleDataArrayList);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }
        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }
        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_watch,container,false);

        MobileAds.initialize(this.getActivity(), "ca-app-pub-3940256099942544~6300978111");

        sAdView = (AdView) v.findViewById(R.id.adsView);
        AdRequest adRequest = new AdRequest.Builder().build();
        sAdView.loadAd(adRequest);


        searchButton = (Button)v.findViewById(R.id.searchButton);
        searchEditText = (EditText)v.findViewById(R.id.searchEditText);
        closeButton = (ImageButton)v.findViewById(R.id.closeButton);
        mArticleDataArrayList = new ArrayList<articleData>();
        bArticleDataArrayList = new ArrayList<articleData>();
        mAdapter = new ArticleDataArrayListAdapter(this.getActivity(), R.layout.new_list);
        mListView = (ListView)v.findViewById(R.id.articleDataListView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        contentsPathRef = databaseReference.child(Const.ContentsPATH);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mArticleDataArrayList.clear();
        contentsPathRef.addChildEventListener(mEventListener);




        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //検索ワードの取得
                cord=searchEditText.getText().toString();
                if (cord.length() > 0) {
                    bArticleDataArrayList.clear();
                    for (articleData aaa : mArticleDataArrayList){
                        if (aaa.getCompanyName().equals(cord)){
                            bArticleDataArrayList.add(aaa);
                            mAdapter.setArticleDataArrayList(bArticleDataArrayList);
                            mListView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mArticleDataArrayList.clear();
                searchEditText.getText().clear();
                contentsPathRef.addChildEventListener(mEventListener);
            }
        });

        //ListViewをタップした時
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("mUid", mArticleDataArrayList.get(position).getUid());
                bundle.putString("date", mArticleDataArrayList.get(position).getDate());
                bundle.putString("companyName", mArticleDataArrayList.get(position).getCompanyName());
                bundle.putString("blackName", mArticleDataArrayList.get(position).getBlackName());
                bundle.putString("content", mArticleDataArrayList.get(position).getContent());
                bundle.putString("cases", mArticleDataArrayList.get(position).getCase());
                bundle.putString("ref", mArticleDataArrayList.get(position).getRef());
                bundle.putString("key", mArticleDataArrayList.get(position).getKey());
                thisFragment fragmentThis = new thisFragment();
                fragmentThis.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentThis,thisFragment.TAG)
                        .commit();

            }
        });
/*
        // ListViewを長押ししたときの処理
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // お気に入りに追加

                return true;
            }
        });
*/


    }

}
