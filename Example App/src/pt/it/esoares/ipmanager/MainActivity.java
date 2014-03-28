package pt.it.esoares.ipmanager;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.net.InetAddress;
import pt.it.porto.esoares.android.network.ip.IPManager;
import pt.it.porto.esoares.android.network.ip.IPManagerFactory;

public class MainActivity extends ActionBarActivity {
	private IPManager manager;
	private Toast toast;
	public static final String DNS_SET = "8.8.4.4";
	public static final String IP_SET = "192.168.1.5";
	public static final String GATEWAY_SET = "192.168.1.0";
	public static final int MASK_SET = 22;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar ab = getSupportActionBar();
		ab.show();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		manager = IPManagerFactory.getIPManager(getApplicationContext());
		toast = new Toast(getApplicationContext()); 
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
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
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
		toast.makeText(getApplicationContext(), show, Toast.LENGTH_SHORT).show();
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
