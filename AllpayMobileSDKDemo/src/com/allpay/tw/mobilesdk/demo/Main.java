package com.allpay.tw.mobilesdk.demo;


import com.allpay.tw.mobilesdk.BANKNAME;
import com.allpay.tw.mobilesdk.CREDITTYPE;
import com.allpay.tw.mobilesdk.CreateTrade;
import com.allpay.tw.mobilesdk.ENVIRONMENT;
import com.allpay.tw.mobilesdk.OptionalATM;
import com.allpay.tw.mobilesdk.OptionalCVS;
import com.allpay.tw.mobilesdk.OptionalCreditInstallment;
import com.allpay.tw.mobilesdk.OptionalCreditPeriodAmount;
import com.allpay.tw.mobilesdk.PAYMENTTYPE;
import com.allpay.tw.mobilesdk.PERIODTYPE;
import com.allpay.tw.mobilesdk.PaymentActivity;
import com.allpay.tw.mobilesdk.STORETYPE;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Main extends Activity {
	
	Button btnAll, btnATM, btnCVS, btnCredit, btnCreditInstallment, btnCreditPeriodAmount;
	Spinner spnATM, spnCVS;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("�q�沣��(���e)");
		
		btnAll = (Button)findViewById(R.id.btnAll);
		btnATM = (Button)findViewById(R.id.btnATM);
		btnCVS = (Button)findViewById(R.id.btnCVS);
		btnCredit = (Button)findViewById(R.id.btnCredit);
		btnCreditInstallment = (Button)findViewById(R.id.btnCreditInstallment);
		btnCreditPeriodAmount = (Button)findViewById(R.id.btnCreditPeriodAmount);		
		spnATM = (Spinner)findViewById(R.id.spinner_ATM);
		spnCVS = (Spinner)findViewById(R.id.spinner_CVS);
		
		btnAll.setOnClickListener(clk_Listener);
		btnATM.setOnClickListener(clk_Listener);
		btnCVS.setOnClickListener(clk_Listener);
		btnCredit.setOnClickListener(clk_Listener);
		btnCreditInstallment.setOnClickListener(clk_Listener);
		btnCreditPeriodAmount.setOnClickListener(clk_Listener);
		
		ArrayAdapter<String> adpATM = new ArrayAdapter<String>(Main.this,  android.R.layout.simple_spinner_item, Config.aryBankName);
		adpATM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnATM.setAdapter(adpATM);
		
		ArrayAdapter<String> adpCVS = new ArrayAdapter<String>(Main.this, android.R.layout.simple_spinner_item, Config.aryStoreType);
		adpCVS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnCVS.setAdapter(adpCVS);
		
	}
	
	Button.OnClickListener clk_Listener = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub		
			PAYMENTTYPE paymentType = null;
			Intent intent = new Intent(Main.this, PaymentActivity.class);
			
			int iBtnID = v.getId();
			if(iBtnID == R.id.btnAll){
				paymentType = PAYMENTTYPE.ALL;				
				
			}else if(iBtnID == R.id.btnATM){
				//�۰��d����(ATM)
				paymentType = PAYMENTTYPE.ATM;
				
				//��ܩʰѼơA���\ú�O���ĤѼ�(1~60)
//				OptionalATM oOptionalATM = new OptionalATM(7);
				OptionalATM oOptionalATM = new OptionalATM(7, BANKNAME.parse2BankName(spnATM.getSelectedItem().toString()));
				intent.putExtra(PaymentActivity.EXTRA_OPTIONAL, oOptionalATM);	
				
			}else if(iBtnID == R.id.btnCVS){
				//�W�ӥN�X(CVS)
				paymentType = PAYMENTTYPE.CVS;
				
				//��ܩʰѼơA����y�z1~4�A�|�X�{�b�W��ú�O���x�ù��W
//				OptionalCVS oOptionalCVS = new OptionalCVS("����1", "����2", "", "", null);
				OptionalCVS oOptionalCVS = new OptionalCVS("����1", "����2", "", "", STORETYPE.parse2StoreType(spnCVS.getSelectedItem().toString()));
				intent.putExtra(PaymentActivity.EXTRA_OPTIONAL, oOptionalCVS);
				
			}else if(iBtnID == R.id.btnCredit){	
				//�H�Υd			
				paymentType = PAYMENTTYPE.CREDIT;
				
			}else if(iBtnID == R.id.btnCreditInstallment){	
				//�H�Υd(����)
				paymentType = PAYMENTTYPE.CREDIT;
				intent.putExtra(PaymentActivity.EXTRA_OPTIONAL_CREDITTYPE, CREDITTYPE.INSTALLMENT);
				
				OptionalCreditInstallment oCreditInstallment = new OptionalCreditInstallment(3,			//�������� 
																							 0, 		//�������I�ڪ��B
																							 false, 	//�O�_�ϥά��Q���
																							 false);	//�O�_�����p�d���(�ݦV�ڥI�_���X�ӽСA�~��ϥ�)
				intent.putExtra(PaymentActivity.EXTRA_OPTIONAL, oCreditInstallment);
			}else if(iBtnID == R.id.btnCreditPeriodAmount){
				//�H�Υd(�w���w�B)
				paymentType = PAYMENTTYPE.CREDIT;
				intent.putExtra(PaymentActivity.EXTRA_OPTIONAL_CREDITTYPE, CREDITTYPE.PERIODAMOUNT);
				
				//EX:�C�Ӥ릩1��100���A�`�@�n��12��
				OptionalCreditPeriodAmount oPeriodAmount = new OptionalCreditPeriodAmount(Config.TotalAmount_test,	//�C�����v���B(���ѼƦ��]�w���B�A�ݻPTotalAmount�ۦP) 
																						  PERIODTYPE.MONTH, 		//�g������
																						  1, 						//�����W�v
																						  12);						//����Ѽ�
				intent.putExtra(PaymentActivity.EXTRA_OPTIONAL, oPeriodAmount);
			}
			
			//************** �SPlatformID ******************
