package sk.zawy.lahodnosti.popUp;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import sk.zawy.lahodnosti.R;

import sk.zawy.lahodnosti.control.EncryptData;
import sk.zawy.lahodnosti.login.PhoneVerification;
import sk.zawy.lahodnosti.login.Profil;

import static sk.zawy.lahodnosti.login.Profil.USER_PHONE;

public class PopUpVerification implements View.OnClickListener {

    private Context context;
    private Map<String,Object> object;

    private EditText number;
    private EditText code;
    private Button buttonSendSMS;
    private Button buttonCode;

    private PhoneVerification phoneVerification;

    private AlertDialog alertDialog;
    private boolean isVerification=false;
    private boolean complete=false;

    public PopUpVerification(Context context, Map object) {
        this.context = context;
        this.object =(Map<String,Object> ) object;

        open_profil();
    }

    private void open_profil() {
        Profil profil=Profil.getInstance();

        isVerification=profil.isVerification();
        complete=profil.isCompleteProfil();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if(!isVerification){
            View promptView = layoutInflater.inflate(R.layout.popup_verification, null);
            phoneVerification=new PhoneVerification(context,promptView,object); //@param 'view' for verification status

            number =(EditText) promptView.findViewById(R.id.number);
            code=(EditText) promptView.findViewById(R.id.code);
            buttonCode=(Button)promptView.findViewById(R.id.buttonCode);
            buttonCode.setOnClickListener(this);
            buttonSendSMS=(Button)promptView.findViewById(R.id.buttonSendSMS);
            buttonSendSMS.setOnClickListener(this);

            number.setText(EncryptData.finalData(EncryptData.DECRYPT_MODE,(String)object.get(USER_PHONE)));
            number.setEnabled(false);

            alertDialog=new AlertDialog.Builder(context)
                    .setView(promptView)
                    .setNegativeButton(R.string.close,null)
                    .show();
        }


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonSendSMS:
                //Verification: send code
                phoneVerification.startPhoneVerification(EncryptData.finalData(EncryptData.DECRYPT_MODE,(String)object.get(USER_PHONE)));
                break;
            case R.id.buttonCode:
                //Verification: check code
                if(code.getText().toString().length()==6){
                    phoneVerification.verifiPhoneWithCode(code.getText().toString());
                }else {
                    code.setError(context.getResources().getString(R.string.error_verif_code));
                }
                break;
        }
    }


}
