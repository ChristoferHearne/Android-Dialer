package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import se.miun.chhe1903.dt031g.dialer.data.Number;
import se.miun.chhe1903.dt031g.dialer.data.NumberDao;
import se.miun.chhe1903.dt031g.dialer.data.NumberDatabase;
import se.miun.chhe1903.dt031g.dialer.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private NumberDatabase db;
    private NumberDao numberDao;
    private List<Number> storedCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds sweden = new LatLngBounds(new LatLng(55.001099, 11.10694), new LatLng(69.063141, 24.16707));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(sweden, 32));
        addCallMarkers();
    }

    private void addCallMarkers(){

        // Get calls
        db = NumberDatabase.getInstance(this);
        numberDao = db.numberDao();
        storedCalls = numberDao.getAll();

        // Create custom marker
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher_foreground);
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap customMarker = Bitmap.createScaledBitmap(b, width, height, false);

        for (Number storedCall: storedCalls){
            Boolean hasLocationData = storedCall.getLatitude() != 0.0 && storedCall.getLongitude() != 0.0;
            if (hasLocationData){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(storedCall.getLatitude(), storedCall.getLongitude()))
                        .title(storedCall.getNumber())
                        .snippet(storedCall.getTimestamp())
                        .icon(BitmapDescriptorFactory.fromBitmap(customMarker)));

            }
        }
    }
}