# Tech Treck - Android

Today we are going to create an app that uses the data from https://www.govtrack.us . We’ll display the list of members of congress, and then we’ll display more details and actions when you click on a member of congress.

![](https://raw.githubusercontent.com/pyricau/TechTrekSummer2014/master/demo.gif)

We’re here to help, so don’t hesitate to ask any question.

## Create a new project

* Open Android Studio.
* Click on New project.
* Enter a cool Application name, a fake company domain and click Next
* Make sure “Phone and Tablet” is checked, select Minimum SDK API 16 and click Next
* Select “Blank Activity” and click Next
* Select Activity Name to CongressPersonActivity, Title to  CongressPerson and click Finish


## Deploy the app

* Make sure the phone is plugged in
* Select Run > Run ‘app’
* Let the magic happen!

## Reading the CongressPersonActivity files

* An activity is like a screen
* Open the file AndroidManifest.xml. It lists all the activities in your app. It’s located in app/src/main/ .
* To open an activity class from the manifest, move the mouse over its name (right of android:name=), and do CMD + Click.
* An activity is made of a hierarchy of views. The hierarchy of views is defined in a layout resource file.
* To open the layout resource file from the activity, locate the onCreate() method, and CMD+Click on the layout id. setContentView(R.layout.some_layout_id).

## Changing the text

* Let’s edit the layout resource file to set a name on the textview, from the code.
* In the layout, add a “name” id to the texview. The syntax is: android:id=”@+id/some_id_for_my_view”
* From the activity class, after setContentView(), you can retrieve the TextView by calling findViewById(R.id.some_id_for_my_view). This method returns a View, you need to cast it to TextView.
* On that TextView, use setText() to set it to a name of your choice.
* Deploy and see if that worked!

## Youtube iz coolz

* Let’s add a button that will take us to the 
* In the layout resource file, change the RelativeLayout to a LinearLayout, and add an android:orientation=”vertical” attribute to it.
* Add a Button below the TextView
* In the activity code, retrieve the Button, create a new View.OnClickListener instance and set it on the Button. 
* Intents are cool. Let’s use an intent to open the youtube app. Add this method to your activity:

```java
  void openYoutubeChannel(String url) {
    Intent intent ;
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
```

* In the listener onClick() method, call openYouTubeChannel() with a youtube channel URL such as "http://www.youtube.com/user/SenatorBoxer"

## Multiple activities

* Create a new class that extends Activity. Let’s call it CongressListActivity
* Declare it in AndroidManifest.xml
* This activity is going to be shown when the app starts, so let’s move the <intent-filter> tag from CongressPersonActivity to CongressListActivity
* Create a new layout resource file that contains just a ListView
* In CongressListActivity, override the onCreate() method and call setContentView() from there with that new layout resource file.
* The content of a listview is provided by an Adapter.
* Let’s create a simple arrayadapter to which we can add data. We need to give it the layout it will use for each view, we can use one provided by Android: android.R.layout.simple_list_item_1   

```
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
```

* Use adapter.add(“some string”) to add a few names
* Retrieve the ListView using its id and set its adapter.
* Set an OnItemClickListener on the listview.
* Start the CongressPersonActivity from onItemClick()

```
        Context context = CongressListActivity.this;
        Intent intent = new Intent(context, CongressPersonActivity.class);
        startActivity(intent);
```

## Activity extras

* An activity extra is a way to send parameters to an activity. Let’s send the name from the list and display it in CongressPersonActivity
* In onItemClick, retrieve the name corresponding to the position that was click, then use intent.putExtra() to add that name to the intent.
* In CongressPersonActivity.onCreate(), you can retrieve this extra with getIntent().getStringExtra()

## Let’s use real data!

* The cool part begins. Let’s download real congress data from the internet!
* The API is defined here: https://www.govtrack.us/developers/api
* We’ll use the following endpoint: https://www.govtrack.us/api/v2/role?current=true&limit=600
* Your app needs the internet permission. Add this right below <manifest> :
	
```xml
  <uses-permission android:name="android.permission.INTERNET" />
```

* We’ll use Retrofit (a Square library) to easily download and parse the data. The documentation is here: http://square.github.io/retrofit/
Locate the build.gradle file in the app folder, and add the retrofit dependency:

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.squareup.retrofit:retrofit:1.6.1'
}
```

* Let Android Studio do a Gradle Sync
* Let’s create an interface that will do the parsing job for us:

```java
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
```

* Create a CongressService instance with Retrofit:

```java
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint("https://www.govtrack.us")
        .build();
    CongressService service = restAdapter.create(CongressService.class);
```

* Call the CongressService.list() method from the Activity.onCreate() method, and in the success() callback update the adapter with the result.
In the failure() callback, you can display an error Toast:

```java
      @Override public void failure(RetrofitError error) {
        Toast.makeText(CongressListActivity.this, "Woops: " + error.getMessage(),
            Toast.LENGTH_SHORT).show();
      }
```

## You’re on your own

* Change the app so that when you click on a name, the youtube id gets passes along as an extra and you can click on the Youtube button from the second screen.

## Nice pictures

* Let’s display the person picture in CongressPersonActivity!
* There’s a nice Square library to download and display pictures: Picasso. 
* Add the following dependency to your build.gradle:

```
compile 'com.squareup.picasso:picasso:2.3.3'
```

* Add an ImageView to the layout resource file of CongressPersonActivity
The URL for a picture is https://www.govtrack.us/data/photos/PERSONID-200px.jpeg where PERSONID is the id in the data we download. For example, https://www.govtrack.us/data/photos/300011-200px.jpeg

![](https://www.govtrack.us/data/photos/300011-200px.jpeg)

* Pass that id from CongressListActivity to CongressPersonActivity and then read the Picasso documentation to load the URL into the image view: http://square.github.io/picasso/

## Moar data

* Can you add the role description to CongressPersonActivity? Look at the data to figure out the fields: https://www.govtrack.us/api/v2/role?current=true&limit=600
* You can now add even more fields, and create your own version of CongressPersonActivity!

## Styling things

* You can add more margin (android:layout_marginBottom="16dp") between elements on the CongressPersonActivity to soften the presentation of the screen.
* You can try building a custom listview adapter for the CongressListActivity that will show the image and more data in a much prettier list. Chris, Py, or John would be happy to provide some pointers on how its done.

## Any question?

Feel free to ask any question about any aspect of the platform. Android is rich and complex, there are a lot of interesting things to learn about it. For instance: What is an APK and what is it made of? How does apps map to the Linux user model? What’s the relation between apps, VMs, processes and threads? What is the zygote? How do we get from XML files to a view hierarchy made of java instances?