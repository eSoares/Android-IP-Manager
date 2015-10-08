package pt.it.esoares.ipmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.net.InetAddress;

import pt.it.porto.esoares.android.network.ip.IPManager;
import pt.it.porto.esoares.android.network.ip.IPManagerFactory;

public class MainActivity extends Activity {
	private IPManager manager;
	public static final String DNS_SET = "8.8.4.4";
	public static final String IP_SET = "192.168.1.5";
	public static final String GATEWAY_SET = "192.168.1.0";
	public static final int MASK_SET = 22;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = IPManagerFactory.getIPManager(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}


	public void get(View v) {
		String show = null;
		switch (v.getId()) {
		case R.id.gdns:
			show = manager.getDNS().getHostAddress();
			break;
		case R.id.gip:
			show = manager.getIP().getHostAddress();
			break;
		case R.id.ggw:
			show = manager.getGateway().getHostAddress();
			break;
		case R.id.gmask:
			show = Integer.toString(manager.getMask());
			break;
		default:
			break;
		}
		Toast.makeText(getApplicationContext(), show, Toast.LENGTH_SHORT).show();
	}

	public void set(View v) {
		try {
			switch (v.getId()) {
			case R.id.sdns:
				manager.setIP(manager.getIP(), manager.getMask(), manager.getGateway(), InetAddress.getByName(DNS_SET));
				break;
			case R.id.sip:
				manager.setIP(InetAddress.getByName(IP_SET), manager.getMask(), manager.getGateway(), manager.getDNS());
				break;
			case R.id.sgw:
				manager.setIP(manager.getIP(), manager.getMask(), InetAddress.getByName(GATEWAY_SET), manager.getDNS());
				break;
			case R.id.smask:
				manager.setIP(manager.getIP(), MASK_SET, manager.getGateway(), manager.getDNS());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
