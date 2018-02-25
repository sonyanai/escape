package jp.techacademy.son.escape;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {



    public sendFragment fragmentSend;
    public watchFragment fragmentWatch;
    private FirebaseUser user;
    private AdView mAdView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~6300978111");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        watchFragment fragmentWatch = new watchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 他にも、メソッドにはreplace removeがあります
        // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
        transaction.add(R.id.container, fragmentWatch);
        // 最後にcommitを使用することで変更を反映します
        transaction.commit();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addButton:
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(user==null){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    sendFragment fragmentSend = new sendFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // 他にも、メソッドにはreplace removeがあります
                    // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
                    transaction.replace(R.id.container, fragmentSend);
                    // 最後にcommitを使用することで変更を反映します
                    transaction.commit();
                }
        }
        return false;
    }

    public void intentLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}

