package umn.ac.id.level;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class AddPost extends AppCompatActivity {

    private ImageView img;
    private Bitmap bm;
    Uri uri;
    String source = "";

    @SuppressLint({"QueryPermissionsNeeded", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.addpost_actionbar);

        img = findViewById(R.id.imageView);
        Button galleryBtn = findViewById(R.id.galleryBtn);
        Button cameraBtn = findViewById(R.id.cameraBtn);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = null;
                        if (extras != null) {
                            imageBitmap = (Bitmap) extras.get("data");
                        }
                        img.setImageBitmap(imageBitmap);
                        bm = imageBitmap;
                        source = "camera";
                    }
                }
        );

        ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        uri = data.getData();
                        img.setImageURI(uri);
                        source = "gallery";
                    }
                }
        );

        cameraBtn.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                activityResultLauncher.launch(takePictureIntent);
            }
        });

        galleryBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher2.launch(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_addpost);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.action_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_addpost:
                    return true;
                case R.id.action_location:
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.action_account:
                    startActivity(new Intent(getApplicationContext(), Account.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addpost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_next) {
            Intent intent = new Intent(AddPost.this, AddPostDetails.class);
            if (source.contentEquals("camera")) {
                if (bm == null) {
                    Toast.makeText(AddPost.this, "Please select image", Toast.LENGTH_LONG).show();
                    return false;
                }
                intent.putExtra("IMG", bm);
            } else if (source.contentEquals("gallery")) {
                if (uri == null) {
                    Toast.makeText(AddPost.this, "Please select image", Toast.LENGTH_LONG).show();
                    return false;
                }
                intent.putExtra("IMG", uri);
            } else {
                Toast.makeText(AddPost.this, "Please select image", Toast.LENGTH_LONG).show();
                return false;
            }

            intent.putExtra("SOURCE", source);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}