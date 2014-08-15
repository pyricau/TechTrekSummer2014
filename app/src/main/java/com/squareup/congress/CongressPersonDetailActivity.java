package com.squareup.congress;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class CongressPersonDetailActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_congressperson);

    TextView nameView = (TextView) findViewById(R.id.name);
    String name = getIntent().getStringExtra("name");
    nameView.setText(name);

    final String youtubeid = getIntent().getStringExtra("youtubeid");
    findViewById(R.id.youtube).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        openYoutubeChannel("http://www.youtube.com/user/" + youtubeid);
      }
    });

    ImageView imageView = (ImageView) findViewById(R.id.picture);
    String id = getIntent().getStringExtra("id");
    Picasso.with(this)
        .load("https://www.govtrack.us/data/photos/" + id + "-200px.jpeg")
        .fit()
        .centerInside()
        .into(imageView);
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
