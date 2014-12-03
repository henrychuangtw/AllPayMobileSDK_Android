package com.allpay.tw.mobilesdk.demo;

import com.allpay.tw.mobilesdk.API_ATM;
import com.allpay.tw.mobilesdk.API_Base;
import com.allpay.tw.mobilesdk.API_CVS;
import com.allpay.tw.mobilesdk.API_Credit;
import com.allpay.tw.mobilesdk.AllpayAsyncTask;
import com.allpay.tw.mobilesdk.AllpayBackgroundTaskCompleted;
import com.allpay.tw.mobilesdk.BANKNAME;
import com.allpay.tw.mobilesdk.BackgroundATM;
import com.allpay.tw.mobilesdk.BackgroundCVS;
import com.allpay.tw.mobilesdk.BackgroundCredit;
import com.allpay.tw.mobilesdk.BackgroundOTP;
import com.allpay.tw.mobilesdk.ENVIRONMENT;
import com.allpay.tw.mobilesdk.STORETYPE;
import com.allpay.tw.mobilesdk.PAYMENTTYPE;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainBackground extends Activity implements AllpayBackgroundTaskCompleted {
	
	Button btnATM, btnCVS, btnCredit, btnOTP, btnCreditInstall;
	Spinner spnATM, spnCVS;
	View layoutMenu, layoutOTP;
	EditText edtOTP, edtCreditPhone, edtCreditInstallmentPhone;
	TextView txtOtpExpiredTime;
	ProgressDialog pd;
	
	String OtpMerchantID, OtpMerchantTradeNo, OtpTradeNo;
	
	boolean isCounterPayIN = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_background);
		setTitle("�q�沣��(����)");
		
		btnATM = (Button)findViewById(R.id.btnBackgroundATM);
		btnCVS = (Button)findViewById(R.id.btnBackgroundCVS);
		btnCredit = (Button)findViewById(R.id.btnBackgroundCredit);
		spnATM = (Spinner)findViewById(R.id.spinner_ATM_background);
		spnCVS = (Spinner)findViewById(R.id.spinner_CVS_background);
		layoutMenu = (View) findViewById(R.id.layout_menu);
		layoutOTP = (View)findViewById(R.id.layout_otp);
		btnOTP = (Button)findViewById(R.id.btnOTP);
		edtOTP = (EditText)findViewById(R.id.edtOTP);
		txtOtpExpiredTime = (TextView)findViewById(R.id.txtOtpExpiredTime);
		btnCreditInstall = (Button)findViewById(R.id.btnBackgroundCreditInstallment);
		edtCreditPhone = (EditText)findViewById(R.id.edt_credit_phone);
		edtCreditInstallmentPhone = (EditText)findViewById(R.id.edt_creditInstallment_phone);
		
		btnATM.setOnClickListener(clk_Listener);
		btnCVS.setOnClickListener(clk_Listener);
		btnCredit.setOnClickListener(clk_Listener);
		btnOTP.setOnClickListener(clk_Listener);
		btnCreditInstall.setOnClickListener(clk_Listener);
		
		
		ArrayAdapter<BANKNAME> adpATM = new ArrayAdapter<BANKNAME>(MainBackground.this, android.R.layout.simple_spinner_item, Config.lstBankName);
		adpATM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnATM.setAdapter(adpATM);
		
		ArrayAdapter<STORETYPE> adpCVS = new ArrayAdapter<STORETYPE>(MainBackground.this, android.R.layout.simple_spinner_item, Config.lstStoreType);
		adpCVS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnCVS.setAdapter(adpCVS);
		
		
		pd = new ProgressDialog(MainBackground.this);
		pd.setTitle("����");
		pd.setMessage("����B�z��");
	}
	
	
	Button.OnClickListener clk_Listener = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int iBtnID = v.getId();
			
			if(iBtnID == R.id.btnBackgroundATM){
				BANKNAME bankName =  BANKNAME.parse2BankName(spnATM.getSelectedItem().toString());
				if(bankName == BANKNAME.ESUN_Counter)
					isCounterPayIN = true;
				else 
					isCounterPayIN = false;
				
				//************** �SPlatformID ******************
//				BackgroundATM oBackgroundATM = new BackgroundATM(Config.MerchantID_test, 			//�t�ӽs��
//																 Config.AppCode_test, 				//App�N�X
//																 Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//																 Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
//																 Config.TotalAmount_test, 			//������B
//																 Config.TradeDesc_test, 			//����y�z
//																 Config.ItemName_test, 				//�ӫ~�W��
//																 PAYMENTTYPE.ATM, 					//�I�ڤ覡
//																 ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
//																 bankName, 							//�Ȧ�N�X
//																 3);								//��ܩʰѼơA���\ú�O���ĤѼ�(1~60)
				
				//************** ��PlatformID ******************
//				BackgroundATM oBackgroundATM = new BackgroundATM(Config.MerchantID_test, 			//�t�ӽs��
//																 Config.PlatformID_test,			//�S���X�@���x�ӥN��
//																 Config.AppCode_PlatformID_test, 	//App�N�X
//																 Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//																 Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
//																 Config.TotalAmount_test, 			//������B
//																 Config.TradeDesc_test, 			//����y�z
//																 Config.ItemName_test, 				//�ӫ~�W��
//																 PAYMENTTYPE.ATM, 					//�I�ڤ覡
//																 ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
//																 BANKNAME.parse2BankName(spnATM.getSelectedItem().toString()), 				//�Ȧ�N�X
//																 3);
				
				//************** ��PlatformID�B����O ******************
				BackgroundATM oBackgroundATM = new BackgroundATM(Config.MerchantID_test, 			//�t�ӽs��
																 Config.PlatformID_test,			//�S���X�@���x�ӥN��
																 Config.PlatformChargeFee_test,		//�S���X�@���x�Ӥ���O
																 Config.AppCode_PlatformID_test, 	//App�N�X
																 Config.getMerchantTradeNo(), 		//�t�ӥ���s��
																 Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
																 Config.TotalAmount_test, 			//������B
																 Config.TradeDesc_test, 			//����y�z
																 Config.ItemName_test, 				//�ӫ~�W��
																 PAYMENTTYPE.ATM, 					//�I�ڤ覡
																 ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
																 BANKNAME.parse2BankName(spnATM.getSelectedItem().toString()), 				//�Ȧ�N�X
																 3);
								
				//�ϥ�ProgressDialog
				new AllpayAsyncTask<BackgroundATM, API_ATM>(MainBackground.this, API_ATM.class, pd).execute(oBackgroundATM);
				//���ϥ�ProgressDialog
//				new AllpayAsyncTask<BackgroundATM, API_ATM>(MainBackground.this, API_ATM.class).execute(oBackgroundATM);
				
			}else if(iBtnID == R.id.btnBackgroundCVS){
				//************** �SPlatformID ******************
//				BackgroundCVS oBackgroundCVS = new BackgroundCVS(Config.MerchantID_test, 			//�t�ӽs��
//																 Config.AppCode_test, 				//App�N�X
//																 Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//																 Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
//																 Config.TotalAmount_test, 			//������B
//																 Config.TradeDesc_test, 			//����y�z
//																 Config.ItemName_test, 				//�ӫ~�W��
//																 PAYMENTTYPE.CVS, 					//�I�ڤ覡
//																 ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
//																 STORETYPE.parse2StoreType(spnCVS.getSelectedItem().toString()),					//�W�ӥN�X
//																 "����1", 							//����y�z1
//																 "����2");
				
				//************** ��PlatformID ******************
//				BackgroundCVS oBackgroundCVS = new BackgroundCVS(Config.MerchantID_test, 			//�t�ӽs��
//																 Config.PlatformID_test,			//�S���X�@���x�ӥN��
//																 Config.AppCode_PlatformID_test, 	//App�N�X
//																 Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//																 Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
//																 Config.TotalAmount_test, 			//������B
//																 Config.TradeDesc_test, 			//����y�z
//																 Config.ItemName_test, 				//�ӫ~�W��
//																 PAYMENTTYPE.CVS, 					//�I�ڤ覡
//																 ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
//																 STORETYPE.parse2StoreType(spnCVS.getSelectedItem().toString()),					//�W�ӥN�X
//																 "����1", 							//����y�z1
//																 "����2");
				
				//************** ��PlatformID�B����O ******************
				BackgroundCVS oBackgroundCVS = new BackgroundCVS(Config.MerchantID_test, 			//�t�ӽs��
																 Config.PlatformID_test,			//�S���X�@���x�ӥN��
																 Config.PlatformChargeFee_test,		//�S���X�@���x�Ӥ���O
																 Config.AppCode_PlatformID_test, 	//App�N�X
																 Config.getMerchantTradeNo(), 		//�t�ӥ���s��
																 Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
																 Config.TotalAmount_test, 			//������B
																 Config.TradeDesc_test, 			//����y�z
																 Config.ItemName_test, 				//�ӫ~�W��
																 PAYMENTTYPE.CVS, 					//�I�ڤ覡
																 ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
																 STORETYPE.parse2StoreType(spnCVS.getSelectedItem().toString()),					//�W�ӥN�X
																 "����1", 							//����y�z1
																 "����2");
				
				//�ϥ�ProgressDialog
				new AllpayAsyncTask<BackgroundCVS, API_CVS>(MainBackground.this, API_CVS.class, pd).execute(oBackgroundCVS);
				//���ϥ�ProgressDialog
//				new AllpayAsyncTask<BackgroundCVS, API_CVS>(MainBackground.this, API_CVS.class).execute(oBackgroundCVS);
				
			}else if(iBtnID == R.id.btnBackgroundCredit){
				String PhoneNumber = edtCreditPhone.getText().toString();
				if(!PhoneNumber.matches("[0][9][0-9]{2}[0-9]{6}")){
					Toast.makeText(MainBackground.this, "�п�J���T��������X", Toast.LENGTH_SHORT).show();
					return;
				}
				
				//************** �SPlatformID ******************
//				BackgroundCredit oBackgroundCredit = new BackgroundCredit(Config.MerchantID_test, 			//�t�ӽs�� 
//																		  Config.AppCode_test, 				//App�N�X 
//																		  Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//																		  Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
//																		  Config.TotalAmount_test, 			//������B
//																		  Config.TradeDesc_test, 			//����y�z
//																		  Config.ItemName_test, 			//�ӫ~�W��
//																		  PAYMENTTYPE.CREDIT, 				//�I�ڤ覡
//																		  ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
//																		  "��Q", 							//���d�H�m�W
//																		  PhoneNumber, 						//������X
//																		  "4311952222222222", 				//�H�Υd�d��
//																		  "204012", 						//���Ħ~��
//																		  "222");							//�d�����T�X
				
				//************** ��PlatformID ******************
//				BackgroundCredit oBackgroundCredit = new BackgroundCredit(Config.MerchantID_test, 			//�t�ӽs�� 
//																		  Config.PlatformID_test,			//�S���X�@���x�ӥN��
//																		  Config.AppCode_PlatformID_test, 	//App�N�X 
//																		  Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//																		  Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
//																		  Config.TotalAmount_test, 			//������B
//																		  Config.TradeDesc_test, 			//����y�z
//																		  Config.ItemName_test, 			//�ӫ~�W��
//																		  PAYMENTTYPE.CREDIT, 				//�I�ڤ覡
//																		  ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
//																		  "��Q", 							//���d�H�m�W
//																		  PhoneNumber, 						//������X
//																		  "4311952222222222", 				//�H�Υd�d��
//																		  "204012", 						//���Ħ~��
//																		  "222");							//�d�����T�X
				
				//************** ��PlatformID�B����O ******************
				BackgroundCredit oBackgroundCredit = new BackgroundCredit(Config.MerchantID_test, 			//�t�ӽs�� 
																		  Config.PlatformID_test,			//�S���X�@���x�ӥN��
																		  Config.PlatformChargeFee_test,	//�S���X�@���x�Ӥ���O
																		  Config.AppCode_PlatformID_test, 	//App�N�X 
																		  Config.getMerchantTradeNo(), 		//�t�ӥ���s��
																		  Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
																		  Config.TotalAmount_test, 			//������B
																		  Config.TradeDesc_test, 			//����y�z
																		  Config.ItemName_test, 			//�ӫ~�W��
																		  PAYMENTTYPE.CREDIT, 				//�I�ڤ覡
																		  ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
																		  "��Q", 							//���d�H�m�W
																		  PhoneNumber, 						//������X
																		  "4311952222222222", 				//�H�Υd�d��
																		  "204012", 						//���Ħ~��
																		  "222");							//�d�����T�X
				
				//�ϥ�ProgressDialog
				new AllpayAsyncTask<BackgroundCredit, API_Credit>(MainBackground.this, API_Credit.class, pd).execute(oBackgroundCredit);
				//���ϥ�ProgressDialog
//				new AllpayAsyncTask<BackgroundCredit, API_Credit>(MainBackground.this, API_Credit.class).execute(oBackgroundCredit);
				
			}else if(iBtnID == R.id.btnBackgroundCreditInstallment){
				String PhoneNumber = edtCreditInstallmentPhone.getText().toString();
				if(!PhoneNumber.matches("[0][9][0-9]{2}[0-9]{6}")){
					Toast.makeText(MainBackground.this, "�п�J���T��������X", Toast.LENGTH_SHORT).show();
					return;
				}
				
				//************** �SPlatformID ******************
//				BackgroundCredit oBackgroundCredit = new BackgroundCredit(
//						  Config.MerchantID_test, 			//�t�ӽs�� 
//						  Config.AppCode_test, 				//App�N�X 
//						  Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//						  Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
//						  Config.TotalAmount_test, 			//������B
//						  Config.TradeDesc_test, 			//����y�z
//						  Config.ItemName_test, 			//�ӫ~�W��
//						  PAYMENTTYPE.CREDIT, 				//�I�ڤ覡
//						  ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
//						  "��Q", 							//���d�H�m�W
//						  PhoneNumber, 						//������X
//						  "4311952222222222", 				//�H�Υd�d��
//						  "204012", 						//���Ħ~��
//						  "222",							//�d�����T�X
//						  3,								//��������
//						  0,								//�������I�ڪ��B
//						  false);							//�O�_�ϥά��Q���
				
				//************** ��PlatformID ******************
//				BackgroundCredit oBackgroundCredit = new BackgroundCredit(
//						  Config.MerchantID_test, 			//�t�ӽs��
//						  Config.PlatformID_test,			//�S���X�@���x�ӥN��
//						  Config.AppCode_PlatformID_test, 	//App�N�X 
//						  Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//						  Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
//						  Config.TotalAmount_test, 			//������B
//						  Config.TradeDesc_test, 			//����y�z
//						  Config.ItemName_test, 			//�ӫ~�W��
//						  PAYMENTTYPE.CREDIT, 				//�I�ڤ覡
//						  ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
//						  "��Q", 							//���d�H�m�W
//						  PhoneNumber, 						//������X
//						  "4311952222222222", 				//�H�Υd�d��
//						  "204012", 						//���Ħ~��
//						  "222",							//�d�����T�X
//						  3,								//��������
//						  0,								//�������I�ڪ��B
//						  false);							//�O�_�ϥά��Q���
				
				//************** ��PlatformID�B����O ******************
				BackgroundCredit oBackgroundCredit = new BackgroundCredit(
						  Config.MerchantID_test, 			//�t�ӽs��
						  Config.PlatformID_test,			//�S���X�@���x�ӥN��
						  Config.PlatformChargeFee_test,	//�S���X�@���x�Ӥ���O
						  Config.AppCode_PlatformID_test, 	//App�N�X 
						  Config.getMerchantTradeNo(), 		//�t�ӥ���s��
						  Config.getMerchantTradeDate(), 	//�t�ӥ���ɶ�
						  Config.TotalAmount_test, 			//������B
						  Config.TradeDesc_test, 			//����y�z
						  Config.ItemName_test, 			//�ӫ~�W��
						  PAYMENTTYPE.CREDIT, 				//�I�ڤ覡
						  ENVIRONMENT.STAGE, 				//�������� : STAGE�����աAOFFICIAL������
						  "��Q", 							//���d�H�m�W
						  PhoneNumber, 						//������X
						  "4311952222222222", 				//�H�Υd�d��
						  "204012", 						//���Ħ~��
						  "222",							//�d�����T�X
						  3,								//��������
						  0,								//�������I�ڪ��B
						  false);							//�O�_�ϥά��Q���
				
				
				//�ϥ�ProgressDialog
				new AllpayAsyncTask<BackgroundCredit, API_Credit>(MainBackground.this, API_Credit.class, pd).execute(oBackgroundCredit);
				//���ϥ�ProgressDialog
//				new AllpayAsyncTask<BackgroundCredit, API_Credit>(MainBackground.this, API_Credit.class).execute(oBackgroundCredit);

			}else if(iBtnID == R.id.btnOTP){
				BackgroundOTP oBackgroundOTP = new BackgroundOTP(OtpMerchantID, 
																 OtpMerchantTradeNo, 
																 OtpTradeNo, 
																 edtOTP.getText().toString(), 
																 ENVIRONMENT.STAGE);
				
				//�ϥ�ProgressDialog
				new AllpayAsyncTask<BackgroundOTP, API_Credit>(MainBackground.this, API_Credit.class, pd).execute(oBackgroundOTP);
				//���ϥ�ProgressDialog
//				new AllpayAsyncTask<BackgroundOTP, API_Credit>(MainBackground.this, API_Credit.class).execute(oBackgroundOTP);
				
			}
			
		}
		
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (layoutOTP.getVisibility() == View.VISIBLE) {
				layoutOTP.setVisibility(View.GONE);
				layoutMenu.setVisibility(View.VISIBLE);
				
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onAllpayBackgroundTaskCompleted(API_Base oApi_Base) {
		// TODO Auto-generated method stub
		String sResult = "";
		
		if(oApi_Base.RtnCode == 2){//ATM �������\ or �{�dú��	
			API_ATM oApi_ATM = (API_ATM)oApi_Base;
			if(isCounterPayIN){
				sResult = "�{�dú��\n\n" + oApi_ATM.toString();
			}else{
				sResult = "ATM �������\\n\n" + oApi_ATM.toString();
			}
						
		}else if(oApi_Base.RtnCode == 10100073){//�W�Ө������\
			API_CVS oApi_CVS = (API_CVS)oApi_Base;
			sResult = "CVS �������\\n\n" + oApi_CVS.toString();
			
		}else if(oApi_Base.RtnCode == 3){//�H�ΥdOTP����
			edtOTP.setText("");
			layoutOTP.setVisibility(View.VISIBLE);
			layoutMenu.setVisibility(View.GONE);		
			
			API_Credit oApi_Credit = (API_Credit)oApi_Base;
			OtpMerchantID = oApi_Credit.MerchantID;
			OtpMerchantTradeNo = oApi_Credit.MerchantTradeNo;
			OtpTradeNo = oApi_Credit.TradeNo;
			txtOtpExpiredTime.setText("���ҽX����ɶ��G" + oApi_Credit.OtpExpiredTime);						
			return;
			
		}else if(oApi_Base.RtnCode == 10000030){//���ҽX���~�A�ЦA��J�@��
			Toast.makeText(MainBackground.this, oApi_Base.RtnMsg, Toast.LENGTH_SHORT).show();
			edtOTP.setText("");
			
			return;
			
		}else if(oApi_Base.RtnCode == 1){//�H�Υd�I�ڦ��\		
			API_Credit oApi_Credit = (API_Credit)oApi_Base;
			sResult = "Credit �I�ڦ��\\n\n" + oApi_Credit.toString();
			
		}else{			
			sResult = oApi_Base.toString();
		}
		
		
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				layoutOTP.setVisibility(View.GONE);
				layoutMenu.setVisibility(View.VISIBLE);	
			}
		}, 1000);
		
		
		Intent it2result = new Intent(MainBackground.this, Result.class);
		it2result.putExtra(Result.EXTRA_RESULT, sResult);
		
		startActivity(it2result);
		
	}
	
	Handler handler = new Handler();
	
	
}
