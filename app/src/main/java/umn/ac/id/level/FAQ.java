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

public class FAQ extends AppCompatActivity
{

    private Bitmap bm;
    private ExpandListAdapter ExpAdapter;
    private ArrayList<ExpandListGroup> ExpListItems;
    private ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapter(FAQ.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.faq_actionbar);

    }

    public ArrayList<ExpandListGroup> SetStandardGroups()
    {
        ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
        ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru1 = new ExpandListGroup();
        gru1.setName("What is LeVel?");
        ExpandListChild ch1_1 = new ExpandListChild();
        ch1_1.setName("LeVel stands for Let's Travel is a travel social media app where you can share your itinerary and experience.");
        ch1_1.setTag(null);
        list2.add(ch1_1);
        gru1.setItems(list2);

        list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru2 = new ExpandListGroup();
        gru2.setName("How many user types on the LeVel?");
        ExpandListChild ch2_1 = new ExpandListChild();
        ch2_1.setName("There are 3 types: Bussiness Traveler, Leisure Traveler, and Special Interest Traveler.");
        ch2_1.setTag(null);
        list2.add(ch2_1);
        gru2.setItems(list2);

        list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru3 = new ExpandListGroup();
        gru3.setName("Privacy Policy");
        ExpandListChild ch3_1 = new ExpandListChild();
        ch3_1.setName("MetaFOUR built the LeVel app as a Free app. This SERVICE is provided by MetaFOUR at no cost and is intended for use as is.\n" +
                "\n" +
                "This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.\n" +
                "\n" +
                "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                "\n" +
                "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which are accessible at LeVel unless otherwise defined in this Privacy Policy.");
        ch3_1.setTag(null);
        list2.add(ch3_1);
        gru3.setItems(list2);

        list2 = new ArrayList<ExpandListChild>();
        ExpandListGroup gru4 = new ExpandListGroup();
        gru4.setName("Terms & Conditions");
        ExpandListChild ch4_1 = new ExpandListChild();
        ch4_1.setName("By downloading or using the app, these terms will automatically apply to you – you should make sure therefore that you read them carefully before using the app. You’re not allowed to copy or modify the app, any part of the app, or our trademarks in any way. You’re not allowed to attempt to extract the source code of the app, and you also shouldn’t try to translate the app into other languages or make derivative versions. The app itself, and all the trademarks, copyright, database rights, and other intellectual property rights related to it, still belong to MetaFOUR.\n" +
                "\n" +
                "MetaFOUR is committed to ensuring that the app is as useful and efficient as possible. For that reason, we reserve the right to make changes to the app or to charge for its services, at any time and for any reason. We will never charge you for the app or its services without making it very clear to you exactly what you’re paying for.\n" +
                "\n" +
                "The LeVel app stores and processes personal data that you have provided to us, to provide my Service. It’s your responsibility to keep your phone and access to the app secure. We therefore recommend that you do not jailbreak or root your phone, which is the process of removing software restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs, compromise your phone’s security features and it could mean that the LeVel app won’t work properly or at all.");
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.close)
        {
            Intent intent = new Intent(FAQ.this, Account.class);
            intent.putExtra("IMG", bm);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}