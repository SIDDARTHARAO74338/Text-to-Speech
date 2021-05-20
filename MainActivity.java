package com.codewithsr.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private EditText editText;
    private Button about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goabout = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(goabout);
            }
        });


        editText = findViewById(R.id.editText);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLanguage = textToSpeech.setLanguage(Locale.US);
                    if (ttsLanguage == TextToSpeech.LANG_MISSING_DATA
                            || ttsLanguage == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else
                        Log.i("TTS", "Language Supported.");
                    Log.i("TTS", "Initialization successful.");
                } else
                    Toast.makeText(getApplicationContext(), "TTS initialization failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void convertTTS(View view) {
        String text = editText.getText().toString();
        Log.i("TTS", "TEXT:" + text);
        int speechStatus = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR)
            Log.e("TTS", "error converting Text to Speech!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

    }
}