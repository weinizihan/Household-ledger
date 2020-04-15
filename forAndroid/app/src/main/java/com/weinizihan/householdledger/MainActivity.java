package com.weinizihan.householdledger;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 以下为测试代码 */
        TextView stateText = (TextView) findViewById(R.id.main_state_textView);

        final EditText drN = (EditText) findViewById(R.id.main_drn_editText);
        final EditText drA = (EditText) findViewById(R.id.main_dra_editText);
        final EditText crN = (EditText) findViewById(R.id.main_crn_editText);
        final EditText crA = (EditText) findViewById(R.id.main_cra_editText);

        Button button = (Button) findViewById(R.id.main_button);

        // 检查状态
        final Account account = new Account(this);
        if(account.check()){
            stateText.setText(R.string.state_localok);
        } else {
            stateText.setText(R.string.state_localerr);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String drName = drN.getText().toString();
                String crName = crN.getText().toString();
                float drAmount = Float.parseFloat(drA.getText().toString());
                float crAmount = Float.parseFloat(crA.getText().toString());

                Account.Entry entry = account.new Entry(null, "2020-04-15", "测试一下功能");
                entry.Dr(drName, drAmount);
                entry.Cr(crName, crAmount);
                account.writeEntry(entry);
            }
        });


        /* 测试代码结束 */
    }
}
