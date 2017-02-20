package com.example.user.moviesdb;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.user.moviesdb.adapters.PersonFavouritesAdapter;
import com.example.user.moviesdb.adapters.PersonRatedAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PersonRated extends AppCompatActivity {

    private static final String TAG = "PersonRated";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private ValueEventListener mDatabaseListener;
    private RecyclerView recyclerView;
    private PersonRatedAdapter adapter;
    TextView back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_rated);

        back = (TextView) findViewById(R.id.back_from_rated);
        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                });

                recyclerView = (RecyclerView)findViewById(R.id.movie_rated_list);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            });
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        adapter = new PersonRatedAdapter(this);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Intent loginIntent = new Intent(PersonRated.this, LoginActivity.class);
            startActivity(loginIntent);
        }else{
            final String user_id = mAuth.getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("ratings");
            Query query = mDatabase.orderByChild("person_id").equalTo(user_id);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    com.example.user.moviesdb.data.PersonRated personRated  = dataSnapshot.getValue(com.example.user.moviesdb.data.PersonRated.class);
                    if(personRated != null){
                        adapter.getData(personRated);
                    }
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
