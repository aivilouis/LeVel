package umn.ac.id.level;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Objects;

public class FAQ extends AppCompatActivity {
    private Bitmap bm;
    private ExpandListAdapter ExpAdapter;
    private ArrayList<ExpandListGroup> ExpListItems;
    private ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(FAQ.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.faq_actionbar);

    }

    public ArrayList<ExpandListGroup> SetStandardGroups() {
        ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
        ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru1 = new ExpandListGroup();
        gru1.setName("What is LeVel?");
        ExpandListChild ch1_1 = new ExpandListChild();
        ch1_1.setName("LeVel stands for Let's Travel is a travel social media app where you can share your itinerary and experience. Let's Travel!");
        ch1_1.setTag(null);
        list2.add(ch1_1);
        gru1.setItems(list2);

        list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru2 = new ExpandListGroup();
        gru2.setName("What is ");
        ExpandListChild ch2_1 = new ExpandListChild();
        ch2_1.setName("Blablabala");
        ch2_1.setTag(null);
        list2.add(ch2_1);
        gru2.setItems(list2);

        list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru3 = new ExpandListGroup();
        gru3.setName("Report");
        ExpandListChild ch3_1 = new ExpandListChild();
        ch3_1.setName("Daily Report");
        ch3_1.setTag(null);
        list2.add(ch3_1);
        gru3.setItems(list2);

        list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru4 = new ExpandListGroup();
        gru4.setName("Report");
        ExpandListChild ch4_1 = new ExpandListChild();
        ch4_1.setName("Daily Report");
        ch4_1.setTag(null);
        list2.add(ch4_1);
        gru4.setItems(list2);

        list.add(gru1);
        list.add(gru2);
        list.add(gru3);
        list.add(gru4);
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.close) {
            Intent intent = new Intent(FAQ.this, Account.class);
            intent.putExtra("IMG", bm);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}