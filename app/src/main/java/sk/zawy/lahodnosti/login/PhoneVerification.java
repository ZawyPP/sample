package sk.zawy.lahodnosti.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.firebase.FirebaseDB;

import static sk.zawy.lahodnosti.login.Profil.USER_ONLINE_RESERVATION;
import static sk.zawy.lahodnosti.login.Profil.USER_PHONE_VERIFICATION;
import static sk.zawy.lahodnosti.login.Profil.USER_PROFIL_COMPLETE;
import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_USERS;

public class PhoneVerification {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Map object;
    private Context context;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog pd;
    private TextView status;
    private EditText codeText;
    private LinearLayout codeLayout;
    private Button button1;
    private Button button2;


    public PhoneVerification(Context context, View promptView, Map object) {
        this.context=context;
        this.object=object;

        pd=new ProgressDialog(context);
        mAuth = FirebaseAuth.getInstance();

        status=(TextView)promptView.findViewById(R.id.status);
        codeText=(EditText) promptView.findViewById(R.id.code);
        codeLayout=(LinearLayout) promptView.findViewById(R.id.codeLayout);
        button1=(Button) promptView.findViewById(R.id.buttonSendSMS);
        button2=(Button) promptView.findViewById(R.id.buttonCode);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("logData", "onVerificationCompleted" );

                pd.hide();
                status.setVisibility(View.VISIBLE);
                status.setTextColor(context.getResources().getColor(R.color.colorAccent));
                status.setText(R.string.tel_n_was_verif);
                signInWithCredential(credential,object);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("logData", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.d("logData", "Invalid request");
                    pd.hide();
                    status.setVisibility(View.VISIBLE);
                    status.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
                    status.setText(R.string.errornumber_verif2);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.d("logData", " The SMS quota for the project has been exceeded");
                    pd.hide();
                    status.setVisibility(View.VISIBLE);
                    status.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
                    status.setText("Číslo nie je možné overiť, skúste neskôr prosím");
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("logData", "onCodeSent:The SMS verification code has been sent");

                codeLayout.setVisibility(View.VISIBLE);
                pd.hide();
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }


    public void startPhoneVerification(String number){

        pd.setMessage(context.getResources().getString(R.string.please_wait));
        pd.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity((Activity) context)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    public void verifiPhoneWithCode(String code){
        Log.d("testovac","VERIFI ID : " +mVerificationId);
        Log.d("testovac","VERIFI CODE : " +code);

        pd.setMessage(context.getResources().getString(R.string.please_wait2));
        pd.show();

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithCredential(credential, object);
        }catch (IllegalArgumentException e){
            pd.hide();
            status.setVisibility(View.VISIBLE);
            status.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
            status.setText(R.string.error_verif_crash);
        }

    }

    private void signInWithCredential(PhoneAuthCredential credential,Map object) {
        mAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("logData","Phone verification:  is successful" );
                    pd.hide();
                    /* Layout disable buttons ... */
                    codeText.setEnabled(false);
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    codeText.setAlpha(0.5f);
                    status.setVisibility(View.VISIBLE);
                    status.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    status.setText(R.string.tel_n_was_verif);

                    /* new user's data will save to db*/
                    object.put(USER_PHONE_VERIFICATION,true);
                    object.put(USER_ONLINE_RESERVATION,true);
                    object.put(USER_PROFIL_COMPLETE,true);
                    Profil.getInstance().setProfilData(object);
                    new FirebaseDB(context).setData(FirebaseDB.UPDATE,object,TB_USERS);

                }else{
                    Log.d("logData","Phone verification:  is not successful" );
                    pd.hide();
                    status.setVisibility(View.VISIBLE);
                    status.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
                    status.setText(R.string.verif_code_error_or);
                }

            }
        });
    }
}
