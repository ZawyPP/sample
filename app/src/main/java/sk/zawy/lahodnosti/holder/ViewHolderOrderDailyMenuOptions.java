package sk.zawy.lahodnosti.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import sk.zawy.lahodnosti.R;
import sk.zawy.lahodnosti.accessories.TextEdit;
import sk.zawy.lahodnosti.preferences.Preferences;

import static sk.zawy.lahodnosti.sqlite.StaticValue.COLUMN_PERSON_INFO;

public class ViewHolderOrderDailyMenuOptions implements CompoundButton.OnCheckedChangeListener, TextWatcher {

    private int countInt=0;

    public CheckBox classicBox;
    public CheckBox veganBox;
    public CheckBox celiaticBox;
    public CheckBox veganCeliaticBox;
    public CheckBox deliveryBox;
    public EditText classicPortion,classicHalfPortion;
    public EditText veganPortion,veganHalfPortion;
    public EditText celiaticPortion,celiaticHalfPortion;
    public EditText veganCeliaticPortion,veganCeliaticHalfPortion;
    public EditText deliveryAdress;
    public EditText addInfo;
    public TextView count;

    private LinearLayout lin1;
    private LinearLayout lin2;
    private LinearLayout lin3;
    private LinearLayout lin4;
    public LinearLayout deliveryLin;
    public LinearLayout textForButton;

    public ImageButton okButtonSpecial;
    public ImageButton cancelButtonSpecial;

