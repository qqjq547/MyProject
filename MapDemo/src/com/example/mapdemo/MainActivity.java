package com.example.mapdemo;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	final static String TAG = "MainActivity";
	/**
	 *  MapView �ǵ�ͼ���ؼ�
	 */
	private MapView mMapView = null;
	/**
	 *  ��MapController��ɵ�ͼ���� 
	 */
	private MapController mMapController = null;
	/**
	 *  MKMapViewListener ���ڴ����ͼ�¼��ص�
	 */
	MKMapViewListener mMapListener = null;
	BMapManager mBMapManager = null;
	public static final String strKey = "yWqcnysYB3y7YnL0DLV9n4Rk";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager.
         * BMapManager��ȫ�ֵģ���Ϊ���MapView���ã�����Ҫ��ͼģ�鴴��ǰ������
         * ���ڵ�ͼ��ͼģ����ٺ���٣�ֻҪ���е�ͼģ����ʹ�ã�BMapManager�Ͳ�Ӧ�����
         */
        if (mBMapManager == null) {
        	 mBMapManager = new BMapManager(getApplicationContext());
            /**
             * ���BMapManagerû�г�ʼ�����ʼ��BMapManager
             */
            mBMapManager.init(strKey,new MyGeneralListener());
        }
        /**
          * ����MapView��setContentView()�г�ʼ��,��������Ҫ��BMapManager��ʼ��֮��
          */
        setContentView(R.layout.activity_main);
        mMapView = (MapView)findViewById(R.id.bmapView);
        /**
         * ��ȡ��ͼ������
         */
        mMapController = mMapView.getController();
        /**
         *  ���õ�ͼ�Ƿ���Ӧ����¼�  .
         */
        mMapController.enableClick(true);
        /**
         * ���õ�ͼ���ż���
         */
        mMapController.setZoom(12);
       
        /**
         * ����ͼ�ƶ���ָ����
         * ʹ�ðٶȾ�γ����꣬����ͨ��http://api.map.baidu.com/lbsapi/getpoint/index.html��ѯ�������
         * �����Ҫ�ڰٶȵ�ͼ����ʾʹ���������ϵͳ��λ�ã��뷢�ʼ���mapapi@baidu.com�������ת���ӿ�
         */
        GeoPoint p ;
        double cLat = 39.945 ;
        double cLon = 116.404 ;
        Intent  intent = getIntent();
        if ( intent.hasExtra("x") && intent.hasExtra("y") ){
        	//����intent����ʱ���������ĵ�Ϊָ����
        	Bundle b = intent.getExtras();
        	p = new GeoPoint(b.getInt("y"), b.getInt("x"));
        }else{
        	//�������ĵ�Ϊ�찲��
        	 p = new GeoPoint((int)(cLat * 1E6), (int)(cLon * 1E6));
        } 
        mMapController.setCenter(p);
  
        /**
    	 *  MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
    	 */
        mMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * �ڴ˴����ͼ�ƶ���ɻص�
				 * ���ţ�ƽ�ƵȲ�����ɺ󣬴˻ص�������
				 */
			}
			
			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * �ڴ˴����ͼpoi����¼�
				 * ��ʾ��ͼpoi��Ʋ��ƶ����õ�
				 * ���ù� mMapController.enableClick(true); ʱ���˻ص����ܱ�����
				 * 
				 */
				String title = "";
				if (mapPoiInfo != null){
					title = mapPoiInfo.strText;
					Toast.makeText(MainActivity.this,title,Toast.LENGTH_SHORT).show();
					mMapController.animateTo(mapPoiInfo.geoPt);
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 *  �����ù� mMapView.getCurrentMap()�󣬴˻ص��ᱻ����
				 *  ���ڴ˱����ͼ���洢�豸
				 */
			}

			@Override
			public void onMapAnimationFinish() {
				/**
				 *  ��ͼ��ɴ��Ĳ�������: animationTo()���󣬴˻ص�������
				 */
			}
            /**
             * �ڴ˴����ͼ������¼� 
             */
			@Override
			public void onMapLoadFinish() {
				Toast.makeText(MainActivity.this, 
						       "��ͼ�������", 
						       Toast.LENGTH_SHORT).show();
				
			}
		};
		mMapView.regMapViewListener(mBMapManager, mMapListener);
    }
    
    @Override
    protected void onPause() {
    	/**
    	 *  MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
    	 */
        mMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
    	/**
    	 *  MapView������������Activityͬ������activity�ָ�ʱ�����MapView.onResume()
    	 */
        mMapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	/**
    	 *  MapView������������Activityͬ������activity���ʱ�����MapView.destroy()
    	 */
        mMapView.destroy();
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mMapView.onRestoreInstanceState(savedInstanceState);
    }
 // �����¼�������������ͨ�������������Ȩ��֤�����
    public class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(MainActivity.this, "��������������",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(MainActivity.this, "������ȷ�ļ���������",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
        	//����ֵ��ʾkey��֤δͨ��
            if (iError != 0) {
                //��ȨKey����
                Toast.makeText(MainActivity.this, 
                        "���� DemoApplication.java�ļ�������ȷ����ȨKey,�����������������Ƿ���error: "+iError, Toast.LENGTH_LONG).show();
//                DemoApplication.getInstance().m_bKeyRight = false;
            }
            else{
//            	DemoApplication.getInstance().m_bKeyRight = true;
            	Toast.makeText(MainActivity.this, 
                        "key��֤�ɹ�", Toast.LENGTH_LONG).show();
            }
        }
    }
    
}
