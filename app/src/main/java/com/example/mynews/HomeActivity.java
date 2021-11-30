package com.example.mynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    TextView fullName, email , phone, verifyMsg,verifybtn ;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fullName =(TextView) findViewById(R.id.fullName);
        email =(TextView)findViewById(R.id.email);
        phone = (TextView)findViewById(R.id.phone);
        fAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        verifybtn=(TextView)findViewById(R.id.verifybtn);
        verifyMsg=(TextView)findViewById(R.id.notverifymsg);

        userID = fAuth.getCurrentUser().getUid();

        final FirebaseUser user = fAuth.getCurrentUser();




        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(HomeActivity.this,(documentSnapshot,e)->{
            phone.setText(documentSnapshot.getString("phone"));
            email.setText(documentSnapshot.getString("email"));
            fullName.setText(documentSnapshot.getString("fname"));
        });
        if(!user.isEmailVerified()){
            verifyMsg.setVisibility(View.VISIBLE);
            verifybtn.setVisibility(View.VISIBLE);

            verifybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Verification Link Sent to Email", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(),"Error ! Verification Link is Not Sent "+e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });


        }
//        if(!user.isEmailVerified()){
//            verifyMsg.setVisibility(View.VISIBLE);
//            resendcode.setVisibility(View.VISIBLE);
//
//            resendcode.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(view.getContext(), "Verification Link Sent to Email", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(view.getContext(),"Error ! Verification Link is Not Sent "+e.getMessage(),Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                }
//            });
//        }

//        DocumentReference documentReference = fstore.collection("user").document(userID);
//        documentReference.addSnapshotListener(HomeActivity.this,(documentSnapshot,e)->{
//           phone.setText(documentSnapshot.getString("phone"));
//           fullName.setText(documentSnapshot.getString("fName"));
//           email.setText(documentSnapshot.getString("email"));
//        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}