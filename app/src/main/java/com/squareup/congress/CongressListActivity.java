package com.squareup.congress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class CongressListActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_congresslist);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
    adapter.add("John Smith 1");
    adapter.add("John Smith 2");
    adapter.add("John Smith 3");

    ListView congressListView = (ListView) findViewById(R.id.congress_list);

    congressListView.setAdapter(adapter);
    congressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = CongressListActivity.this;
        Intent intent = new Intent(context, CongresspersonActivity.class);
        startActivity(intent);
      }
    });
  }
}
