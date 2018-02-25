package jp.techacademy.son.escape;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;



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




        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            watchFragment fragmentWatch = new watchFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // 他にも、メソッドにはreplace removeがあります
            // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
            transaction.add(R.id.container, fragmentWatch);
            // 最後にcommitを使用することで変更を反映します
            transaction.commit();
        }else{
            permission();
        }



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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                        // 許可されている
                        if(user==null) {
                            intentLogin();
                        }else{
                            sendFragment fragmentSend = new sendFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            // 他にも、メソッドにはreplace removeがあります
                            // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
                            transaction.replace(R.id.container, fragmentSend);
                            // 最後にcommitを使用することで変更を反映します
                            transaction.commit();
                        }
                    } else {
                        // 許可されていないので許可ダイアログを表示する
                        requestPermissions(new String[]{Manifest.permission.INTERNET}, PERMISSIONS_REQUEST_CODE);
                    }
                }
        }
        return false;
    }

    public void intentLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void permission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                //Log.d("ANDROID", "許可されている");
            } else {
                //Log.d("ANDROID", "許可されていない");
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(new String[]{Manifest.permission.INTERNET}, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //許可された
                    intentLogin();
                } else {
                    //許可されなかった
                    dialog();

                }
                break;
            default:
                break;
        }
    }

    public void dialog(){
        // AlertDialog.Builderクラスを使ってAlertDialogの準備をする
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("");
        alertDialogBuilder.setMessage("インターネットへのアクセスを許可してください");

        // 肯定ボタンに表示される文字列、押したときのリスナーを設定する
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("UI_PARTS", "肯定ボタン");
                        permission();
                    }
                });
        // 否定ボタンに表示される文字列、押したときのリスナーを設定する
        alertDialogBuilder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("UI_PARTS", "否定ボタン");
                    }
                });


        // AlertDialogを作成して表示する
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

