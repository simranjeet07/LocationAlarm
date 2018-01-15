package com.example.simranjeet.project1;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Random;

public class TaskActivity extends AppCompatActivity implements LocationListener {
    FirebaseUser u;
    EditText Task;
    EditText pl;
    String user_id;
    Button Location, btn2;//btn3;
    double latitude, longitude, latitude1, longitude1;
    private LocationManager locationManager;
    private android.location.LocationListener myLocationListener;
    String pll, tll;
    int PLACE_PICKER_REQUEST = 1;
    TextView count;
    NotificationManager nm;
    //String[] s1; String[] s2;String[] s3;String[] s4;String[] s5;
    Double d1, d2;
int i=0;


    NotificationCompat.Builder notification;
    private static final int UNIQUE_ID = 45612;

    DatabaseReference databaseReference;
    DatabaseReference d, e;
    FirebaseUser currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        pl = (EditText) findViewById(R.id.place);
        Task = (EditText) findViewById(R.id.task);
        Location = (Button) findViewById(R.id.location);
        btn2 = (Button) findViewById(R.id.start);
//        btn3=(Button)findViewById(R.id.finish);
        count = (TextView) findViewById(R.id.count);
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        currentuser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(currentuser.getUid()).child("taskdet");

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


    }

    public void onLocation(View v) {

        pll = pl.getText().toString().trim();
        tll = Task.getText().toString().trim();

        if (!(TextUtils.isEmpty(pll)) && !(TextUtils.isEmpty(tll))) {

            PlacePicker.IntentBuilder builder =
                    new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(TaskActivity.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        } else {
            if (TextUtils.isEmpty(pll))
                pl.requestFocus();
            if (!TextUtils.isEmpty(pll) && TextUtils.isEmpty(tll))
                Task.requestFocus();
            Toast.makeText(TaskActivity.this, "Enter both Fields", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {
            com.google.android.gms.location.places.Place place =
                    PlacePicker.getPlace(TaskActivity.this, data);
            //tx.setText(place.getAddress());
            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;

        }
    }


    void getLocation() {
        pll = pl.getText().toString().trim();
        tll = Task.getText().toString().trim();
        user_id = currentuser.getUid();
        if (!TextUtils.isEmpty(pll) && !(TextUtils.isEmpty(tll))) {


            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            String id = databaseReference.push().getKey();
            String lati = String.valueOf(latitude);
            String longi = String.valueOf(longitude);
            Taskdetails obj = new Taskdetails(user_id, id, pll, tll, lati, longi);
            databaseReference.child(id).setValue(obj);

            Toast.makeText(TaskActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TaskActivity.this, "Enter both Fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        latitude1 = location.getLatitude();
        longitude1 = location.getLongitude();
       /*double theta = longitude - longitude1;
        double dist = Math.sin(deg2rad(latitude1))
                * Math.sin(deg2rad(latitude))
                + Math.cos(deg2rad(latitude1))
                * Math.cos(deg2rad(latitude))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;*/


        if (currentuser != null) {
             if(i>0)
            nm.cancelAll();
//DatabaseReference d=FirebaseDatabase.getInstance().getReference().;

       /*  d.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 if (dataSnapshot.exists()) {
                     for (DataSnapshot item: dataSnapshot.getChildren()) {
                         String s1=item.child("lati").getValue().toString();
                         String s2=item.child("longi").getValue().toString();
                         String s3=item.child("taskplace").getValue().toString();
                         String s4=item.child("task").getValue().toString();
                         String s5=item.child("taskid").getValue().toString();
                         Double d1=Double.parseDouble(s1);
                         Double d2=Double.parseDouble(s2);

                         Location startPoint=new Location("locationA");
                         startPoint.setLatitude(latitude1);
                         startPoint.setLongitude(longitude1);

                         Location endPoint=new Location("locationA");
                         endPoint.setLatitude(d1);
                         endPoint.setLongitude(d2);

                         double dist=startPoint.distanceTo(endPoint);
                         if(dist<5000){
                             Uri sounduri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                             notification.setSmallIcon(R.drawable.icon1);
                             notification.setTicker("jeet");
                             notification.setWhen(System.currentTimeMillis());
                             notification.setContentTitle(item.child("taskplace").getValue().toString());
                             notification.setSound(sounduri);
                             notification.setContentText("You have one task around this place");

                             Intent intent=new Intent(TaskActivity.this,FinishTask.class);
                             intent.putExtra("place",s3);
                             intent.putExtra("task",s4);
                             intent.putExtra("id",s5);
                             PendingIntent pendingIntent=PendingIntent.getActivity(TaskActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                             notification.setContentIntent(pendingIntent);

                             Random random = new Random();
                             int m = random.nextInt(9999 - 1000) + 1000;

                             NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                             nm.notify(m,notification.build());
                         }
                     }
                 }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });*/

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> items = dataSnapshot.getChildren();


                    for (DataSnapshot item : items) {

                        String s1 = item.child("lati").getValue().toString();
                        String s2 = item.child("longi").getValue().toString();
                        String s3 = item.child("taskplace").getValue().toString();
                        String s4 = item.child("task").getValue().toString();
                        String s5 = item.child("taskid").getValue().toString();
                        d1 = Double.parseDouble(s1);
                        d2 = Double.parseDouble(s2);

                        Location startPoint = new Location("locationA");
                        startPoint.setLatitude(latitude1);
                        startPoint.setLongitude(longitude1);

                        Location endPoint = new Location("locationA");
                        endPoint.setLatitude(d1);
                        endPoint.setLongitude(d2);

                        double dist = startPoint.distanceTo(endPoint);
                        if (dist < 7000) {
                            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            notification.setSmallIcon(R.drawable.icon2);
                            notification.setTicker("Task");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle(s3);//.getValue().toString());
                            notification.setSound(sounduri);
                            notification.setContentText(s4);

                            Intent intent = new Intent(TaskActivity.this, FinishTask.class);
                            intent.putExtra("place", s3);
                            intent.putExtra("task", s4);
                            intent.putExtra("id", s5);
                            intent.setAction(Long.toString(System.currentTimeMillis()));
                            PendingIntent pendingIntent = PendingIntent.getActivity(TaskActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pendingIntent);

                            Random random = new Random();
                            int m = random.nextInt(9999 - 1000) + 1000;

                            nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(m, notification.build());

                        }

                    }


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            i=1;
        }




/*        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            txcurrent.setText(txcurrent.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }
*/
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(TaskActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}
