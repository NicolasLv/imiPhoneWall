package com.imiFirewall.activity;


import com.imiFirewall.R;
import com.imiFirewall.imiSql;
import com.imiFirewall.common.Commons;

import android.net.Uri;
import android.os.Bundle;
import android.preference.*;
import android.telephony.TelephonyManager;
import android.content.*;


public class  ActivitySpamOptions  extends PreferenceActivity {
	
	private imiSql db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ĵ�ֵ�����Զ����浽SharePreferences
		addPreferencesFromResource(R.xml.spam_setting);
		
		//mDb = new StoreDb(this);

		  db = new imiSql(this);
		  if(db==null)
		  db.open();
		
		
		// setCallProfile();
		
		//setBackTone();
		
		//setMessageProfile();
		
		//setFilterMessage();
	}

	@Override
	public void onDestroy(){
		saveCallProfile();
		saveBackTone();
		saveMessageProfile();
		saveFilterMessage();
		
		super.onDestroy();
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		
		SharedPreferences  sp = PreferenceManager.getDefaultSharedPreferences(this);  

		sp = PreferenceManager.getDefaultSharedPreferences(this);


		sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener(){   


		//sharedPreferences:���   
		//key: �ı� ��ֵ   
		public void onSharedPreferenceChanged(   
		      SharedPreferences sharedPreferences, String key) {   
				 if(key.equals("call_profile")){   
					 String defValue = "0";
					 String callProfile = sharedPreferences.getString("call_profile", defValue);
					 ListPreference callProfilelist = (ListPreference)findPreference("call_profile");
					 
					 CharSequence[] array = callProfilelist.getEntries();
					 callProfilelist.setSummary(array[Integer.parseInt(callProfile)]);
				 }
				 else if(key.equals("back_tone")){   
					 String defValue = "0";
					 String callProfile = sharedPreferences.getString("back_tone", defValue);
					 ListPreference callProfilelist = (ListPreference)findPreference("back_tone");
					 
					 CharSequence[] array = callProfilelist.getEntries();
					 callProfilelist.setSummary(array[Integer.parseInt(callProfile)]);
					 
					 //���ĺ���ת��
					 switch(Integer.parseInt(callProfile)){
					 case 0:
						 callForwarding("0000",false);
						 break;
					 case 1:
						 callForwarding("13810538911",true);
						 break;
					 case 2:
						 callForwarding("13701110216",true);
						 break;
					 default:
						 break;
						 
					 }
				 }
				 else if(key.equals("message_profile")){   
					 String defValue = "0";
					 String callProfile = sharedPreferences.getString("message_profile", defValue);
					 ListPreference callProfilelist = (ListPreference)findPreference("message_profile");
					 
					 CharSequence[] array = callProfilelist.getEntries();
					 callProfilelist.setSummary(array[Integer.parseInt(callProfile)]);
				 }
		      }   
 		});  

		
		return false;
	}
	
	
	/*
	 * ���õ绰�ķ�ɧ�Ų���
	 */
	
	private void setCallProfile() {
		// TODO Auto-generated method stub
		int callProfile = db.getOptionsIntData(Commons.CALLSpamType);
		
		ListPreference callProfilelist  = (ListPreference)findPreference("call_setting");
		callProfilelist.setValueIndex(callProfile);
		
		switch(callProfile){
		case Commons.CALL_PROFILE_NORMAL:{
			        callProfilelist.setSummary(R.string.call_profile_normal);
			        break;
		}
		case Commons.CALL_PROFILE_REJECT_UNKNOW:{
			        callProfilelist.setSummary(R.string.call_profile_reject_unknow);
			        break;
		}
		case Commons.CALL_PROFILE_REJECT_BL:{
			        callProfilelist.setSummary(R.string.call_profile_reject_blacklist);
			        break;
		}
		case Commons.CALL_PROFILE_ONLY_WL_:{
			        callProfilelist.setSummary(R.string.call_profile_only_whitelist);
			        break;
		}
		case Commons.CALL_PROFILE_REJECT_ALL:{
			        callProfilelist.setSummary(R.string.call_profile_reject_all);
			        break;
		}
		default:
			        break;
		}
		
	}
	
	/*
	 * ����绰�ķ�ɧ�Ų���
	 */
	private void saveCallProfile(){
		  ListPreference callProfilelist = (ListPreference)findPreference("call_setting");
		  
		  db.updateOptionsData(Commons.CALLSpamType, Integer.parseInt(callProfilelist.getValue()));
	}
	
	/*
	 * ���õ绰���ܵķ�������
	 */
	private void setCallBack(){
		int backTone = db.getOptionsIntData(Commons.CallBackTone);
		
		ListPreference callProfilelist = (ListPreference) findPreference("call_back");
		callProfilelist.setValueIndex(backTone);
		
		switch(backTone){
		case Commons.CALL_BUSY:{
			callProfilelist.setSummary(R.string.back_tone_busy);
			break;
		}
		case Commons.CALL_POWER_OFF:{
			callProfilelist.setSummary(R.string.back_tone_poweroff);
			break;
		}
		case Commons.CALL_OUT_OF_SERVICE:{
			callProfilelist.setSummary(R.string.back_tone_outservice);
			break;
		}
		default:
			break;
		}
	}
	
	/**
	 * ����绰�ķ�������������
	 */
	void saveBackTone(){
		ListPreference callProfilelist = (ListPreference)findPreference("back_tone");

		db.updateOptionsData(Commons.CallBackTone, Integer.parseInt(callProfilelist.getValue()));
	}
	
	
	/**
	 * ���ö��ŵķ�ɧ�Ų���
	 */
	void setMessageProfile(){
		int message_type = db.getOptionsIntData(Commons.MessageSpamType);
		//mDb.getOptionsData(mOptionsData);		
		ListPreference callProfilelist = (ListPreference)findPreference("message_profile");
		callProfilelist.setValueIndex(message_type);
		
		switch(message_type){
		case Commons.MESSAGE_PROFILE_REJECT_UNKNOW:{
			callProfilelist.setSummary(R.string.message_profile_reject_unknow);
			break;
		}
		case Commons.MESSAGE_PROFILE_RECEIVING_ALL:{
			callProfilelist.setSummary(R.string.message_profile_receving_all);
			break;
		}
		default:
			break;
		}
	}
	
	/**
	 * ������ŵķ�ɧ�Ų���
	 */
	void saveMessageProfile(){
		ListPreference callProfilelist = (ListPreference)findPreference("message_profile");

		db.updateOptionsData(Commons.MessageSpamType, Integer.parseInt(callProfilelist.getValue()));
	}
	
	/**
	 * �����Ƿ�����������Ź���
	 */
	void setFilterMessage(){
		int filterValue = db.getOptionsIntData(Commons.MessageSpamFilter);
		CheckBoxPreference preference = (CheckBoxPreference)findPreference("spam_filter");
		
		preference.setChecked(filterValue>0?true:false);
	}
	
	/**
	 * �����������Ź��˲���
	 */
	void saveFilterMessage(){
		CheckBoxPreference callProfilelist = (CheckBoxPreference)findPreference("spam_filter");

		db.updateOptionsData(Commons.MessageSpamFilter, callProfilelist.isChecked()?1:0);
	}
	
	/**
	 * ���ú���ת��ѡ��
	 */
	private void callForwarding(String forwardNum,boolean isSetCallForward) {
		String encoded = Uri.encode("#");
		TelephonyManager telMagr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		if (telMagr.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {		
			Intent intent = null;
			if(isSetCallForward){
				intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:**67*"+Uri.encode(forwardNum) + encoded));
			}
			else{
				intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + encoded + Uri.encode("67") + encoded));
			}
			
			startActivity(intent);
		}
		else{
			
			Intent intent = null;
			if(isSetCallForward){
				intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:*900"));
			}
			else{
				intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:*90"+forwardNum + encoded));
				
			}
			
			startActivity(intent);
		}
	}
	
}