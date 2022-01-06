package sk.zawy.lahodnosti.facebookPosts;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import sk.zawy.lahodnosti.objects.FBpost;

import static sk.zawy.lahodnosti.sqlite.StaticValue.TB_TOKEN;

public class JSONfromFB {

    private String token;
    private String url="https://graph.facebook.com/lahodnostipoprad/posts/?access_token=";

    private List<FBpost> listData=new ArrayList<>();

    public List connectFB() throws Exception {
        HttpsURLConnection connection=null;
        int retry=0;

        //Check token validation, when is invalid try load new one
        for(;retry<2;) {
            token = getToken();
             URL obj = new URL(url + token);
            connection = (HttpsURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            if (responseCode == 400) {
                loadNewTokenToCache();
                retry+=1;
            }else {
                System.out.println("RESPONSECODE: " + responseCode);
                retry = 2;
            }
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        for (int i=0 ; i<jsonArray.length() ; i++){
            FBpost postObj=new FBpost();
            int length=0;
            postObj.setId(jsonArray.getJSONObject(i).getString("id"));
            postObj.setCreated_time(jsonArray.getJSONObject(i).getString("created_time"));

            try {
                postObj.setMessage(jsonArray.getJSONObject(i).getString("message"));
                length+=1;
            }catch (JSONException e){
                postObj.setMessage("");
            }

            try {
                postObj.setStory(jsonArray.getJSONObject(i).getString("story"));
            }catch (JSONException e){
                postObj.setStory("");
            }

            try {
                postObj.setLink(jsonArray.getJSONObject(i).getString("link"));
            }catch (JSONException e){
                postObj.setLink("");
            }

            try {
                postObj.setDescription(jsonArray.getJSONObject(i).getString("description"));
                length+=1;
            }catch (JSONException e){
                postObj.setDescription("");
            }

            try {
                postObj.setCaption(jsonArray.getJSONObject(i).getString("caption"));
                length+=1;
            }catch (JSONException e){
                postObj.setCaption("");
            }

            if(length>0) {
                listData.add(postObj);
            }
        }

        return listData;
    }

    private String  loadNewTokenToCache() {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Task<DocumentSnapshot> task=db.collection(TB_TOKEN)
                    .document("TokenFB")
                    .get();

        for (; !task.isComplete(); ) {
            //wait
        }

        try {
                String token = (String) task.getResult().getData().get("token");
                Log.d("logData", "New token is loaded from server");
                return token;

        }catch (Exception e){
                 Log.d("logData", "Error : token isn't loaded - " + e);
            }
        return "";
    }


    private String getToken(){
        Task<DocumentSnapshot> task = null;

        FirebaseFirestore db=FirebaseFirestore.getInstance();

        /*Read token from cache DB, when it doesn't exist, read DB from server */
        task=db.collection(TB_TOKEN)
                .document("TokenFB")
                .get(Source.CACHE);

        for (; !task.isComplete(); ) {
            //wait
        }

        try {
            String token = (String) task.getResult().getData().get("token");
            Log.d("logData", "Token is loaded from cache");
            return token;
        }catch (Exception e){
            Log.d("logData", "Error - token isn't found in cache : "+e);
            //in cache isn't item
            //read and return token from server
            return loadNewTokenToCache();
        }

    }

}
