package opengarden.yee.interview.yistest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import service.SolvePuzzleCallBack;
import service.SolvePuzzleService;

/**
 * Created by Yee on 11/11/15.
 */
public class PopulationPuzzleActivity extends Activity implements SolvePuzzleCallBack {

    TextView mResult;
    TextView mQuestion;
    Button mSubmit;
    ProgressDialog mDialog;
    SolvePuzzleService mService;
    Timer mTimer = new Timer();
    int mTime = 0;
    String mTimeCost = "";
    String mOutput = "";

    public static final int TARGET = 100000000;
    private static final int[] defaultDataSet = {
            18897109, 12828837, 9461105, 6371773, 5965343, 5946800, 5582170, 5564635, 5268860, 4552402, 4335391,
            4296250, 4224851, 4192887, 3439809, 3279833, 3095313, 2812896, 2783243, 2710489, 2543482, 2356285,
            2226009, 2149127, 2142508, 2134411};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mService = new SolvePuzzleService(this);
        mQuestion = (TextView) findViewById(R.id.input);
        mSubmit = (Button) findViewById(R.id.solveButton);
        mResult = (TextView) findViewById(R.id.result);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long offset;
                                int sampleTime = mTime;
                                mTimeCost = String.format("%02d:%02d:%02d", sampleTime / (offset = 100 * 60),
                                        (offset = sampleTime % offset) / 100, offset % 100);
                                mDialog.setMessage(getString(R.string.on_processing_hint) + mTimeCost);
                                mTime++;
                            }
                        });
                    }
                }, 1000, 10);
            }
        });
    }

    @Override
    public void onActionSuccess() {
        mDialog.hide();
        mDialog.setMessage(getString(R.string.complete));
        mTimer.cancel();
        mOutput = getString(R.string.processing_result) + mTimeCost + "\n\n" + mService.getResult().toString();
        mResult.setText(mOutput);
    }

    @Override
    public void onActionFailed() {
        mDialog.hide();
        mDialog.setMessage(getString(R.string.complete));
        mTimer.cancel();
        mResult.setText(getString(R.string.no_solution));
    }

    private void startService() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.processing));
        mDialog.setCancelable(false);
        mDialog.show();
        mService.solvePuzzle(getArrayList(defaultDataSet), TARGET, new ArrayList<Integer>());
    }

    private ArrayList<Integer> getArrayList(int[] array) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i : array) result.add(i);
        return result;
    }
}
