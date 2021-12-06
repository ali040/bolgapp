package com.example.mynews;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myAdapter adapter;
    private AdView mAdView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recview);
        MobileAds.initialize(this);
        getSupportActionBar().hide();
        mAdView = findViewById(R.id.adView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>(

        ).setQuery(FirebaseDatabase.getInstance().getReference().child("news"),model.class).build();


       adapter = new myAdapter(options);
        recyclerView.setAdapter(adapter);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
//    TextView fullName, email , phone, verifyMsg,verifybtn ;
//    FirebaseAuth fAuth;
//    FirebaseFirestore fstore;
//    String userID;
//    private ImageView profilePic;
//    private Uri imageuri;
//    private FirebaseStorage storage;
//    private StorageReference storageReference;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        fullName =(TextView) findViewById(R.id.fullName);
//        email =(TextView)findViewById(R.id.email);
//        phone = (TextView)findViewById(R.id.phone);
//        fAuth = FirebaseAuth.getInstance();
//        fstore= FirebaseFirestore.getInstance();
//        verifybtn=(TextView)findViewById(R.id.verifybtn);
//        verifyMsg=(TextView)findViewById(R.id.notverifymsg);
//        profilePic =(ImageView)findViewById(R.id.profilepic);
//        storage =FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
//        userID = fAuth.getCurrentUser().getUid();
//        final FirebaseUser user = fAuth.getCurrentUser();
//        DocumentReference documentReference = fstore.collection("users").document(userID);
//        documentReference.addSnapshotListener(HomeActivity.this,(documentSnapshot,e)->{
//            phone.setText(documentSnapshot.getString("phone"));
//            email.setText(documentSnapshot.getString("email"));
//            fullName.setText(documentSnapshot.getString("fname"));
//        });
//        if(!user.isEmailVerified()){
//            verifyMsg.setVisibility(View.VISIBLE);
//            verifybtn.setVisibility(View.VISIBLE);
//            verifybtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(view.getContext(), "Verification Link Sent to Email", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(view.getContext(),"Error ! Verification Link is Not Sent "+e.getMessage(),Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                }
//            });
//
//
//        }
//        profilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chosePicture();
//
//            }
//        });
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1&& resultCode == RESULT_OK&&data!=null&&data.getData()!=null){
//            imageuri = data.getData();
//            profilePic.setImageURI(imageuri);
//            UploadPicture();
//
//        }
//    }
//
//    private void UploadPicture() {
//        final  ProgressDialog pd =new ProgressDialog(this);
//        final String randomkey = UUID.randomUUID().toString();
//       StorageReference imageRef = storageReference.child("image/"+randomkey);
//
//       imageRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//           @Override
//           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//               pd.dismiss();
//
//           }
//       }).addOnFailureListener(new OnFailureListener() {
//           @Override
//           public void onFailure(@NonNull Exception e) {
//               pd.dismiss();
//
//           }
//       }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//           @Override
//           public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//               double progresspercent = (100.00+ snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
//             pd.setMessage("percente " +(int) progresspercent + '%');
//
//           }
//       });
//    }
//    private void chosePicture() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,1);
//    }
//    public void logout(View view){
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//        finish();
//    }
//}