package com.allpay.tw.mobilesdk;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;

public class OptionalCreditInstallment implements Parcelable {
	private Integer CreditInstallment;
	private Integer InstallmentAmount;
	private Boolean Redeem;
	private Boolean UnionPay;
	
	
	public void setCreditInstallment(Integer CreditInstallment) { this.CreditInstallment = CreditInstallment; }
	public Integer getCreditInstallment() { return this.CreditInstallment; }
	
	public void setInstallmentAmount(Integer InstallmentAmount) { this.InstallmentAmount = InstallmentAmount; }
	public Integer getInstallmentAmount() { return this.InstallmentAmount; }
	
	public void setRedeem(Boolean Redeem) { this.Redeem = Redeem; }
	public Boolean getRedeem() { return this.Redeem; }
	
	public void setUnionPay(Boolean UnionPay) { this.UnionPay = UnionPay; }
	public Boolean getUnionPay() { return this.UnionPay; }
	
	
	/**
	 * ��ܩʰѼơA�H�Υd����
	 * ��ChoosePayment�ѼƬ�Credit�ɱa�J�A���i�P�H�Υd�w���w�B�@�_�]�w
	 * @param CreditInstallment : ��d��������
	 * @param InstallmentAmount : �ϥΨ�d�������I�ڪ��B
	 * @param Redeem : �H�Υd�O�_�ϥά��Q���
	 * @param UnionPay : �O�_�����p�d���
	 */
	public OptionalCreditInstallment(Integer CreditInstallment, Integer InstallmentAmount, Boolean Redeem, Boolean UnionPay){
		this.CreditInstallment = CreditInstallment;
		this.InstallmentAmount = InstallmentAmount;
		this.Redeem = Redeem;
		this.UnionPay = UnionPay;
	}
	private OptionalCreditInstallment(){}
	
	
	public Collection<Map.Entry<String, String>> getPostData(int TotalAmount){
		Map<String, String> mapParams = new HashMap<String, String>();		
		mapParams.put("CreditInstallment", String.valueOf(this.CreditInstallment));
		if(this.CreditInstallment * this.InstallmentAmount > TotalAmount)
			mapParams.put("InstallmentAmount", String.valueOf(this.InstallmentAmount));
		if(this.Redeem)
			mapParams.put("Redeem", "Y");
		mapParams.put("UnionPay", this.UnionPay ? "1" : "0");
		
		return mapParams.entrySet();
	}
	
	
	public String getJSON()
	{
		return new com.google.gson.Gson().toJson(this);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Parcelable.Creator<OptionalCreditInstallment> CREATOR = new Creator(){

		@Override
		public OptionalCreditInstallment createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			OptionalCreditInstallment obj = new OptionalCreditInstallment();
			obj.setCreditInstallment(Integer.valueOf(source.readString()));
			obj.setInstallmentAmount(Integer.valueOf(source.readString()));
			obj.setRedeem(Boolean.valueOf(source.readString()));
			obj.setUnionPay(Boolean.valueOf(source.readString()));
			
			return obj;
		}

		@Override
		public OptionalCreditInstallment[] newArray(int size) {
			// TODO Auto-generated method stub
			return new OptionalCreditInstallment[size];
		}
		
	};
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(String.valueOf(CreditInstallment));
		dest.writeString(String.valueOf(InstallmentAmount));
		dest.writeString(String.valueOf(Redeem));
		dest.writeString(String.valueOf(UnionPay));
	}
	
	
}
