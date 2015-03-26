package joandev.speechtest;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    SpeechRecognizer sr;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final String TAG = "ALO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MyService.class));

    }

    public void startSpeech (View v) {
        promptSpeechInput();
    }

//    class listener implements RecognitionListener
//    {
//        public void onReadyForSpeech(Bundle params)
//        {
//            Log.d(TAG, "onReadyForSpeech");
//        }
//        public void onBeginningOfSpeech()
//        {
//            Log.d(TAG, "onBeginningOfSpeech");
//        }
//        public void onRmsChanged(float rmsdB)
//        {
//            Log.d(TAG, "onRmsChanged");
//        }
//        public void onBufferReceived(byte[] buffer)
//        {
//            Log.d(TAG, "onBufferReceived");
//        }
//        public void onEndOfSpeech()
//        {
//            Log.d(TAG, "onEndofSpeech");
//        }
//        public void onError(int error)
//        {
//            Log.d(TAG,  "error " +  error);
//        }
//        public void onResults(Bundle results)
//        {
//            String str = new String();
//            Log.d(TAG, "onResults " + results);
//            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//            for (int i = 0; i < data.size(); i++)
//            {
//                Log.d(TAG, "result " + data.get(i));
//                str += data.get(i);
//            }
//        }
//        public void onPartialResults(Bundle partialResults)
//        {
//            Log.d(TAG, "onPartialResults");
//        }
//        public void onEvent(int eventType, Bundle params)
//        {
//            Log.d(TAG, "onEvent " + eventType);
//        }
//    }
    public void onClick(View v) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
            sr.startListening(intent);
            Log.i("111111","11111111");
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.get(0) != null)
                        if (result.get(0).contains("Charlie")) {
                            Log.v("ALO", result.get(0));
                            Intent intent = new Intent (this, MainActivity2Activity.class);
                            startActivity(intent);
                        }
                    else if (result.get(0).contains("mierda")) finish();
                        Log.v("ALO", result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}