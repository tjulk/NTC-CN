package com.nike.ntc_cn;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.nike.ntc_cn.actionbar.ActionBarActivity;

/**
 * @ClassName: BaseActivity 
 * @Description: 通用activity
 * @author LEIKANG 
 * @date 2013-3-6 下午2:27:14
 */
public class BaseActivity extends ActionBarActivity{

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.main, menu);

	        // Calling super after populating the menu is necessary here to ensure that the
	        // action bar helpers have a chance to handle this event.
	        return super.onCreateOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case android.R.id.home:
	                Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
	                break;

	            case R.id.menu_refresh:
	                Toast.makeText(this, "Fake refreshing...", Toast.LENGTH_SHORT).show();
	                getActionBarHelper().setRefreshActionItemState(true);
	                getWindow().getDecorView().postDelayed(
	                        new Runnable() {
	                            @Override
	                            public void run() {
	                                getActionBarHelper().setRefreshActionItemState(false);
	                            }
	                        }, 1000);
	                break;

	            case R.id.menu_search:
	                Toast.makeText(this, "Tapped search", Toast.LENGTH_SHORT).show();
	                break;

	            case R.id.menu_share:
	                Toast.makeText(this, "Tapped share", Toast.LENGTH_SHORT).show();
	                break;
	        }
	        return super.onOptionsItemSelected(item);
	    }
}
