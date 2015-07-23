package com.allpay.tw.mobilesdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainPre extends Activity {
	
	Button btnForeGround, btnBackGround;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_pre);
		
		btnForeGround = (Button)findViewById(R.id.btnForeGround);
		btnBackGround = (Button)findViewById(R.id.btnBackGround);
		
		btnForeGround.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it2main = new Intent(MainPre.this, Main.class);
				startActivity(it2main);
			}
		});
		
		btnBackGround.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it2mainBackground = new Intent(MainPre.this, MainBackground.class);
				startActivity(it2mainBackground);
			}
		});
		
	}
	
}
