package com.example.homefit.Activities;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.homefit.Adapters.ParentAdapter;
import com.example.homefit.Models.Days;
import com.example.homefit.R;
import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.Utils;
import com.github.jinatonic.confetti.confetto.Confetto;
import com.github.jinatonic.confetti.confetto.ShimmeringConfetto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkingOutActivity extends AppCompatActivity implements ConfettoGenerator {

    public Dialog finished; //after press done button
    private int size;
    Button start, pause, done;
    private long stopped = 0;
    boolean started = false;
    boolean paused = false;
    public int total = 0;
    public int count = 0;
    private Chronometer chrono; //time counter
    RecyclerView workoutRV;
    protected ViewGroup container;

    //for confetti
    protected int[] colors;
    private int velocitySlow, velocityNormal;
    protected int goldDark, goldMed, gold, goldLight;
    private List<Bitmap> confettoBitmaps;
    private final List<ConfettiManager> activeConfettiManagers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_out);
        //action bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("HomeFit");


        final Resources res = getResources();
        //confetti
        size = res.getDimensionPixelSize(R.dimen.default_confetti_size);
        velocitySlow = res.getDimensionPixelOffset(R.dimen.default_velocity_slow);
        velocityNormal = res.getDimensionPixelOffset(R.dimen.default_velocity_normal);
        container = findViewById(R.id.container);
        //confetti colors
        goldDark = res.getColor(R.color.blue_dark);
        goldMed = res.getColor(R.color.blue_med);
        gold = res.getColor(R.color.bluee);
        goldLight = res.getColor(R.color.blue_light);
        colors = new int[]{goldDark, goldMed, gold, goldLight};
        confettoBitmaps = Utils.generateConfettiBitmaps(colors, size);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        Days day = bundle.getParcelable("day");

        //init buttons and chronometer
        chrono = findViewById(R.id.chrono);
        start = findViewById(R.id.btnStart);
        pause = findViewById(R.id.btnPause);
        done = findViewById(R.id.done);

        //start time button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //start timer
                if (!started) {
                    chrono.setBase(SystemClock.elapsedRealtime() - stopped);
                    chrono.start();
                    started = true;
                    paused = false;
                }
            }
        });
        //pause time button
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //pause timer
                if (!paused && started) {
                    chrono.stop();
                    stopped = SystemClock.elapsedRealtime() - chrono.getBase();
                    started = false;
                    paused = true;
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Celebrate();
            }
        });

        workoutRV = findViewById(R.id.workoutRV); //recycler view
        workoutRV.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        workoutRV.setLayoutManager(mLayoutManager);
        ParentAdapter adapter = new ParentAdapter(this, day.getExercises(), true);
        workoutRV.setAdapter(adapter);


        for (int ex = 0; ex < day.getExercises().size(); ex++)
            for (int setNum = 0; setNum < day.getExercises().get(ex).getSets().size(); setNum++)
                total = total + 1;
        finished = new Dialog(this);

    }

    @Override
    protected void onDestroy() {
        chrono.stop();
        super.onDestroy();
    }

    public void Celebrate() {

        Button okBtn;
        chrono.stop(); //stop timer
        final CoordinatorLayout constraintLayout;
        finished.setContentView(R.layout.complete); //complete dialog
        constraintLayout = finished.findViewById(R.id.dialogLayout);
        container = finished.findViewById(R.id.dialogLayout);
        finished.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        constraintLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                constraintLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                activeConfettiManagers.add(generateStream());
                activeConfettiManagers.add(generateStream());
                container = finished.findViewById(R.id.con);
                activeConfettiManagers.add(generateStream());

            }
        });

        okBtn = finished.findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finished.dismiss();
                onBackPressed();
            }
        });

        finished.show();

    }

    @Override
    public Confetto generateConfetto(Random random){
        return new ShimmeringConfetto(
                confettoBitmaps.get(random.nextInt(confettoBitmaps.size())),
                goldLight, goldDark, 1000, random);
    }

    protected ConfettiManager generateStream() {
        return getConfettiManager().setNumInitialCount(0)
                .setEmissionDuration(3000)
                .setEmissionRate(50)
                .animate();
    }

    private ConfettiManager getConfettiManager() {
        final ConfettiSource confettiSource = new ConfettiSource(0, -size, container.getWidth(),
                -size);
        return new ConfettiManager(this,this,confettiSource,container)
                .setVelocityX(0, velocitySlow)
                .setVelocityY(velocityNormal, velocitySlow)
                .setInitialRotation(180, 180)
                .setRotationalAcceleration(360, 180)
                .setTargetRotationalVelocity(360);
    }
}
