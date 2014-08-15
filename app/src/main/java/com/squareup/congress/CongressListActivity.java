package com.squareup.congress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CongressListActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_congresslist);

    final ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

    RestAdapter restAdapter =
        new RestAdapter.Builder().setEndpoint("https://www.govtrack.us").build();
    CongressService service = restAdapter.create(CongressService.class);

    service.list(new Callback<CongressService.Data>() {
      @Override public void success(CongressService.Data data, Response response) {
        for (CongressService.Role role : data.objects) {
          adapter.add(role.person.name);
        }
      }

      @Override public void failure(RetrofitError error) {
        Toast.makeText(CongressListActivity.this, "Woops: " + error.getMessage(),
            Toast.LENGTH_SHORT).show();
      }
    });

    ListView congressListView = (ListView) findViewById(R.id.congress_list);

    congressListView.setAdapter(adapter);
    congressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = CongressListActivity.this;
        Intent intent = new Intent(context, CongresspersonActivity.class);
        String name = adapter.getItem(position);
        intent.putExtra("name", name);
        startActivity(intent);
      }
    });
  }
}
