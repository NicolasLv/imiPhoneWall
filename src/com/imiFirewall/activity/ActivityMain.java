package com.imiFirewall.activity;


import com.imiFirewall.R;
import com.imiFirewall.imiApi;
import com.imiFirewall.R.drawable;
import com.imiFirewall.R.id;
import com.imiFirewall.R.layout;
import com.imiFirewall.R.string;
import com.imiFirewall.activity.personal.ActivityPersonal;

import android.app.Activity;    //����android���������ǵ�android��ҪСд
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.view.Menu;     //����˵���
import android.view.MenuItem;

public class ActivityMain extends Activity {
    
	ImageButton btn_setting;
	ImageButton btn_network;
	ImageButton btn_call;
	ImageButton btn_sms;
	
	ImageButton btn_call_black;
	ImageButton btn_sms_black;
	
	ImageButton btn_call_record;
	ImageButton btn_private_space;
	
	ImageButton btn_help;
	
	 boolean firewall=true;
	 boolean callwall=false;
	 boolean smswall=false;
	
	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;
	
	
	private static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
	private static final String EXTRA_SHORTCUT_DUPLICATE = "duplicate";
	
	static final int REQUEST_CODE = 1;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final SharedPreferences prefs = getSharedPreferences(imiApi.PREFS_NAME, 0);
        final boolean hasshortcut = prefs.getBoolean(imiApi.PREF_SHORTCUT, false);  //hasshortcutĬ��ֵ��Ϊ false û�� ��װ ShortCut        
        if(hasshortcut)
        {
        	InstallShortCut();
        	final Editor edit = prefs.edit();
            edit.putBoolean(imiApi.PREF_SHORTCUT, true);
            edit.commit();
        }    
                
        setContentView(R.layout.main);
        
        
        //����ǽ��������
        btn_setting = (ImageButton) findViewById(R.id.setting);
        btn_setting.setOnClickListener(new ImageButton.OnClickListener() {
        	public void onClick(View v){
        		Intent intent1= new Intent(ActivityMain.this,ActivitySetting.class);
        		startActivity(intent1);
        	}
        });
       
        //�û��߼�����Activity
        btn_call_black = (ImageButton) findViewById(R.id.main_call_black);
        btn_call_black.setOnClickListener(new ImageButton.OnClickListener() {
        	public void onClick(View v){
        		Intent intent5= new Intent(ActivityMain.this,ActivityAdvance.class);
        		startActivity(intent5);
        	}
        });
        
        //�ֻ�����ͳ��Activity
        btn_sms_black = (ImageButton) findViewById(R.id.main_sms_black);
        btn_sms_black.setOnClickListener(new ImageButton.OnClickListener() {
        	public void onClick(View v){
        		Intent intent6= new Intent(ActivityMain.this,ActivityInit.class);
        		startActivity(intent6);
        	}
        });
        
        //����������������Activity
        btn_network = (ImageButton) findViewById(R.id.network);
        btn_network.setOnClickListener(new ImageButton.OnClickListener() {
        	public void onClick(View v){
        		Intent intent2= new Intent(ActivityMain.this,ActivityNetwork.class);
        		startActivity(intent2);
        	}
        });
       //��ɧ������Activity
       btn_call  = (ImageButton) findViewById(R.id.call);
       btn_call.setOnClickListener(new ImageButton.OnClickListener() {
    	  public void onClick(View v){
    		   Intent intent3 = new Intent(ActivityMain.this,ActivitySpam.class);
    		   startActivity(intent3);
    	  }
       });
     //���ſ�����������Activity
       btn_sms  = (ImageButton) findViewById(R.id.sms);
       btn_sms.setOnClickListener(new ImageButton.OnClickListener() {
    	  public void onClick(View v){
    		  // Intent intent4 = new Intent(ActivityMain.this,ActivitySms.class);
    		  // startActivity(intent4);
    	  }
       });       
       //������ˣ����ؼ�¼��Activity
       btn_call_record = (ImageButton) findViewById(R.id.main_call_filter);
       btn_call_record.setOnClickListener(new ImageButton.OnClickListener() {
       	public void onClick(View v){
       		//Intent intent8= new Intent(ActivityMain.this,ActivityCallfilter.class);
       		//startActivity(intent8);
       	}
       });
       //���Ź���(���ؼ�¼��Activity
       btn_private_space = (ImageButton) findViewById(R.id.main_private_space);
       btn_private_space.setOnClickListener(new ImageButton.OnClickListener() {
       	public void onClick(View v){
       		Intent intent7= new Intent(ActivityMain.this,ActivityPersonal.class);
       		startActivity(intent7);
       	}
       });
       
       //����ǽ������Ϣ
       btn_help = (ImageButton) findViewById(R.id.main_help);
       btn_help.setOnClickListener(new ImageButton.OnClickListener() {
     	  public void onClick(View v){
     		   new ActivityHelp(ActivityMain.this).show();
     	  }
        });       
    }
    
    @Override
    /*
	 * ���ӳ���˵���
	 * MenuItem.setIcon.��������menu��ť�ı���
	 */
    public boolean onCreateOptionsMenu(Menu menu){
          super.onCreateOptionsMenu(menu);
          //menu.add(0,ITEM0,0,"�˵�1");
          //menu.add(0,ITEM1,1,"�˵�2");
          return true;
    }
    
    @Override
    
    /*   ���ӳ���Բ˵���ĵ�����Ӧ 
     * 
     */
    public boolean onOptionsItemSelected(MenuItem item){
    
      switch(item.getItemId()){
      case ITEM0:
    	  //fun0
    	  //setTitle("fun0");
    	  break;
      case ITEM1:
    	  //fun1;
    	  //setTitle("fun1");
    	  break;
      }
      return super.onOptionsItemSelected(item);
    }
    /*
     * Install APP ShortCut Function
     * 
     */
    public void InstallShortCut()
    {
    	
    	Intent shortcutIntent = new Intent(ACTION_INSTALL_SHORTCUT);  

    	shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name)); //��ݷ�ʽICON
    	shortcutIntent.putExtra(EXTRA_SHORTCUT_DUPLICATE, false);                         //������ǰ��ICON


    	Intent intent = new Intent();  
    	intent.setComponent(new ComponentName(this.getPackageName(),".ActivityMain"));        //���ÿ�ݷ�ʽ����������
    	    	  
    	shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);  
    	shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this,R.drawable.icon));  
    	sendBroadcast(shortcutIntent);  
    	
    }
    
    
}