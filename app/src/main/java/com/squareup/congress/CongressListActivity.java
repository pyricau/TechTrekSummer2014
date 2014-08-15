package com.squareup.congress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CongressListActivity extends Activity {

  CongressService.Data data;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_congresslist);

    final ListView congressListView = (ListView) findViewById(R.id.congress_list);
    final ProgressBar progressSpinner = (ProgressBar) findViewById(R.id.spinner);

    final ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

    RestAdapter restAdapter =
        new RestAdapter.Builder().setEndpoint("https://www.govtrack.us").build();
    CongressService service = restAdapter.create(CongressService.class);

    service.list(new Callback<CongressService.Data>() {

      @Override public void success(CongressService.Data data, Response response) {
        CongressListActivity.this.data = data;
        for (CongressService.Role role : data.objects) {
          adapter.add(role.person.name);
        }
        // Hide the loading spinner, show the list with contents.
        progressSpinner.setVisibility(View.GONE);
        congressListView.setVisibility(View.VISIBLE);
      }

      @Override public void failure(RetrofitError error) {
        Toast.makeText(CongressListActivity.this, "Woops: " + error.getMessage(),
            Toast.LENGTH_SHORT).show();
      }
    });

    congressListView.setAdapter(adapter);
    congressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = CongressListActivity.this;
        Intent intent = new Intent(context, CongressPersonActivity.class);
        CongressService.Role role = data.objects.get(position);
        intent.putExtra("name", role.person.name);
        intent.putExtra("youtubeid", role.person.youtubeid);
        intent.putExtra("id", role.person.id);
        startActivity(intent);
      }
    });
  }
}
