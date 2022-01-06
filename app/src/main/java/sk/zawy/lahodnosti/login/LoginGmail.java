package sk.zawy.lahodnosti.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import sk.zawy.lahodnosti.R;

public class LoginGmail extends Intent {

    private Context context ;
    private static final int RC_SIGN_IN = 9;
    private static LoginGmail instance =null;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    private LoginGmail(Context context) {
        this.context=context;
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public static LoginGmail getInstance(Context context){
        if(instance==null){
            instance=new LoginGmail(context);
        }
        return instance;
    }

    public Intent getSignInIntent(){
        return mGoogleSignInClient.getSignInIntent();
    }

    public FirebaseUser getCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser;
    }

    private boolean firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        Task<AuthResult> task= mAuth.signInWithCredential(credential);

        long time1=System.currentTimeMillis();

        for (; !task.isComplete(); ) {
            //wait

            //if task isn't complete for 5 second
            if((System.currentTimeMillis()-time1)>5000) {
                signOut();
                return false;
            }

        }
        if(task.isSuccessful()){
            // Sign in success, update UI with the signed-in user's information
            Log.d("logData", "signInWithCredential:success");
            return true;
        }else{
            Log.w("logData", "signInWithCredential:failure", task.getException());
            return false;
        }

    }

    public boolean onResult(int requestCode, Intent data){
        return onActivityResult(requestCode,data);

    }

    private boolean onActivityResult(int requestCode, Intent data) {

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("logData", "firebaseAuthWithGoogle:" + account.getId());
                return firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("logData", "Google sign in failed", e);
            }
        }
        return false;
    }

    public void signOut(){
        mAuth.signOut();
        mGoogleSignInClient.signOut();
    }


}