    public ViewHolderOrderDailyMenuOptions(View base) {
        classicBox=(CheckBox)base.findViewById(R.id.checkBoxClassic);
        veganBox=(CheckBox)base.findViewById(R.id.checkBoxVegan);
        celiaticBox=(CheckBox)base.findViewById(R.id.checkBoxGlutenfree);
        veganCeliaticBox=(CheckBox)base.findViewById(R.id.checkBoxVeganGlutenfree);
        deliveryBox=(CheckBox)base.findViewById(R.id.checkDelivery);

        lin1=(LinearLayout)base.findViewById(R.id.lin1);
        lin2=(LinearLayout)base.findViewById(R.id.lin2);
        lin3=(LinearLayout)base.findViewById(R.id.lin3);
        lin4=(LinearLayout)base.findViewById(R.id.lin4);
        deliveryLin=(LinearLayout)base.findViewById(R.id.deliveryLin);
        textForButton=(LinearLayout)base.findViewById(R.id.layButtonText);

        classicBox.setOnCheckedChangeListener(this);
        veganBox.setOnCheckedChangeListener(this);
        celiaticBox.setOnCheckedChangeListener(this);
        veganCeliaticBox.setOnCheckedChangeListener(this);
        deliveryBox.setOnCheckedChangeListener(this);

        classicPortion=(EditText)base.findViewById(R.id.classicPortion);
        classicHalfPortion=(EditText)base.findViewById(R.id.classicHalfPortion);
        veganPortion=(EditText)base.findViewById(R.id.veganPortion);
        veganHalfPortion=(EditText)base.findViewById(R.id.veganHalfPorion);
        celiaticPortion=(EditText)base.findViewById(R.id.celiaticPortion);
        celiaticHalfPortion=(EditText)base.findViewById(R.id.celiaticHalfPortion);
        veganCeliaticPortion=(EditText)base.findViewById(R.id.veganCeliaticPortion);
        veganCeliaticHalfPortion=(EditText)base.findViewById(R.id.veganCeliaticHalfPorion);
        deliveryAdress=(EditText)base.findViewById(R.id.deliveryAdress);
        addInfo=(EditText)base.findViewById(R.id.infoToOrder);
        count=(TextView) base.findViewById(R.id.reservationOptionsCount);

        classicPortion.addTextChangedListener(this);
        classicHalfPortion.addTextChangedListener(this);
        veganPortion.addTextChangedListener(this);
        veganHalfPortion.addTextChangedListener(this);
        celiaticPortion.addTextChangedListener(this);
        celiaticHalfPortion.addTextChangedListener(this);
        veganCeliaticPortion.addTextChangedListener(this);
        veganCeliaticHalfPortion.addTextChangedListener(this);

        okButtonSpecial=(ImageButton) base.findViewById(R.id.placeAnOrder2);
        cancelButtonSpecial=(ImageButton) base.findViewById(R.id.cancelOrder2);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkBoxClassic:
                if(isChecked){
                    lin1.setAlpha(1.0f);
                    classicPortion.setHint("0");
                    classicHalfPortion.setHint("0");
                    classicPortion.setEnabled(true);
                    classicHalfPortion.setEnabled(true);
                }else{
                    classicBox.setError(null);
                    lin1.setAlpha(0.3f);
                    classicPortion.setHint("x");
                    classicHalfPortion.setHint("x");
                    classicPortion.setText("");
                    classicHalfPortion.setText("");
                    classicPortion.setEnabled(false);
                    classicHalfPortion.setEnabled(false);
                    countPortions();
                }

                break;
            case R.id.checkBoxVegan:
                if(isChecked){
                    lin2.setAlpha(1.0f);
                    veganPortion.setHint("0");
                    veganHalfPortion.setHint("0");
                    veganPortion.setEnabled(true);
                    veganHalfPortion.setEnabled(true);
                }else{
                    veganBox.setError(null);
                    lin2.setAlpha(0.3f);
                    veganPortion.setHint("x");
                    veganHalfPortion.setHint("x");
                    veganPortion.setText("");
                    veganHalfPortion.setText("");
                    veganPortion.setEnabled(false);
                    veganHalfPortion.setEnabled(false);
                    countPortions();
                }
                break;
            case R.id.checkBoxGlutenfree:
                if(isChecked){
                    lin3.setAlpha(1.0f);
                    celiaticPortion.setHint("0");
                    celiaticHalfPortion.setHint("0");
                    celiaticPortion.setEnabled(true);
                    celiaticHalfPortion.setEnabled(true);
                }else{
                    celiaticBox.setError(null);
                    lin3.setAlpha(0.3f);
                    celiaticPortion.setHint("x");
                    celiaticHalfPortion.setHint("x");
                    celiaticPortion.setText("");
                    celiaticHalfPortion.setText("");
                    celiaticPortion.setEnabled(false);
                    celiaticHalfPortion.setEnabled(false);
                    countPortions();
                }
                break;
            case R.id.checkBoxVeganGlutenfree:
                if(isChecked){
                    lin4.setAlpha(1.0f);
                    veganCeliaticPortion.setHint("0");
                    veganCeliaticHalfPortion.setHint("0");
                    veganCeliaticPortion.setEnabled(true);
                    veganCeliaticHalfPortion.setEnabled(true);
                }else{
                    veganCeliaticBox.setError(null);
                    lin4.setAlpha(0.3f);
                    veganCeliaticPortion.setHint("x");
                    veganCeliaticHalfPortion.setHint("x");
                    veganCeliaticPortion.setText("");
                    veganCeliaticHalfPortion.setText("");
                    veganCeliaticPortion.setEnabled(false);
                    veganCeliaticHalfPortion.setEnabled(false);
                    countPortions();
                }
                break;
            case R.id.checkDelivery:
                if(isChecked){
                    deliveryAdress.setEnabled(true);
                    deliveryAdress.setAlpha(1.0f);
                    deliveryAdress.setText(new Preferences(deliveryAdress.getContext(),Preferences.PREFERENCE).loadData(Preferences.LAST_EDIT_ADDRESS));
                }else{
                    deliveryAdress.setError(null);
                    deliveryAdress.setEnabled(false);
                    deliveryAdress.setAlpha(0.3f);
                }
                break;
        }
    }


    public String getOptions(){

        StringBuilder builder=new StringBuilder();

        if(classicBox.isChecked()){
            int classicCount =TextEdit.textToNumber(classicPortion.getText().toString());
            int classicHalfCount =TextEdit.textToNumber(classicHalfPortion.getText().toString());

            if((classicCount+classicHalfCount)==0){
                classicBox.setError("Nezadaný počet porcií");
                builder=null;

                return null;
            }
            for( ;classicCount!=0 ;){
                builder.append("α");
                countInt++;
                classicCount--;
            }
            for( ;classicHalfCount!=0 ; ){
                builder.append("Ω");
                countInt++;
                classicHalfCount--;
            }
        }

        if(veganBox.isChecked()){
            int veganCount =TextEdit.textToNumber(veganPortion.getText().toString());
            int veganHalfCount =TextEdit.textToNumber(veganHalfPortion.getText().toString());

            if((veganCount+veganHalfCount)==0){
                veganBox.setError("Nezadaný počet porcií");
                builder=null;
                return null;
            }
            for( ;veganCount!=0 ; ){
                builder.append("Φ");
                countInt++;
                veganCount--;
            }
            for( ;veganHalfCount!=0 ; ){
                builder.append("φ");
                countInt++;
                veganHalfCount--;
            }

        }

        if(celiaticBox.isChecked()){
            int celiaticCount =TextEdit.textToNumber(celiaticPortion.getText().toString());
            int celiaticHalfCount =TextEdit.textToNumber(celiaticHalfPortion.getText().toString());

            if((celiaticCount+celiaticHalfCount)==0){
                celiaticBox.setError("Nezadaný počet porcií");
                builder=null;
                return null;
            }

            for( ;celiaticCount!=0 ; ){
                builder.append("Σ");
                countInt++;
                celiaticCount--;
            }
            for( ;celiaticHalfCount!=0 ; ){
                builder.append("σ");
                countInt++;
                celiaticHalfCount--;
            }

        }

        if(veganCeliaticBox.isChecked()){
            int veganCeliaticCount =TextEdit.textToNumber(veganCeliaticPortion.getText().toString());
            int veganCeliaticHalfCount =TextEdit.textToNumber(veganCeliaticHalfPortion.getText().toString());

            if((veganCeliaticCount+veganCeliaticHalfCount)==0){
                veganCeliaticBox.setError("Nezadaný počet porcií");
                builder=null;
                return null;
            }
            for( ;veganCeliaticCount!=0 ; ){
                builder.append("ε");
                countInt++;
                veganCeliaticCount--;
            }
            for( ;veganCeliaticHalfCount!=0 ; ){
                builder.append("δ");
                countInt++;
                veganCeliaticHalfCount--;
            }

        }

        if(deliveryBox.isChecked()){
            if(deliveryAdress.getText().toString().length()>3) {
                builder.append(String.format("▲%s▼", deliveryAdress.getText().toString()));
                new Preferences(deliveryAdress.getContext(),Preferences.PREFERENCE).saveData(Preferences.LAST_EDIT_ADDRESS,deliveryAdress.getText().toString());
            }else{
                deliveryAdress.setError("Vyplnte adresu doručenia!");
                builder=null;
                return null;
            }
        }

        if(countInt>0||(deliveryAdress.getText().toString().length()!=0 && deliveryBox.isChecked())) {
            return String.format("►%s◄%s",builder.toString(),addInfo.getText().toString());
        }else {
            return null;
        }

    }


    private int countPortions(){
        int reservationNo=0;
        if(classicBox.isChecked()){
            reservationNo+=TextEdit.textToNumber(classicPortion.getText().toString());
            reservationNo+=TextEdit.textToNumber(classicHalfPortion.getText().toString());
        }
        if(veganBox.isChecked()){
            reservationNo+=TextEdit.textToNumber(veganPortion.getText().toString());
            reservationNo+=TextEdit.textToNumber(veganHalfPortion.getText().toString());
        }
        if(celiaticBox.isChecked()){
            reservationNo+=TextEdit.textToNumber(celiaticPortion.getText().toString());
            reservationNo+=TextEdit.textToNumber(celiaticHalfPortion.getText().toString());
        }
        if(veganCeliaticBox.isChecked()){
            reservationNo+=TextEdit.textToNumber(veganCeliaticPortion.getText().toString());
            reservationNo+=TextEdit.textToNumber(veganCeliaticHalfPortion.getText().toString());
        }

        this.count.setText(String.valueOf(reservationNo));
        return reservationNo;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        countPortions();
    }

    public void setOptions(Map<String, Object> result) {
        if(result!=null){
            String string=(String)result.get(COLUMN_PERSON_INFO);

            cancelButtonSpecial.setVisibility(View.VISIBLE);
            textForButton.setVisibility(View.VISIBLE);

            String onlyOptions="";
            String onlyAdress=null;
            String info="";
            String diet="";

            onlyOptions=string.substring(string.indexOf("►")+1,string.indexOf("◄"));

            //get only adress
            if(onlyOptions.contains("▲")&&onlyOptions.contains("▼")) {
                onlyAdress = onlyOptions.substring(onlyOptions.indexOf("▲") + 1, onlyOptions.indexOf("▼"));
            }

            info=string.replaceAll(string.substring(string.indexOf("►"),string.indexOf("◄")+1),"");

            if(onlyAdress!=null) {
                diet= onlyOptions.replaceAll(onlyOptions.substring(onlyOptions.indexOf("▲"),onlyOptions.indexOf("▼")+1),"");
            }else{
                diet= onlyOptions;
            }

            addInfo.setText(info);
            if(onlyAdress!=null) {
                deliveryAdress.setText(onlyAdress);
                deliveryBox.setChecked(true);
            }

            if(diet.length()>0){
                if(diet.contains("α")){
                    classicBox.setChecked(true);
                    int i=(diet.length()-(diet.replaceAll("α","").length()));
                    classicPortion.setText(String.valueOf(i));
                }
                if(diet.contains("Ω")){
                    classicBox.setChecked(true);
                    int i=(diet.length()-(diet.replaceAll("Ω","").length()));
                    classicHalfPortion.setText(String.valueOf(i));
                }
                if(diet.contains("Φ")){
                    veganBox.setChecked(true);
                    int i=(diet.length()-(diet.replaceAll("Φ","").length()));
                    veganPortion.setText(String.valueOf(i));
                }
                if(diet.contains("φ")){
                    veganBox.setChecked(true);
                    int i=(diet.length()-(diet.replaceAll("φ","").length()));
                    veganHalfPortion.setText(String.valueOf(i));
                }
                if(diet.contains("Σ")){
                    celiaticBox.setChecked(true);
                    int i=(diet.length()-(diet.replaceAll("Σ","").length()));
                    celiaticPortion.setText(String.valueOf(i));
                }
                if(diet.contains("σ")){
                    celiaticBox.setChecked(true);
                    int i=(diet.length()-(diet.replaceAll("σ","").length()));
                    celiaticHalfPortion.setText(String.valueOf(i));
                }
                if(diet.contains("ε")){
                    veganCeliaticBox.setChecked(true);
                    int i=(diet.length()-(diet.replaceAll("ε","").length()));
                    veganCeliaticPortion.setText(String.valueOf(i));
                }
                if(diet.contains("δ")){
                    veganCeliaticBox.setChecked(true);
                    int i=(diet.length()-(diet.replaceAll("δ","").length()));
                    veganCeliaticHalfPortion.setText(String.valueOf(i));
                }

            }
        }
    }
}
