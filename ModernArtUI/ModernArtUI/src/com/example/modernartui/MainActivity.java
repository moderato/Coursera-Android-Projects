package com.example.modernartui;

import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends ActionBarActivity {

	private Context mContext = this;
	private SeekBar seekbar;
	private ImageView[] imageview = new ImageView[5];
	private final static String THE_URL = "http://www.moma.org";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		seekbar = (SeekBar) findViewById(R.id.seekbar);
		imageview[0] = (ImageView) findViewById(R.id.zero);
		imageview[1] = (ImageView) findViewById(R.id.one);
		imageview[2] = (ImageView) findViewById(R.id.two);
		imageview[3] = (ImageView) findViewById(R.id.three);
		imageview[4] = (ImageView) findViewById(R.id.four);
				
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int currentProgress = 0;
			int pastProgress = 0;
			int colorValue = 0;

			public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser){
				currentProgress = progressValue;
				for (int i=0;i<5;i++){
					ColorDrawable colorDrawable = (ColorDrawable) imageview[i].getBackground();
					colorValue = colorDrawable.getColor();
					
					//If the background color is not white or gray
					if (colorValue != getResources().getColor(R.color.White) && 
							colorValue != getResources().getColor(R.color.Gray)){ 
						// Add 1 to R, G and B respectively
						colorValue = colorValue + (currentProgress - pastProgress) * (1+256+256*256);
						// Set the color
						imageview[i].setBackgroundColor(colorValue);
					}
				}
				pastProgress = currentProgress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {}

			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		
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
		if (id == R.id.action_settings) {
			showDialog(); //Display the dialog window
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showDialog(){
		final Dialog dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //Don't need the title
		dialog.setContentView(R.layout.dialog);

		// if the yes button is clicked, open the webpage
		Button dialogButtonYes = (Button) dialog.findViewById(R.id.yes);
		dialogButtonYes.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				openBrowser(); //Open the browser
				dialog.dismiss();
			}
		});
		
		// if the no button is clicked, close the custom dialog
		Button dialogButtonNo = (Button) dialog.findViewById(R.id.no);
		dialogButtonNo.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	//Start the build-in browser function
	private void openBrowser(){
		final Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(THE_URL));
		startActivity(intent);		
	}	
	
}
