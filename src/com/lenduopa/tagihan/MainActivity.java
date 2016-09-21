package com.lenduopa.tagihan;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lenduopa.tagihan.fragment.CategoryList;
import com.lenduopa.tagihan.fragment.MainList;
import com.lenduopa.tagihan.fragment.PartnerList;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setPadding(0, 0, 0, 0);
		toolbar.setContentInsetsAbsolute(0, 0);
		setSupportActionBar(toolbar);

		MainList mainFragment = new MainList();
		getSupportFragmentManager()
	    		.beginTransaction()
	    		.replace(R.id.frame, mainFragment)
	    		.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_main) {
			getSupportActionBar().setTitle("Tagihan");

			MainList mainFragment = new MainList();
			getSupportFragmentManager()
		    		.beginTransaction()
		    		.replace(R.id.frame, mainFragment)
		    		.commit();

			return true;
		} else if (id == R.id.action_partners) {
			getSupportActionBar().setTitle("Partner");
			
			PartnerList mainFragment = new PartnerList();
			getSupportFragmentManager()
		    		.beginTransaction()
		    		.replace(R.id.frame, mainFragment)
		    		.commit();

			return true;
		} else if (id == R.id.action_categories) {
			getSupportActionBar().setTitle("Category");
			
			CategoryList mainFragment = new CategoryList();
			getSupportFragmentManager()
		    		.beginTransaction()
		    		.replace(R.id.frame, mainFragment)
		    		.commit();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		setupTransparentSystemBarsForLmp(getWindow());
	}

	public static boolean isLmpOrAbove() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}

	/**
	 * Sets up transparent navigation and status bars in LMP. This method is a
	 * no-op for other platform versions.
	 */
	@TargetApi(19)
	public static void setupTransparentSystemBarsForLmp(Window window) {
		// Currently we use reflection to access the flags and the API to set
		// the transparency
		// on the System bars.
		if (isLmpOrAbove()) {
			try {
				//window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
								| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
				Field drawsSysBackgroundsField = WindowManager.LayoutParams.class
						.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
				window.addFlags(drawsSysBackgroundsField.getInt(null));

				Method setStatusBarColorMethod = Window.class
						.getDeclaredMethod("setStatusBarColor", int.class);
				Method setNavigationBarColorMethod = Window.class
						.getDeclaredMethod("setNavigationBarColor", int.class);
				setStatusBarColorMethod.invoke(window, Color.TRANSPARENT);
				setNavigationBarColorMethod.invoke(window, Color.BLACK);
				//setNavigationBarColorMethod.invoke(window, R.drawable.gradient_background);	// Navigation color
			} catch (NoSuchFieldException e) {
			} catch (NoSuchMethodException ex) {
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			} finally {
			}
		}
	}

}
