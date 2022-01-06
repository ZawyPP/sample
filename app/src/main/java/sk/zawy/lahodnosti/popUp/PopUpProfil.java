package sk.zawy.lahodnosti.popUp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.control.EncryptData;
import sk.zawy.lahodnosti.login.Profil;

public class PopUpProfil {

    private Context context;

    private EditText name;
    private TextView email;
    private EditText phone;
    private TextView verification;
    private TextView onlineReservation;

    private Profil profil;
    private AlertDialog alertDialog;
    private boolean isVerification=false;
    private boolean allowOnlineReservation=false;
    private boolean complete=false;

    public PopUpProfil(Context context) {
        this.context = context;
        this.profil=Profil.getInstance();
        openProfil();
    }

    private void openProfil() {
        isVerification=profil.isVerification();
        allowOnlineReservation=profil.isOnlineReservation();
        complete=profil.isCompleteProfil();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.popup_profil, null);

        name=(EditText) promptView.findViewById(R.id.profil_name2);
        email=(TextView) promptView.findViewById(R.id.profil_email2);
        phone=(EditText) promptView.findViewById(R.id.profil_phone);
        verification=(TextView)promptView.findViewById(R.id.verification);
        onlineReservation=(TextView)promptView.findViewById(R.id.online_reservation);

        if(isVerification){
            TextView infoName=(TextView) promptView.findViewById(R.id.infoForName);
            TextView infoEmail=(TextView) promptView.findViewById(R.id.infoForEmail);
            TextView infoPhone=(TextView) promptView.findViewById(R.id.infoForPhone);
            TextView infoVerificate=(TextView) promptView.findViewById(R.id.infoForVerificate);
            infoName.setVisibility(View.GONE);
            infoEmail.setVisibility(View.GONE);
            infoPhone.setVisibility(View.GONE);
            infoVerificate.setVisibility(View.GONE);
        }

        if(profil.getReservation_name()!=null){
            name.setText(EncryptData.finalData(EncryptData.DECRYPT_MODE,profil.getReservation_name()));
            name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            name.setEnabled(false);
        }else{
            name.setText(profil.getName());
        }

        if(profil.getEmail().contains("@")) {
            email.setText(profil.getEmail());
            email.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        if(profil.getPhone()!=null && profil.isVerification()) {
            phone.setText(EncryptData.finalData(EncryptData.DECRYPT_MODE,profil.getPhone()));
            phone.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            phone.setEnabled(false);

            verification.setText(R.string.verified);
            verification.setTextColor(context.getResources().getColor(R.color.colorAccent));
            verification.setClickable(false);
        }else{
            verification.setText(R.string.unverified);
            verification.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
        }


        if(profil.isOnlineReservation()){
            onlineReservation.setText(R.string.allowed);
            onlineReservation.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            onlineReservation.setText(R.string.notallowed);
            onlineReservation.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
        }


        buildAlertdialog(promptView);
    }

    private void buildAlertdialog(View promptView) {
        alertDialog=new AlertDialog.Builder(context)
                .setView(promptView)
                .setNegativeButton(R.string.close,null)
                .setPositiveButton(R.string.save, null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button buttonPositive=((AlertDialog)alertDialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if(!isVerification) {
                    buttonPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (!areErrors()) {
                                Map<String, Object> object = new HashMap<>();
                                object.put(Profil.USER_NAME, EncryptData.finalData(EncryptData.ENCRYPT_MODE,name.getText().toString()));
                                object.put(Profil.USER_PHONE, EncryptData.finalData(EncryptData.ENCRYPT_MODE,phone.getText().toString()));
                                object.put(Profil.USER_ID, email.getText().toString());
                                object.put(Profil.USER_PHONE_VERIFICATION, isVerification);
                                object.put(Profil.USER_ONLINE_RESERVATION, allowOnlineReservation);
                                object.put(Profil.USER_PROFIL_COMPLETE, complete);
                                profil.setProfilData(object);

                                new PopUpVerification(context, object);
                                alertDialog.dismiss();
                            }
                        }
                    });
                }else if(isVerification){
                    buttonPositive.setEnabled(false);
                    buttonPositive.setVisibility(View.GONE);
                }
            }
        });
        alertDialog.show();
    }

    private boolean areErrors() {
        boolean error=false;
        if(name.getText().length()<4){
            name.setError("?");
            error=true;
        }

        if(phone.getText().toString().trim().length()<12){
            if(phone.getText().toString().contains("+")){
                phone.setError(context.getResources().getString(R.string.errornumber_verif));
            }else{
            phone.setError(context.getResources().getString(R.string.errornumber_verif2));
            }
            error=true;
        }else if(!phone.getText().toString().startsWith("+")){
            phone.setError(context.getResources().getString(R.string.errornumber_verif2));
            error=true;
        }

        return error;
    }
}
