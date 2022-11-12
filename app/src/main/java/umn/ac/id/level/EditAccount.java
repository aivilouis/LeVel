package umn.ac.id.level;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditAccount extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_icon);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.done_icon);

        Button editprofileBtn = findViewById(R.id.editprofileBtn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editaccount, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            Intent intent = new Intent(EditAccount.this, Account.class);
            Toast.makeText(this,"Profile Update Successfully", Toast.LENGTH_SHORT).show();
            this.startActivity(intent);
            return false;
        }
        if (id == R.id.action_cancel){
            Intent intent = new Intent(EditAccount.this, Account.class);
            this.startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
