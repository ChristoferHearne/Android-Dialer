package se.miun.chhe1903.dt031g.dialer;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import se.miun.chhe1903.dt031g.dialer.data.Number;
import se.miun.chhe1903.dt031g.dialer.data.NumberDao;
import se.miun.chhe1903.dt031g.dialer.data.NumberDatabase;
import se.miun.chhe1903.dt031g.dialer.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    // Auto-created stub from wizard
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        addCallMarkers();
    }

    private void addCallMarkers(){
        db = NumberDatabase.getInstance(this);
        numberDao = db.numberDao();
        storedCalls = numberDao.getAll();
        for (Number storedCall: storedCalls){
            Boolean hasLocationData = storedCall.getLatitude() != 0.0 && storedCall.getLongitude() != 0.0;
            if (hasLocationData){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(storedCall.getLatitude(), storedCall.getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_call_24)))
                        .setTitle(storedCall.getNumber());
            }
        }
    }
}