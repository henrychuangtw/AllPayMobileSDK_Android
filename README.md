AllPayMobileSDK
=========

AllPay Mobile SDK 訂單幕前、幕後產生
請配合使用 AllPay 廠商手機金流介接技術文件

 - web view 產生訂單(幕前)
 - api 產生訂單(幕後)
 - otp 簡訊驗證(幕後)


Version
-----------

1.0.2

How to Use
-----------
如何在你的專案使用 AllPay Mobile SDK 

* 下載 AllPayMobileSDK(Android Library Project)
* 將AllpayMobileSDK加入您的Android專案Library Reference
* 將&lt;activity android:name="com.allpay.tw.mobilesdk.PaymentActivity" /&gt;加入AndroidManifest
* 實作AllpayBackgroundTaskCompleted介面
* AllpayMobileSDK詳細使用方式請參考AllpayMobileSDKDemo


Usage
--------------

web view 產生訂單(幕前)
```Java
Intent intent = new Intent(Main.this, PaymentActivity.class);
paymentType = PAYMENTTYPE.ALL;

CreateTrade oCreateTrade = new CreateTrade(
            Config.MerchantID_test,         //廠商編號 
            Config.AppCode_test,            //App代碼
            Config.getMerchantTradeNo(),    //廠商交易編號
            Config.getMerchantTradeDate(),  //廠商交易時間
            Config.TotalAmount_test,        //交易金額
            Config.TradeDesc_test,          //交易描述
            Config.ItemName_test,           //商品名稱
            paymentType,                    //預設付款方式
            ENVIRONMENT.STAGE);             //介接環境 : STAGE為測試，OFFICIAL為正式

intent.putExtra(PaymentActivity.EXTRA_PAYMENT, oCreateTrade);
startActivityForResult(intent, Config.REQUEST_CODE);

```

 

api 產生訂單(幕後)
```Java
BackgroundCredit oBackgroundCredit = new BackgroundCredit(
            Config.MerchantID_test,         //廠商編號 
            Config.AppCode_test,            //App代碼 
            Config.getMerchantTradeNo(),    //廠商交易編號
            Config.getMerchantTradeDate(),  //廠商交易時間
            Config.TotalAmount_test,        //交易金額
            Config.TradeDesc_test,          //交易描述
            Config.ItemName_test,           //商品名稱
            PAYMENTTYPE.CREDIT,             //付款方式
            ENVIRONMENT.STAGE,              //介接環境 : STAGE為測試，OFFICIAL為正式
            "亨利",                         //持卡人姓名
            PhoneNumber,                    //手機號碼
            "4311952222222222",             //信用卡卡號
            "204012",                       //有效年月
            "222");                         //卡片末三碼

new AllpayAsyncTask<BackgroundCredit, API_Credit>(MainBackground.this, API_Credit.class, pd).execute(oBackgroundCredit);

```



otp 簡訊驗證(幕後)
```Java
BackgroundOTP oBackgroundOTP = new BackgroundOTP(
        OtpMerchantID,                  //廠商編號
        OtpMerchantTradeNo,             //廠商交易編號
        OtpTradeNo,                     //AllPay的交易編號
        edtOTP.getText().toString(),    //OTP驗證碼
        ENVIRONMENT.STAGE);             //介接環境 : STAGE為測試，OFFICIAL為正式

new AllpayAsyncTask<BackgroundOTP, API_Credit>(MainBackground.this, API_Credit.class, pd).execute(oBackgroundOTP);
```



幕後API結果回傳
```Java
@Override
public void onAllpayBackgroundTaskCompleted(API_Base oApi_Base) {
    if(oApi_Base.RtnCode == 2){//ATM 取號成功
        API_ATM oApi_ATM = (API_ATM)oApi_Base;
    
    }else if(oApi_Base.RtnCode == 10100073){//超商取號成功
        API_CVS oApi_CVS = (API_CVS)oApi_Base;

    }else if(oApi_Base.RtnCode == 3){//信用卡OTP驗證
        API_Credit oApi_Credit = (API_Credit)oApi_Base;

    }else if(oApi_Base.RtnCode == 10000030){//驗證碼錯誤

    }else if(oApi_Base.RtnCode == 1){//信用卡付款成功
        API_Credit oApi_Credit = (API_Credit)oApi_Base;

    }

}
```



License
----

MIT
