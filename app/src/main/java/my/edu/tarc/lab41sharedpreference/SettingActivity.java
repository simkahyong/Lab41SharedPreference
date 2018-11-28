package my.edu.tarc.lab41sharedpreference;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingActivity extends AppCompatActivity {
    private static final String PREF_NAME = "my.edu.tarc.lab41sharedpreference" ;
    private EditText editTextName;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        editTextName = findViewById(R.id.editTextName);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);

        // context level shared preferences file
        // use this because main & setting activities use shared preferences
        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        // OR activity level shared preferences file
        // sharedPreferences = getPreferences(MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name;
        int gender; // default = -1, male = 1, female = 0

        name = sharedPreferences.getString(getString(R.string.user_name),getString(R.string.no_name));
        // OR (not recommended)
        // name = sharedPreferences.getString("user_name","No name");

        gender = sharedPreferences.getInt(getString(R.string.gender),-1);
        editTextName.setText(name);
        if(gender == 1){
            radioButtonMale.setChecked(true);
        }else if(gender == 0){
            radioButtonFemale.setChecked(true);
        }
    }

    @Override
    // Setting Activity writes shared preferences at onPause
    protected void onPause() {
        super.onPause();
        // create an editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!TextUtils.isEmpty(editTextName.getText().toString())){
            String name = editTextName.getText().toString();
            editor.putString(getString(R.string.user_name), name);
        }
        int gender = radioGroupGender.getCheckedRadioButtonId();
        if(gender == R.id.radioButtonMale){
            editor.putInt(getString(R.string.gender),1);

        }else if(gender == R.id.radioButtonFemale) {
            editor.putInt(getString(R.string.gender),0);
        }
        // apply changes to the Shared Preference
        editor.apply();
    }
}
