package com.rajaryan.todo_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rajaryan.todo_list.manager.BlockChainManager;
import com.rajaryan.todo_list.manager.SharedPreferencesManager;
import com.rajaryan.todo_list.model.BlockModel;
import com.rajaryan.todo_list.utils.CypherUtils;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {
    private Context context;
    BlockChainManager blockChainManager;
    private boolean isEnc=true,isDark=true;
    RecyclerView rec;
    EditText message;
    Button send;
    private static final String TAG_POW_DIALOG="proof_of_work";
    private SharedPreferencesManager preferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencesManager= new SharedPreferencesManager(getApplicationContext());
        preferencesManager.setPowValue(2);
        isDark=preferencesManager.isDarkTheme();
        PowerManager powerManager=(PowerManager)getSystemService(POWER_SERVICE);
        boolean isPowerSavedMode=false;
        if(powerManager!=null){
            isPowerSavedMode=powerManager.isPowerSaveMode();
        }
        if(isPowerSavedMode){
            isPowerSavedMode=powerManager.isPowerSaveMode();
        }
        else {
            if(isDark){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        isEnc=preferencesManager.getEncryptionStatus();
        rec=findViewById(R.id.rec);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        new Thread(()->runOnUiThread(()->{
            blockChainManager=new BlockChainManager(preferencesManager.getPowValue(),this);
            rec.setAdapter(blockChainManager.adepter);
        })).start();
        message=findViewById(R.id.message);
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(()->{
                    if(blockChainManager!=null && message.getText().toString()!=null && rec.getAdapter()!=null){
                        String data=message.getText().toString();
                        if(!data.isEmpty()){
                            if(!isEnc){
                                blockChainManager.addBlock(blockChainManager.newBlock(data));
                            }
                            else {
                                try {
                                    blockChainManager.addBlock(blockChainManager.newBlock(CypherUtils.encryptIt(data).trim()));
                                }catch (Exception e){
                                    Toast.makeText(MainActivity.this,"Something",Toast.LENGTH_LONG).show();
                                }
                            }
                            rec.scrollToPosition(blockChainManager.adepter.getItemCount()-1);
                            if(blockChainManager.isBlockChainValid()){
                                rec.getAdapter().notifyDataSetChanged();
                                message.setText("");
                            }else {
                                Toast.makeText(MainActivity.this,"Blockchain Corrupted",Toast.LENGTH_LONG).show();
                            }

                        }
                        else {
                            Toast.makeText(MainActivity.this,"Error Empty Data",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}