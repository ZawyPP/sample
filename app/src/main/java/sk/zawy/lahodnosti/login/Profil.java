package sk.zawy.lahodnosti.login;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.Map;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.control.EncryptData;

public class Profil {

    public static final String USER_PREFS="user_sharedPrefs";
    public static final String USER_NAME="user_full_name";
    public static final String USER_PHONE="user_crypt_phone";
    public static final String USER_ID ="user_id_crypt";
    public static final String USER_VIP="user_spesl";
    public static final String USER_ONLINE_RESERVATION="user_online_reservations";
    public static final String USER_PHONE_VERIFICATION="verification_phone";
    public static final String USER_PROFIL_COMPLETE="complete";
    public static final String USER_DISCOUNT_DM="discountDM";

    private ListenerRegistration listenerRegistration;
    private ListenerRegistration listenerRegistration2;


    private static Profil instance =null;
    private String name = "";
    private String email = "";
    private String photoUrl = "";
    private String tokenMessage = "";

    private String reservation_name;
    private String phone;
    private String discountDM="";
    private Boolean verification=false;
    private Boolean vip=false;
    private Boolean onlineReservation=false;
    private Boolean completeProfil=false;

    private Profil() {
        setUserInfo();
    }

    public static Profil getInstance(){
        if(instance==null){
            instance=new Profil();
        }
        return instance;
    }

    private void setUserInfo() {
        LoginGmail loginSingleton=LoginGmail.getInstance(null);
        FirebaseUser user =loginSingleton.getCurrentUser();

        name=user.getDisplayName();
        email=user.getEmail();
        photoUrl=user.getPhotoUrl().toString();

        checkToken();

    }


    /**Set NavigationDrawer profile info*/
    public void profilND(View headerView){

        setUserInfo();
        TextView profilName=(TextView)headerView.findViewById(R.id.profil_name);
        TextView profilMail=(TextView)headerView.findViewById(R.id.profil_email);
        ImageView profilPhoto=(ImageView) headerView.findViewById(R.id.profil_photo);

        profilName.setText(getName());
        profilMail.setText(getEmail());
        Picasso picasso=new Picasso.Builder(headerView.getContext())
                .memoryCache(new LruCache(4000))
                .build();

        picasso.get().load(photoUrl)
                .priority(Picasso.Priority.HIGH)
                .resize(100,100)
                .into(profilPhoto);
    }


    public void setProfilData(Map<String, Object> user) {
        reservation_name= (String)user.get(USER_NAME);
        phone=(String)user.get(USER_PHONE);
        email=(String)user.get(USER_ID);
        verification=notBooleanNull((Boolean)user.get(USER_PHONE_VERIFICATION));
        onlineReservation=notBooleanNull((Boolean)user.get(USER_ONLINE_RESERVATION));
        vip=notBooleanNull((Boolean)user.get(USER_VIP));
        completeProfil=notBooleanNull((Boolean)user.get(USER_PROFIL_COMPLETE));

        discountDM=user.get(USER_DISCOUNT_DM)==null ? "" : (String)user.get(USER_DISCOUNT_DM);
    }


    private boolean notBooleanNull(Boolean input){
        if(input==null){
            return false;
        }else{
            return input;
        }
    }


    public void kill(){
        reservation_name="";
        phone="";
        email="";
        verification=false;
        onlineReservation=false;
        vip=false;
        completeProfil=false;
        discountDM="";
        if(listenerRegistration!=null) {
            getListenerRegistration().remove();
        }

        instance=null;
    }


    public void checkToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("logData", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        setTokenMessage(token);
                    }
                });


    }

    public String getTokenMessage() {
        return tokenMessage;
    }

    public void setTokenMessage(String tokenMessage) {
        this.tokenMessage = tokenMessage;
    }

    public String getReservation_name() {
        return reservation_name;
    }

    public String getReservation_name(String param) {
       return EncryptData.finalData(EncryptData.ENCRYPT_MODE,(EncryptData.finalData(EncryptData.DECRYPT_MODE,reservation_name)+param));
    }

    public String getPhone() {
        return phone;
    }


    public boolean isVerification() {
        return verification;
    }

    public boolean isVip() {
        return vip;
    }

    public boolean isOnlineReservation() {
        return onlineReservation;
    }

    public boolean isCompleteProfil() {
        return completeProfil;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDiscountDM() {
        return discountDM;
    }

    public ListenerRegistration getListenerRegistration() {
        return listenerRegistration;
    }

    public void setListenerRegistration(ListenerRegistration listenerRegistration) {
        this.listenerRegistration = listenerRegistration;
    }

    public ListenerRegistration getListenerRegistration2() {
        return listenerRegistration2;
    }

    public void setListenerRegistration2(ListenerRegistration listenerRegistration2) {
        this.listenerRegistration2 = listenerRegistration2;
    }
}
