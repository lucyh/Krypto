package com.hci.krypto;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class KryptoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krypto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_krypto, menu);
        return true;
    }
}
