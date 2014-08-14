package com.squareup.congress;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CongresspersonActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_congressperson);
    TextView name = (TextView) findViewById(R.id.name);
    name.setText("Senator Boxer");

    findViewById(R.id.youtube).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        openYoutubeChannel("http://www.youtube.com/user/SenatorBoxer");
      }
    });
  }

  void openYoutubeChannel(String url) {
    Intent intent;
    try {
      intent = new Intent(Intent.ACTION_VIEW);
      intent.setPackage("com.google.android.youtube");
      intent.setData(Uri.parse(url));
      startActivity(intent);
    } catch (ActivityNotFoundException e) {
      intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(url));
      startActivity(intent);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.congressperson, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