//			CreateTrade oCreateTrade = new CreateTrade(
//					Config.MerchantID_test,				//�t�ӽs�� 
//					Config.AppCode_test, 				//App�N�X
//					Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//					Config.getMerchantTradeDate(), 		//�t�ӥ���ɶ�
//					Config.TotalAmount_test, 			//������B
//					Config.TradeDesc_test, 				//����y�z
//					Config.ItemName_test, 				//�ӫ~�W��
//					paymentType, 						//�w�]�I�ڤ覡
//					ENVIRONMENT.STAGE);					//�������� : STAGE�����աAOFFICIAL������
			
//			//************** ��PlatformID ******************
//			CreateTrade oCreateTrade = new CreateTrade(
//					Config.MerchantID_test,				//�t�ӽs�� 
//					Config.PlatformID_test,				//�S���X�@���x�ӥN��
//					Config.AppCode_PlatformID_test, 	//App�N�X
//					Config.getMerchantTradeNo(), 		//�t�ӥ���s��
//					Config.getMerchantTradeDate(), 		//�t�ӥ���ɶ�
//					Config.TotalAmount_test, 			//������B
//					Config.TradeDesc_test, 				//����y�z
//					Config.ItemName_test, 				//�ӫ~�W��
//					paymentType, 						//�w�]�I�ڤ覡
//					ENVIRONMENT.STAGE);					//�������� : STAGE�����աAOFFICIAL������
			
			//************** ��PlatformID�B����O ******************
			CreateTrade oCreateTrade = new CreateTrade(
					Config.MerchantID_test,				//�t�ӽs�� 
					Config.PlatformID_test,				//�S���X�@���x�ӥN��
					Config.PlatformChargeFee_test,		//�S���X�@���x�Ӥ���O
					Config.AppCode_PlatformID_test, 	//App�N�X
					Config.getMerchantTradeNo(), 		//�t�ӥ���s��
					Config.getMerchantTradeDate(), 		//�t�ӥ���ɶ�
					Config.TotalAmount_test, 			//������B
					Config.TradeDesc_test, 				//����y�z
					Config.ItemName_test, 				//�ӫ~�W��
					paymentType, 						//�w�]�I�ڤ覡
					ENVIRONMENT.STAGE);					//�������� : STAGE�����աAOFFICIAL������
								
			
			intent.putExtra(PaymentActivity.EXTRA_PAYMENT, oCreateTrade);
			startActivityForResult(intent, Config.REQUEST_CODE);			
			
		}};
	
	
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
//			super.onActivityResult(requestCode, resultCode, data);
			Log.d(Config.LOGTAG, "requestCode=" + requestCode + " , resultCode=" + resultCode);
			if(requestCode == Config.REQUEST_CODE){
				if(resultCode == PaymentActivity.RESULT_EXTRAS_NULL){
	            	Log.d(Config.LOGTAG, "EXTRA_PAYMENT is NULL ");
	            }else if (resultCode == PaymentActivity.RESULT_EXTRAS_CANCEL) {
	                Log.d(Config.LOGTAG, "The user canceled.");
	            } 
			}
						
		}
		
}
