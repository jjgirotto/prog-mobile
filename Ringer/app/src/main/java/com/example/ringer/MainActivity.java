package com.example.ringer;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private AudioManager mAudioManager;
    private boolean mModoSilencio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        adicionaEventoBotao();
        verificaEstadoRinger();
        alteraIconSonoro();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Obter o NotificationManager para verificar se temos permissão para alterar o ringer mode
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        /* Só necessitamos de pedir autorização no Android 6.0 Marshmallow ou superior e só se realmente não tivermos permissões. */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            //Construímos o intent que nos permite aceder às permissões que temos de dar à app.
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            //Lançamos a actividade com base no intent.
            startActivity(intent);
        }
    }

    /* Adiciona o listener ao botaoOnOff. Sempre que o botão for premido,
     * o código fornecido será executado. Isto é programação orientada a eventos. */
    private void adicionaEventoBotao() {
        Button botao = findViewById(R.id.btnToggle);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mModoSilencio) {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    mModoSilencio = false;
                } else {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    mModoSilencio = true;
                }
                alteraIconSonoro();
            }
        });
    }

    /* Este método afeta ao atributo <b>mModoSilencio</b> o valor <i>true</i> se o ringer estiver
    * em modo silencio ou em vibração e <i>false</i> se estiver em modo normal. */
    @SuppressLint("SetTextI18n")
    private void verificaEstadoRinger() {
        //obter o estado do ringer
        int estado = mAudioManager.getRingerMode();
        TextView txtView = findViewById(R.id.txtMode);
        if (estado == AudioManager.RINGER_MODE_SILENT || estado == AudioManager.RINGER_MODE_VIBRATE) {
            txtView.setText("@string/silent_mode");
        } else if (estado == AudioManager.RINGER_MODE_NORMAL) {
            txtView.setText("@string/normal_mode");
        }
    }

    /* Este método é responsável por colocar o icon correto mediante o estado do ringer */
    private void alteraIconSonoro() {
        ImageView img = findViewById(R.id.imgMode);
        if(mModoSilencio) {
            img.setImageResource(R.drawable.muted);
        } else {
            img.setImageResource(R.drawable.unmuted);
        }
    }


}