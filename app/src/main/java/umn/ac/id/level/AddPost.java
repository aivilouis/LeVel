package umn.ac.id.level;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;

public class AddPost extends AppCompatActivity {

    private ImageView img;

    @SuppressLint("QueryPermissionsNeeded")
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
                        Bundle extras = null;
                        if (data != null) {
                            extras = data.getExtras();
                        }
                        Bitmap imageBitmap = null;
                        if (extras != null) {
                            imageBitmap = (Bitmap) extras.get("data");
                        }
                        img.setImageBitmap(imageBitmap);
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
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}