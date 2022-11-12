package umn.ac.id.level;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Objects;

public class AddPostDetails extends AppCompatActivity {

    private EditText days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.addpost_actionbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bitmap bm = (Bitmap)intent.getParcelableExtra("IMG");
        ImageView img = findViewById(R.id.imgView);
        img.setImageBitmap(bm);

        days = findViewById(R.id.input_days);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addpostdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            Intent intent = new Intent(AddPostDetails.this, AddPostDetails2.class);
            intent.putExtra("DAYS", days.getText().toString());
            this.startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            Intent intent = new Intent(AddPostDetails.this, AddPost.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}