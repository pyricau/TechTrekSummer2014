package com.squareup.congress;

import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;

public interface CongressService {

  class Data {
    List<Role> objects;
  }

  class Role {
    Person person;
  }

  class Person {
    String id;
    String name;
    String youtubeid;
  }

  @GET("/api/v2/role?current=true&limit=600")
  void list(Callback<Data> callback);
}
