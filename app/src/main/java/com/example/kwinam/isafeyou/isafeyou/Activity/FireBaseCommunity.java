package com.example.kwinam.isafeyou.isafeyou.Activity;

/**
 * Created by KwiNam on 2017-11-18.
 */
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.DataBase.ChatAdapter;
import com.example.kwinam.isafeyou.isafeyou.DataBase.ChatData;
import com.example.kwinam.isafeyou.isafeyou.DataBase.UserData;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class FireBaseCommunity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1001;
    private static final String FCM_MESSAGE_URL = "https://isafeyou-1bc41.firebaseio.com/";
    private static final String SERVER_KEY = "AAAAgGoRvj8:APA91bEy9F4-H_wsmPOpg3l8rn9xS60EKeZuViB9PAYfCLNzMStWFPZsgUhAkVgjO03FpHa8Cu2H1N4qOtiaM16siM_Q64tslpADt8qGY6sUdqOFqeGWfKyAlxv_L7mYWBZ3AiQbrgnp";

    // Firebase - Realtime Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    // Firebase - Authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    // Views
    private ListView mListView; // 채팅메시지를 표시하는 리스트뷰
    private SignInButton mBtnGoogleSignIn; // 로그인 버튼
    private Button mBtnGoogleSignOut; // 로그아웃 버튼
    private TextView mTxtProfileInfo; // 사용자 정보 표시
    private ImageView mImgProfile; // 사용자 프로필 이미지 표시


    // Values
    private ChatAdapter mAdapter;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_main);
        initViews();
        initFirebaseDatabase();
        initFirebaseAuth();
        initValues();
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.list_message);
        mAdapter = new ChatAdapter(this, 0);
        mListView.setAdapter(mAdapter);

        findViewById(R.id.btn_send).setOnClickListener(this);

        mBtnGoogleSignIn = (SignInButton) findViewById(R.id.btn_google_signin);
        mBtnGoogleSignOut = (Button) findViewById(R.id.btn_google_signout);
        mBtnGoogleSignIn.setOnClickListener(this);
        mBtnGoogleSignOut.setOnClickListener(this);

        mTxtProfileInfo = (TextView) findViewById(R.id.txt_profile_info);
        mImgProfile = (ImageView) findViewById(R.id.img_profile);
    }

    private void initFirebaseDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("message");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                mAdapter.add(chatData);
                mListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (mAdapter.getItem(i).firebaseKey.equals(firebaseKey)) {
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateProfile();
            }
        };
    }

    private void initValues() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            userName = "Guest" + new Random().nextInt(5000);
        } else {
            userName = user.getDisplayName();
        }
    }

    private void updateProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // 비 로그인 상태 (메시지를 전송할 수 없다.)
            mBtnGoogleSignIn.setVisibility(View.VISIBLE);
            mBtnGoogleSignOut.setVisibility(View.GONE);
            mTxtProfileInfo.setVisibility(View.GONE);
            mImgProfile.setVisibility(View.GONE);
            findViewById(R.id.btn_send).setVisibility(View.GONE);
            mAdapter.setEmail(null);
            mAdapter.notifyDataSetChanged();
        } else {
            // 로그인 상태
            mBtnGoogleSignIn.setVisibility(View.GONE);
            mBtnGoogleSignOut.setVisibility(View.VISIBLE);
            mTxtProfileInfo.setVisibility(View.VISIBLE);
            mImgProfile.setVisibility(View.VISIBLE);
            findViewById(R.id.btn_send).setVisibility(View.VISIBLE);

            userName = user.getDisplayName(); // 채팅에 사용 될 닉네임 설정
            String email = user.getEmail();
            StringBuilder profile = new StringBuilder();
            profile.append(userName).append("\n").append(user.getEmail());
            mTxtProfileInfo.setText(profile);
            mAdapter.setEmail(email);
            mAdapter.notifyDataSetChanged();

            Picasso.with(this).load(user.getPhotoUrl()).into(mImgProfile);

            UserData userData = new UserData();
            userData.userEmailID = email.substring(0, email.indexOf('@'));
            userData.fcmToken = FirebaseInstanceId.getInstance().getToken();

            mFirebaseDatabase.getReference("users").child(userData.userEmailID).setValue(userData);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateProfile();
                    }
                });
    }

    private void sendPostToFCM(final ChatData chatData, final String message) {
        mFirebaseDatabase.getReference("users")
                .child(chatData.userEmail.substring(0, chatData.userEmail.indexOf('@')))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final UserData userData = dataSnapshot.getValue(UserData.class);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // FMC 메시지 생성 start
                                    JSONObject root = new JSONObject();
                                    JSONObject notification = new JSONObject();
                                    notification.put("body", message);
                                    notification.put("title", getString(R.string.app_name));
                                    root.put("notification", notification);
                                    root.put("to", userData.fcmToken);
                                    // FMC 메시지 생성 end

                                    URL Url = new URL(FCM_MESSAGE_URL);
                                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                                    conn.setRequestMethod("POST");
                                    conn.setDoOutput(true);
                                    conn.setDoInput(true);
                                    conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
                                    conn.setRequestProperty("Accept", "application/json");
                                    conn.setRequestProperty("Content-type", "application/json");
                                    OutputStream os = conn.getOutputStream();
                                    os.write(root.toString().getBytes("utf-8"));
                                    os.flush();
                                    conn.getResponseCode();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(FireBaseCommunity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                updateProfile();
            }
        }
        if(requestCode == 101){
            Log.e("RESULT_OK", "여까진 왔는지라");
            String message = "장소   : " + data.getStringExtra("ADDRESS") + "\n" +
                             "세부 장소: " + data.getStringExtra("ADDRESS_EXTRA") + "\n" +
                             "날짜   : " + data.getStringExtra("DATE") + "\n" +
                             "시간   : " + data.getStringExtra("TIME") + "\n" +
                             "코멘트  : " + data.getStringExtra("COMMENT");
            ChatData chatData = new ChatData();
            chatData.userName = userName;
            chatData.message = message;
            chatData.time = System.currentTimeMillis();
            chatData.userEmail = mAuth.getCurrentUser().getEmail(); // 사용자 이메일 주소
            chatData.userPhotoUrl = mAuth.getCurrentUser().getPhotoUrl().toString(); // 사용자 프로필 이미지 주소
            mDatabaseReference.push().setValue(chatData);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                Intent intent = new Intent(this, CommunityAddActivity.class);
                startActivityForResult(intent,101);
                break;
            case R.id.btn_google_signin:
                signIn();
                break;
            case R.id.btn_google_signout:
                signOut();
                break;
        }
    }
}